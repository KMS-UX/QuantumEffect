package com.example.game.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.game.api.GeminiServiceHelper
import com.example.game.api.WebSource
import com.example.game.db.GameDatabase
import com.example.game.db.GameRepository
import com.example.game.db.GameState
import com.example.game.engine.AugmentBinding
import com.example.game.engine.Combo
import com.example.game.engine.CombatMath
import com.example.game.engine.Element
import com.example.game.engine.ElementalStatusRegistry
import com.example.game.engine.FactionReputationEngine
import com.example.game.models.*
import com.example.ui.theme.QuantumNeonBlue
import com.example.ui.theme.QuantumNeonGreen
import com.example.ui.theme.QuantumNeonOrange
import com.example.ui.theme.QuantumNeonPurple
import com.example.ui.theme.QuantumNeonRed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

data class RadioMessage(
    val sender: String,
    val text: String,
    val timestamp: String,
    val frequency: Float
)

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val db = GameDatabase.getDatabase(application)
    internal val repository = GameRepository(db.gameProgressDao())

    val gameStateFlow: StateFlow<GameState> = repository.gameStateFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = GameState()
    )

    // --- Ground combat ------------------------------------------------------

    private val _battleState = MutableStateFlow(BattleState())
    val battleState: StateFlow<BattleState> = _battleState.asStateFlow()

    /** Live element markers on the enemy currently in the battle slot. */
    private val enemyElements = ElementalStatusRegistry()

    // --- Gemini terminals ---------------------------------------------------

    private val _thinkingText = MutableStateFlow("")
    val thinkingText: StateFlow<String> = _thinkingText.asStateFlow()

    private val _isThinking = MutableStateFlow(false)
    val isThinking: StateFlow<Boolean> = _isThinking.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private val _searchSources = MutableStateFlow<List<WebSource>>(emptyList())
    val searchSources: StateFlow<List<WebSource>> = _searchSources.asStateFlow()

    private val _activeCompanionName = MutableStateFlow<String?>(null)
    val activeCompanionName: StateFlow<String?> = _activeCompanionName.asStateFlow()

    private val _radioTransmissionLog = MutableStateFlow<List<RadioMessage>>(emptyList())
    val radioTransmissionLog: StateFlow<List<RadioMessage>> = _radioTransmissionLog.asStateFlow()

    private val _isTransmitting = MutableStateFlow(false)
    val isTransmitting: StateFlow<Boolean> = _isTransmitting.asStateFlow()

    // --- Parallel earths ----------------------------------------------------

    private val _currentParallelEarth = MutableStateFlow(ParallelEarth.EARTH_PRIME)
    val currentParallelEarth: StateFlow<ParallelEarth> = _currentParallelEarth.asStateFlow()

    private val _unlockedGates = MutableStateFlow(setOf("gate_prime", "gate_nova"))
    val unlockedGates: StateFlow<Set<String>> = _unlockedGates.asStateFlow()

    private val _puzzlesState = MutableStateFlow(EnvironmentalPuzzlesState())
    val puzzlesState: StateFlow<EnvironmentalPuzzlesState> = _puzzlesState.asStateFlow()

    private val _isWarping = MutableStateFlow(false)
    val isWarping: StateFlow<Boolean> = _isWarping.asStateFlow()

    private val _warpProgress = MutableStateFlow(0f)
    val warpProgress: StateFlow<Float> = _warpProgress.asStateFlow()

    private val _warpMessage = MutableStateFlow("")
    val warpMessage: StateFlow<String> = _warpMessage.asStateFlow()

    // --- Starship -----------------------------------------------------------

    private val _starshipState = MutableStateFlow(
        StarshipState(
            upgrades = listOf(
                ShipUpgrade("up_shield_boost", "Hyper-Flux Shield Cell", "Boosts maximum shield output by +35%.", ShipSystem.SHIELDS, 400, 100, 1.35f),
                ShipUpgrade("up_railgun", "Hyper-Velocity Railgun", "Increases cannon firepower by +40%.", ShipSystem.WEAPONS, 600, 150, 1.4f),
                ShipUpgrade("up_chronos", "Chronos Warp Stabilizer", "Reduces warp travel fuel cost by 25%.", ShipSystem.NAVIGATION, 800, 200, 0.75f),
                ShipUpgrade("up_thrusters", "Tachyon Drive Boosters", "Increases engine sub-light velocity by +25%.", ShipSystem.ENGINES, 500, 120, 1.25f),
                ShipUpgrade("up_biosphere", "Automated Bio-Purifiers", "Increases life support efficiency, restoring health after travel.", ShipSystem.LIFE_SUPPORT, 300, 80, 1.2f)
            ),
            crew = listOf(
                CrewMember("crew_jaxx", "Commander Jaxx", "Pilot", "Tactical Evacuation", 4, "A former Aegis special ops pilot who turned rogue to find the missing Quantum Babies.", false, 400, "👨‍🚀"),
                CrewMember("crew_sera", "Sera Moss", "Engineer", "Overclocked Shields", 5, "Biomechanical engineer who designed the Nova Tellus canopy. Can hotwire Singularium reactors.", false, 600, "👩‍🔧"),
                CrewMember("crew_draks", "Draks", "Gunner", "Aerosol Flak Burst", 3, "A scrap-merchant mercenary with an extreme obsession with heavy munitions.", false, 300, "👽"),
                CrewMember("crew_alistar", "Dr. Alistair", "Scientist", "Quantum Decryption", 4, "Obsessive researcher of the 'Original Sins'. Knows the key frequencies of parallel universes.", false, 500, "👨‍🔬")
            ),
            starSystems = listOf(
                StarSystem("sys_sol_prime", "Solis System (Earth Prime Core)", 0f, 0f, "Aurelian Corp", "The baseline center of Neo Solis. Densely populated and suffering under cybernetic decay.", ParallelEarth.EARTH_PRIME, 1.0f, "Aurelian Hyper-Reactor", true),
                StarSystem("sys_tellus_alpha", "Tellus Alpha Sector", -40f, 70f, "Emberpact Cultivators", "An overgrown sector in the Nova Tellus cluster teeming with bioluminescent space flora and feral biomechanical creatures.", ParallelEarth.NOVA_TELLUS, 1.5f, "Ancient Arbor Anchor", true),
                StarSystem("sys_void_omega", "Void Omega Cluster", 80f, -50f, "Void Seekers", "A fractured ocean of spatial rifts where forgotten outposts float inside cosmic gravity wells.", ParallelEarth.VOID_CORE, 2.0f, "Reality Anchor Core", true),
                StarSystem("sys_singularity", "The Core Singularium", 120f, 100f, "Quantum Guardians", "The cradle of Project Genesis. A highly unstable spacetime nexus where the original Quantum Babies remain suspended.", ParallelEarth.VOID_CORE, 3.0f, "Nursery Nexus Obelisk", false)
            )
        )
    )
    val starshipState: StateFlow<StarshipState> = _starshipState.asStateFlow()

    private val _spaceCombat = MutableStateFlow(SpaceCombatState())
    val spaceCombat: StateFlow<SpaceCombatState> = _spaceCombat.asStateFlow()

    init {
        viewModelScope.launch { seedPremiumCompanions() }
    }

    /**
     * Ensures the premium companion roster exists on an old save from before those
     * records shipped, without clobbering bond progress on the ones already there.
     */
    private suspend fun seedPremiumCompanions() {
        val current = repository.getGameState()
        val premium = listOf(
            CompanionRecord("Kade", "The Ronin", "Silent Veil", 0, false, "Blade Cascade", "Chains three quantum-edge strikes, each ignoring 25% armor.", "🗡️", 1),
            CompanionRecord("Orin", "The Archivist", "Aurelian Order", 0, false, "Coordinate Recall", "Reveals every point of interest in the active biome for 5 turns.", "📜", 1)
        )
        val missing = premium.filter { candidate -> current.companions.none { it.name == candidate.name } }
        if (missing.isEmpty()) return
        repository.saveGameState(current.copy(companions = current.companions + missing))
    }

    // ------------------------------------------------------------------
    // Player progression helpers
    // ------------------------------------------------------------------

    /** Applies XP and rolls the level up when the threshold is crossed. */
    private fun awardXp(state: GameState, xp: Int): Pair<Int, Int> {
        var level = state.level
        var total = state.xp + xp
        val required = level * XP_PER_LEVEL
        if (total >= required) {
            level++
            total -= required
        }
        return level to total
    }

    /** Augment-derived combat profile for the current save. */
    private fun profileFor(state: GameState) =
        AugmentBinding.derive(state.installedAugments, repository.augmentList)

    // ------------------------------------------------------------------
    // Companions
    // ------------------------------------------------------------------

    fun deployCompanionOnField(companionName: String?) {
        _activeCompanionName.value = companionName
    }

    fun recruitCompanion(companionName: String, creditsCost: Int, nanitesCost: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            if (current.credits < creditsCost || current.nanites < nanitesCost) return@launch
            val target = current.companions.firstOrNull { it.name == companionName } ?: return@launch
            if (target.isRecruited) return@launch

            repository.saveGameState(
                current.copy(
                    credits = current.credits - creditsCost,
                    nanites = current.nanites - nanitesCost,
                    companions = current.companions.map {
                        if (it.name == companionName) it.copy(isRecruited = true) else it
                    }
                )
            )
        }
    }

    fun levelUpCompanion(companionName: String, creditsCost: Int, nanitesCost: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            if (current.credits < creditsCost || current.nanites < nanitesCost) return@launch

            repository.saveGameState(
                current.copy(
                    credits = current.credits - creditsCost,
                    nanites = current.nanites - nanitesCost,
                    companions = current.companions.map {
                        if (it.name == companionName && it.isRecruited) {
                            it.copy(level = it.level + 1, bondPoints = it.bondPoints + 10)
                        } else {
                            it
                        }
                    }
                )
            )
        }
    }

    fun equipCompanionGear(companionName: String, slot: String, gearName: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val updated = current.companions.map { companion ->
                if (companion.name != companionName) return@map companion
                when (slot.uppercase(Locale.ROOT)) {
                    "WEAPON" -> companion.copy(weaponEquipped = gearName)
                    "ARMOR" -> companion.copy(armorEquipped = gearName)
                    "ACCESSORY" -> companion.copy(accessoryEquipped = gearName)
                    "MODULE" -> companion.copy(moduleEquipped = gearName)
                    else -> companion
                }
            }
            repository.saveGameState(current.copy(companions = updated))
        }
    }

    fun triggerCompanionBond(companionName: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            repository.saveGameState(
                current.copy(
                    companions = current.companions.map {
                        if (it.name == companionName) {
                            it.copy(bondPoints = min(MAX_BOND_POINTS, it.bondPoints + BOND_POINTS_PER_INTERACTION))
                        } else {
                            it
                        }
                    }
                )
            )
        }
    }

    // ------------------------------------------------------------------
    // Loadout
    // ------------------------------------------------------------------

    fun equipWeapon(weapon: Weapon) {
        viewModelScope.launch {
            val current = repository.getGameState()
            repository.saveGameState(current.copy(currentWeapon = weapon))
        }
    }

    fun equipOutfit(outfit: Outfit) {
        viewModelScope.launch {
            val current = repository.getGameState()
            repository.saveGameState(current.copy(currentOutfit = outfit))
        }
    }

    fun installAugment(slot: AugmentSlot, name: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            repository.saveGameState(
                current.copy(installedAugments = current.installedAugments + (slot to name))
            )
        }
    }

    fun toggleModChip(chip: ModChip) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val installed = current.installedModChips
            val updated = if (chip in installed) {
                installed - chip
            } else {
                if (installed.size >= MAX_MOD_CHIPS) return@launch
                installed + chip
            }
            repository.saveGameState(current.copy(installedModChips = updated))
        }
    }

    // ------------------------------------------------------------------
    // World interaction
    // ------------------------------------------------------------------

    fun completePOIMission(poiName: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val poi = current.mapState.firstOrNull { it.name == poiName } ?: return@launch
            if (poi.isCleared) return@launch

            val (level, xp) = awardXp(current, POI_CLEAR_XP)
            val reputations = FactionReputationEngine.applyShift(
                current.factionReputations,
                poi.factionPresent,
                POI_CLEAR_REPUTATION
            )
            repository.saveGameState(
                current.copy(
                    level = level,
                    xp = xp,
                    credits = current.credits + POI_CLEAR_CREDITS,
                    nanites = current.nanites + POI_CLEAR_NANITES,
                    factionReputations = reputations,
                    mapState = current.mapState.map {
                        if (it.name == poiName) it.copy(isCleared = true) else it
                    }
                )
            )
        }
    }

    fun addResources(credits: Int, nanites: Int, xp: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val (level, remainingXp) = awardXp(current, xp)
            repository.saveGameState(
                current.copy(
                    level = level,
                    xp = remainingXp,
                    credits = max(0, current.credits + credits),
                    nanites = max(0, current.nanites + nanites)
                )
            )
        }
    }

    fun addItemToInventory(
        name: String,
        category: String,
        symbol: String,
        amount: Int,
        description: String,
        hpBonus: Int = 0,
        mpBonus: Int = 0,
        atkBonus: Int = 0,
        defBonus: Int = 0,
        isConsumable: Boolean = false,
        isEquippable: Boolean = false
    ) {
        viewModelScope.launch {
            val current = repository.getGameState()
            repository.saveGameState(
                current.copy(
                    inventory = mergeIntoInventory(
                        current.inventory,
                        InventoryItem(
                            id = "item_${System.nanoTime()}",
                            name = name,
                            category = category,
                            iconSymbol = symbol,
                            quantity = amount,
                            description = description,
                            hpBonus = hpBonus,
                            mpBonus = mpBonus,
                            atkBonus = atkBonus,
                            defBonus = defBonus,
                            isConsumable = isConsumable,
                            isEquippable = isEquippable
                        )
                    )
                )
            )
        }
    }

    /** Stacks [addition] onto a same-named entry when one exists, else appends it. */
    private fun mergeIntoInventory(
        inventory: List<InventoryItem>,
        addition: InventoryItem
    ): List<InventoryItem> {
        val index = inventory.indexOfFirst { it.name == addition.name }
        if (index == -1) return inventory + addition
        return inventory.mapIndexed { i, item ->
            if (i == index) item.copy(quantity = item.quantity + addition.quantity) else item
        }
    }

    fun equipInventoryItem(itemId: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val target = current.inventory.firstOrNull { it.id == itemId } ?: return@launch
            if (!target.isEquippable) return@launch

            repository.saveGameState(
                current.copy(
                    inventory = current.inventory.map {
                        when {
                            it.id == itemId -> it.copy(isEquipped = !it.isEquipped)
                            // Only one item per category may be worn at a time.
                            it.category == target.category && !target.isEquipped -> it.copy(isEquipped = false)
                            else -> it
                        }
                    }
                )
            )
        }
    }

    fun consumeInventoryItem(itemId: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val target = current.inventory.firstOrNull { it.id == itemId } ?: return@launch
            if (!target.isConsumable || target.quantity <= 0) return@launch

            val remaining = target.quantity - 1
            val inventory = if (remaining <= 0) {
                current.inventory.filterNot { it.id == itemId }
            } else {
                current.inventory.map { if (it.id == itemId) it.copy(quantity = remaining) else it }
            }

            repository.saveGameState(
                current.copy(
                    health = min(current.maxHealth, current.health + target.hpBonus),
                    mp = min(current.maxMp, current.mp + target.mpBonus),
                    inventory = inventory
                )
            )
        }
    }

    fun deployArchitecture(
        faction: Faction,
        creditsCost: Int,
        nanitesCost: Int,
        xpGain: Int,
        repIncrease: Int,
        structure: DeployedStructure
    ) {
        viewModelScope.launch {
            val current = repository.getGameState()
            if (current.credits < creditsCost || current.nanites < nanitesCost) return@launch

            val (level, xp) = awardXp(current, xpGain)
            repository.saveGameState(
                current.copy(
                    level = level,
                    xp = xp,
                    credits = current.credits - creditsCost,
                    nanites = current.nanites - nanitesCost,
                    factionReputations = FactionReputationEngine.applyShift(
                        current.factionReputations, faction, repIncrease
                    ),
                    deployedStructures = current.deployedStructures + structure
                )
            )
        }
    }

    fun upgradeDeployedStructure(structureId: Long, creditsCost: Int, nanitesCost: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            if (current.credits < creditsCost || current.nanites < nanitesCost) return@launch

            repository.saveGameState(
                current.copy(
                    credits = current.credits - creditsCost,
                    nanites = current.nanites - nanitesCost,
                    deployedStructures = current.deployedStructures.map {
                        if (it.id == structureId) it.copy(isUpgraded = true, level = it.level + 1) else it
                    }
                )
            )
        }
    }

    fun adoptPet(name: String, emoji: String, creditsCost: Int, nanitesCost: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            if (current.credits < creditsCost || current.nanites < nanitesCost) return@launch
            val alreadyAdopted = current.deployedStructures.any {
                it.type == PET_STRUCTURE_TYPE && it.name == name
            }
            if (alreadyAdopted) return@launch

            val pet = DeployedStructure(
                id = System.nanoTime(),
                type = PET_STRUCTURE_TYPE,
                name = name,
                factionName = emoji,
                biomeName = "Colony Base",
                x = 0f,
                y = 0f,
                colorVal = PET_MARKER_COLOR
            )
            repository.saveGameState(
                current.copy(
                    credits = current.credits - creditsCost,
                    nanites = current.nanites - nanitesCost,
                    deployedStructures = current.deployedStructures + pet
                )
            )
        }
    }

    fun trainPet(petId: Long, creditsCost: Int, nanitesCost: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            if (current.credits < creditsCost || current.nanites < nanitesCost) return@launch

            repository.saveGameState(
                current.copy(
                    credits = current.credits - creditsCost,
                    nanites = current.nanites - nanitesCost,
                    deployedStructures = current.deployedStructures.map {
                        if (it.id == petId) it.copy(level = it.level + 1) else it
                    }
                )
            )
        }
    }

    fun setActivePet(petId: Long) {
        viewModelScope.launch {
            val current = repository.getGameState()
            repository.saveGameState(
                current.copy(
                    deployedStructures = current.deployedStructures.map {
                        if (it.type == PET_STRUCTURE_TYPE) it.copy(isUpgraded = it.id == petId) else it
                    }
                )
            )
        }
    }

    fun craftItem(
        recipeName: String,
        creditsCost: Int,
        nanitesCost: Int,
        materialsNeeded: List<Pair<String, Int>>,
        resultItemName: String,
        resultCategory: String,
        resultSymbol: String,
        resultDesc: String,
        hpBonus: Int,
        mpBonus: Int,
        atkBonus: Int,
        defBonus: Int,
        isConsumable: Boolean,
        isEquippable: Boolean
    ) {
        viewModelScope.launch {
            val current = repository.getGameState()
            if (current.credits < creditsCost || current.nanites < nanitesCost) return@launch

            val hasMaterials = materialsNeeded.all { (materialName, needed) ->
                (current.inventory.firstOrNull { it.name == materialName }?.quantity ?: 0) >= needed
            }
            if (!hasMaterials) return@launch

            val consumed = current.inventory.mapNotNull { item ->
                val cost = materialsNeeded.firstOrNull { it.first == item.name }?.second
                    ?: return@mapNotNull item
                val remaining = item.quantity - cost
                if (remaining > 0) item.copy(quantity = remaining) else null
            }

            val crafted = InventoryItem(
                id = "crafted_${System.nanoTime()}",
                name = resultItemName,
                category = resultCategory,
                iconSymbol = resultSymbol,
                quantity = 1,
                description = resultDesc,
                hpBonus = hpBonus,
                mpBonus = mpBonus,
                atkBonus = atkBonus,
                defBonus = defBonus,
                isConsumable = isConsumable,
                isEquippable = isEquippable
            )

            val (level, xp) = awardXp(current, CRAFT_XP)
            repository.saveGameState(
                current.copy(
                    level = level,
                    xp = xp,
                    credits = current.credits - creditsCost,
                    nanites = current.nanites - nanitesCost,
                    inventory = mergeIntoInventory(consumed, crafted)
                )
            )
        }
    }

    /**
     * Advances the colony production tick. Yields scale with the number and level
     * of deployed structures; [onYieldCalculated] receives the credits and nanites
     * that were banked so the UI can animate them.
     */
    fun advanceTimeAndProduce(onYieldCalculated: (Int, Int) -> Unit) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val producers = current.deployedStructures.filterNot { it.type == PET_STRUCTURE_TYPE }
            val credits = producers.sumOf { CREDITS_PER_STRUCTURE_LEVEL * it.level }
            val nanites = producers.sumOf { NANITES_PER_STRUCTURE_LEVEL * it.level }

            repository.saveGameState(
                current.copy(
                    credits = current.credits + credits,
                    nanites = current.nanites + nanites
                )
            )
            onYieldCalculated(credits, nanites)
        }
    }

    // ------------------------------------------------------------------
    // Vitality
    // ------------------------------------------------------------------

    fun healPlayerToFull() {
        viewModelScope.launch {
            val current = repository.getGameState()
            repository.saveGameState(current.copy(health = current.maxHealth))
        }
    }

    fun restorePlayerMp(amount: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            repository.saveGameState(current.copy(mp = min(current.maxMp, current.mp + amount)))
        }
    }

    fun updatePlayerHealth(hp: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            repository.saveGameState(current.copy(health = hp.coerceIn(0, current.maxHealth)))
        }
    }

    fun consumePlayerMp(amount: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            repository.saveGameState(current.copy(mp = max(0, current.mp - amount)))
        }
    }

    fun spendCreditsDirectly(amount: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            if (current.credits < amount) return@launch
            repository.saveGameState(current.copy(credits = current.credits - amount))
        }
    }

    fun awardCreditsDirectly(amount: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            repository.saveGameState(current.copy(credits = current.credits + amount))
        }
    }

    // ------------------------------------------------------------------
    // Ground combat
    // ------------------------------------------------------------------

    fun startCombatWithEnemy(enemyName: String, dangerLevel: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val hp = when (dangerLevel) {
                "Minor" -> 150
                "Moderate" -> 250
                "Severe" -> 400
                "Critical" -> 600
                else -> 900
            }
            val atk = when (dangerLevel) {
                "Minor" -> 20
                "Moderate" -> 35
                "Severe" -> 50
                "Critical" -> 70
                else -> 90
            }
            val def = when (dangerLevel) {
                "Minor" -> 10
                "Moderate" -> 25
                "Severe" -> 40
                "Critical" -> 55
                else -> 75
            }

            enemyElements.clear()
            val enemy = Enemy(
                name = enemyName,
                maxHp = hp,
                currentHp = hp,
                atk = atk,
                defense = def,
                speed = 40,
                description = "Corrupted high-threat hazard warping physical matter with localized quantum radiation.",
                resistanceType = if (dangerLevel == "Apocalyptic") "Void" else "Kinetic",
                vulnerability = "Quantum Energy & Critical Strikes",
                modName = "Overcharged Resonator"
            )
            _battleState.value = BattleState(
                isActive = true,
                playerHp = current.health,
                playerMaxHp = current.maxHealth,
                playerMp = current.mp,
                playerMaxMp = current.maxMp,
                activeEnemy = enemy,
                logs = listOf(
                    BattleLog("ALERT: Spatial tear detected! Encountered hostile threat: $enemyName ($dangerLevel)!", false, QuantumNeonRed),
                    BattleLog("Tactical advisory: Analyze vulnerabilities and cycle active augments.", false, Color.LightGray)
                ),
                turnNumber = 1,
                isPlayerTurn = true,
                battleRewardXp = hp / 2,
                battleRewardCredits = hp
            )
        }
    }

    fun executePlayerAttack() {
        val state = _battleState.value
        val enemy = state.activeEnemy ?: return
        if (!state.isPlayerTurn || state.victory || state.defeat) return

        viewModelScope.launch {
            val save = repository.getGameState()
            val profile = profileFor(save)
            val weapon = save.currentWeapon

            val result = CombatMath.resolveAttack(
                weaponPower = weapon.baseAtk + save.totalAtkBonus,
                strengthAugment = profile.strengthMultiplier,
                targetDefense = enemy.defense,
                targetMaxHp = enemy.maxHp,
                critChance = profile.critChance + modChipCritBonus(save),
                armorPierce = if (weapon == Weapon.QUANTUM_BLADE) QUANTUM_BLADE_PIERCE else 0f
            )

            val newHp = max(0, enemy.currentHp - result.damage)
            val logs = state.logs.toMutableList()
            logs += BattleLog(
                buildString {
                    append(if (result.isCritical) "CRITICAL STRIKE: " else "STRIKE: ")
                    append("${weapon.displayName} lands for ${result.damage} damage")
                    append(" [${result.tier.name} impact, ${CombatMath.particleBudget(result.tier)} sparks]")
                },
                true,
                if (result.isCritical) QuantumNeonOrange else QuantumNeonGreen
            )

            // Kinetic strikes seed EARTH; a follow-up fire skill detonates Magma Spike.
            if (Random.nextFloat() < profile.synergyChance) {
                enemyElements.apply(Element.EARTH)?.let { combo ->
                    logs += BattleLog("SYNERGY DETONATION: ${combo.displayName}!", true, QuantumNeonPurple)
                }
            }

            if (newHp <= 0) {
                handleBattleVictory(state, enemy, logs)
            } else {
                _battleState.value = state.copy(
                    activeEnemy = enemy.copy(currentHp = newHp),
                    logs = logs,
                    isPlayerTurn = false
                )
                triggerEnemyResponseTurn()
            }
        }
    }

    fun executePlayerSkill(skill: ActiveSkill) {
        val state = _battleState.value
        val enemy = state.activeEnemy ?: return
        if (!state.isPlayerTurn || state.victory || state.defeat) return

        if (state.playerMp < skill.mpCost) {
            _battleState.value = state.copy(
                logs = state.logs + BattleLog(
                    "ERROR: Insufficient Quantum MP to cycle ${skill.displayName}!",
                    true,
                    QuantumNeonRed
                )
            )
            return
        }

        viewModelScope.launch {
            val save = repository.getGameState()
            val profile = profileFor(save)
            val logs = state.logs.toMutableList()

            if (skill == ActiveSkill.HEALING_PULSE || skill == ActiveSkill.TEMPORAL_LOOP) {
                val healed = min(state.playerMaxHp, state.playerHp + skill.healAmount)
                logs += BattleLog(
                    "${skill.displayName}: bio-regenerators restore ${healed - state.playerHp} HP.",
                    true,
                    QuantumNeonGreen
                )
                _battleState.value = state.copy(
                    playerHp = healed,
                    playerMp = state.playerMp - skill.mpCost,
                    logs = logs,
                    isPlayerTurn = false
                )
                triggerEnemyResponseTurn()
                return@launch
            }

            val result = CombatMath.resolveAttack(
                weaponPower = save.currentWeapon.baseMag + skill.mpCost * 4,
                skillMultiplier = skill.damageMultiplier,
                strengthAugment = profile.strengthMultiplier,
                targetDefense = enemy.defense,
                targetMaxHp = enemy.maxHp,
                critChance = profile.critChance + modChipCritBonus(save)
            )

            val newHp = max(0, enemy.currentHp - result.damage)
            logs += BattleLog(
                "${skill.displayName} resonates for ${result.damage} damage [${result.tier.name}].",
                true,
                skill.color
            )

            skill.element?.let { element ->
                enemyElements.apply(element)?.let { combo ->
                    logs += BattleLog(
                        "SYNERGY DETONATION: ${combo.displayName} — ${comboDescription(combo)}",
                        true,
                        QuantumNeonPurple
                    )
                }
            }

            if (newHp <= 0) {
                handleBattleVictory(state.copy(playerMp = state.playerMp - skill.mpCost), enemy, logs)
            } else {
                _battleState.value = state.copy(
                    activeEnemy = enemy.copy(currentHp = newHp),
                    playerMp = state.playerMp - skill.mpCost,
                    logs = logs,
                    isPlayerTurn = false
                )
                triggerEnemyResponseTurn()
            }
        }
    }

    private fun comboDescription(combo: Combo): String = buildString {
        if (combo.pullsTargets) append("targets dragged inward. ")
        if (combo.freezeSeconds > 0f) append("frozen for ${combo.freezeSeconds}s. ")
        if (combo.chainsToAdjacent) append("arcing to adjacent hostiles. ")
        if (combo.blocksNavigation) append("terrain spikes block the approach. ")
        if (combo.armorShredFraction > 0f) {
            append("armor stripped by ${(combo.armorShredFraction * 100).roundToInt()}%. ")
        }
    }.trim().ifEmpty { "impact registered." }

    private fun modChipCritBonus(state: GameState): Float =
        if (ModChip.CRITICAL_BOOST in state.installedModChips) CRITICAL_BOOST_CHIP_BONUS else 0f

    private fun triggerEnemyResponseTurn() {
        viewModelScope.launch {
            delay(ENEMY_TURN_DELAY_MS)
            val state = _battleState.value
            val enemy = state.activeEnemy ?: return@launch
            if (state.victory || state.defeat) return@launch

            val isMiss = Random.nextInt(100) < ENEMY_MISS_PERCENT
            val damage = max(10, enemy.atk - Random.nextInt(0, 10))
            val logs = state.logs.toMutableList()

            val playerHp = if (isMiss) {
                logs += BattleLog(
                    "DODGED: ${enemy.name} sweeps a heavy strike but misses your localized coordinate!",
                    false,
                    QuantumNeonBlue
                )
                state.playerHp
            } else {
                logs += BattleLog(
                    "ALERT: ${enemy.name} releases ${enemy.modName} blast, dealing $damage damage to your cyber-chassis!",
                    false,
                    QuantumNeonRed
                )
                max(0, state.playerHp - damage)
            }

            if (playerHp > 0) {
                _battleState.value = state.copy(
                    playerHp = playerHp,
                    logs = logs,
                    turnNumber = state.turnNumber + 1,
                    isPlayerTurn = true
                )
            } else {
                logs += BattleLog(
                    "SYSTEM CRITICAL FAILURE: Cyber-core offline. Quantum Baby has collapsed ...",
                    false,
                    QuantumNeonRed
                )
                _battleState.value = state.copy(playerHp = 0, logs = logs, defeat = true)
            }
            updatePlayerHealth(playerHp)
        }
    }

    private suspend fun handleBattleVictory(
        state: BattleState,
        enemy: Enemy,
        logs: MutableList<BattleLog>
    ) {
        val current = repository.getGameState()
        val xpChipBonus = if (ModChip.EXP_BOOSTER in current.installedModChips) EXP_BOOSTER_CHIP_BONUS else 1f
        val awardedXp = (state.battleRewardXp * xpChipBonus).roundToInt()
        val (level, xp) = awardXp(current, awardedXp)

        logs += BattleLog(
            "TARGET NEUTRALIZED: ${enemy.name} collapses. Salvaged ${state.battleRewardCredits} credits and $awardedXp XP.",
            true,
            QuantumNeonGreen
        )
        if (level > current.level) {
            logs += BattleLog("LEVEL UP: neural lattice recalibrated to tier $level.", true, QuantumNeonPurple)
        }

        enemyElements.clear()
        _battleState.value = state.copy(
            activeEnemy = enemy.copy(currentHp = 0),
            logs = logs,
            victory = true,
            isPlayerTurn = false
        )

        repository.saveGameState(
            current.copy(
                level = level,
                xp = xp,
                credits = current.credits + state.battleRewardCredits,
                health = state.playerHp,
                mp = state.playerMp
            )
        )
    }

    fun endBattle() {
        enemyElements.clear()
        _battleState.value = BattleState()
    }

    /**
     * Appends a log line from UI-driven combat flourishes. [colorBits] is the
     * packed `Color.value` the caller already has on hand.
     */
    fun injectCombatLog(
        text: String,
        isPlayerAction: Boolean = false,
        colorBits: Long = Color.White.value.toLong()
    ) {
        val state = _battleState.value
        _battleState.value = state.copy(
            logs = state.logs + BattleLog(text, isPlayerAction, Color(colorBits.toULong()))
        )
    }

    // ------------------------------------------------------------------
    // Gemini terminals
    // ------------------------------------------------------------------

    fun analyzeQuantumSynergy(prompt: String) {
        if (prompt.isBlank()) return
        viewModelScope.launch {
            _isThinking.value = true
            _thinkingText.value = ""
            _thinkingText.value = GeminiServiceHelper.generateHighThinkingContent(
                prompt = prompt,
                systemInstruction = SYNERGY_SYSTEM_INSTRUCTION
            )
            _isThinking.value = false
        }
    }

    fun searchSolisInfoband(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _isSearching.value = true
            _searchText.value = ""
            _searchSources.value = emptyList()
            val (text, sources) = GeminiServiceHelper.generateSearchGroundedContent(
                prompt = query,
                systemInstruction = INFOBAND_SYSTEM_INSTRUCTION
            )
            _searchText.value = text
            _searchSources.value = sources
            _isSearching.value = false
        }
    }

    fun transmitRadioMessage(frequency: Float, userMessage: String) {
        if (userMessage.isBlank()) return
        viewModelScope.launch {
            _radioTransmissionLog.value += RadioMessage("DIRECTOR (YOU)", userMessage, nowTimestamp(), frequency)
            _isTransmitting.value = true

            val systemInstruction = when (frequency) {
                FREQ_ENLIGHTENERS -> ENLIGHTENERS_INSTRUCTION
                FREQ_TECHNOPUNKS -> TECHNOPUNKS_INSTRUCTION
                FREQ_SPECTRE -> SPECTRE_INSTRUCTION
                else -> "You are an unknown encrypted tactical frequency. Provide basic military intelligence."
            }
            val respondent = when (frequency) {
                FREQ_ENLIGHTENERS -> "ENLIGHTENERS HQ [144.8 MHz]"
                FREQ_TECHNOPUNKS -> "TECHNOPUNK HACKER REBELS [98.2 MHz]"
                FREQ_SPECTRE -> "SPECTRE CORRUPT-AI [404.0 MHz]"
                else -> "UNKNOWN BROADCASTER"
            }

            val response = GeminiServiceHelper.generateHighThinkingContent(userMessage, systemInstruction)
            _radioTransmissionLog.value += RadioMessage(respondent, response, nowTimestamp(), frequency)
            _isTransmitting.value = false
        }
    }

    fun clearRadioLogs() {
        _radioTransmissionLog.value = emptyList()
    }

    private fun nowTimestamp(): String =
        SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

    // ------------------------------------------------------------------
    // Quests and factions
    // ------------------------------------------------------------------

    fun acceptQuest(questId: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            repository.saveGameState(
                current.copy(
                    quests = current.quests.map {
                        if (it.id == questId && it.status == "AVAILABLE") it.copy(status = "ACTIVE") else it
                    }
                )
            )
        }
    }

    /**
     * Resolves a branching quest step. Choice 0 sides with the quest's own faction;
     * any other choice sides against it, which the reputation engine mirrors onto
     * the opposed faction.
     */
    fun makeQuestChoice(questId: String, choiceIndex: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val quest = current.quests.firstOrNull { it.id == questId } ?: return@launch
            if (quest.status == "COMPLETE") return@launch

            val faction = Faction.entries.firstOrNull {
                it.displayName.equals(quest.faction, ignoreCase = true)
            }
            val repDelta = if (choiceIndex == 0) quest.rewardReputationPoints else -quest.rewardReputationPoints
            val reputations = faction
                ?.let { FactionReputationEngine.applyShift(current.factionReputations, it, repDelta) }
                ?: current.factionReputations

            val (level, xp) = awardXp(current, quest.rewardXp)
            repository.saveGameState(
                current.copy(
                    level = level,
                    xp = xp,
                    credits = current.credits + quest.rewardCredits,
                    nanites = current.nanites + quest.rewardNanites,
                    factionReputations = reputations,
                    quests = current.quests.map {
                        if (it.id == questId) {
                            it.copy(status = "COMPLETE", progress = it.targetCount)
                        } else {
                            it
                        }
                    }
                )
            )
        }
    }

    fun purchaseFactionItem(factionName: String, itemName: String, price: Int, requiredRep: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val faction = Faction.entries.firstOrNull {
                it.displayName.equals(factionName, ignoreCase = true)
            } ?: return@launch

            val reputation = current.factionReputations[faction] ?: 0
            if (reputation < requiredRep) return@launch

            val finalPrice = (price * FactionReputationEngine.priceMultiplier(reputation)).roundToInt()
            if (current.credits < finalPrice) return@launch

            repository.saveGameState(
                current.copy(
                    credits = current.credits - finalPrice,
                    inventory = mergeIntoInventory(
                        current.inventory,
                        InventoryItem(
                            id = "faction_${System.nanoTime()}",
                            name = itemName,
                            category = "Faction Goods",
                            iconSymbol = "🏷️",
                            quantity = 1,
                            description = "Acquired from ${faction.displayName} at ${faction.baseLocation}."
                        )
                    )
                )
            )
        }
    }

    fun modifyFactionReputations(repChanges: Map<Faction, Int>, dialogueLogText: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            var reputations = current.factionReputations
            repChanges.forEach { (faction, delta) ->
                reputations = FactionReputationEngine.applyShift(reputations, faction, delta)
            }
            repository.saveGameState(current.copy(factionReputations = reputations))

            if (dialogueLogText.isNotBlank()) {
                injectCombatLog(dialogueLogText, false, Color.LightGray.value.toLong())
            }
        }
    }

    // ------------------------------------------------------------------
    // Parallel earths
    // ------------------------------------------------------------------

    fun discoverGate(gateId: String) {
        _unlockedGates.value = _unlockedGates.value + gateId
    }

    fun startWarpShift(targetEarth: ParallelEarth, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            _isWarping.value = true
            _warpProgress.value = 0f
            _warpMessage.value = "Spooling Chronos core toward ${targetEarth.displayName}..."

            repeat(WARP_STEPS) { step ->
                delay(WARP_STEP_DELAY_MS)
                _warpProgress.value = (step + 1) / WARP_STEPS.toFloat()
                _warpMessage.value = when {
                    step < WARP_STEPS / 3 -> "Aligning quantum coordinates..."
                    step < 2 * WARP_STEPS / 3 -> "Breaching the membrane between realities..."
                    else -> "Stabilizing arrival vector in ${targetEarth.displayName}..."
                }
            }

            _currentParallelEarth.value = targetEarth
            _warpMessage.value = "Arrived: ${targetEarth.displayName}"
            _isWarping.value = false
            _warpProgress.value = 1f
            onComplete()
        }
    }

    fun toggleBridgeNovaRootNodes() {
        val current = _puzzlesState.value
        val nextVal = !current.bridgeNovaRootNodesActive
        _puzzlesState.value = current.copy(
            isAurelianBridgeSolved = nextVal && current.bridgeVoidTemporalRiftActive,
            bridgeNovaRootNodesActive = nextVal
        )
    }

    fun toggleBridgeVoidTemporalRift() {
        val current = _puzzlesState.value
        val nextVal = !current.bridgeVoidTemporalRiftActive
        _puzzlesState.value = current.copy(
            isAurelianBridgeSolved = current.bridgeNovaRootNodesActive && nextVal,
            bridgeVoidTemporalRiftActive = nextVal
        )
    }

    fun toggleCryoShieldEarthGenerators() {
        val current = _puzzlesState.value
        val nextVal = !current.cryoShieldEarthGeneratorsDisabled
        _puzzlesState.value = current.copy(
            isCryoShieldSolved = nextVal && current.cryoShieldNovaVinesHarvested,
            cryoShieldEarthGeneratorsDisabled = nextVal
        )
    }

    fun toggleCryoShieldNovaVines() {
        val current = _puzzlesState.value
        val nextVal = !current.cryoShieldNovaVinesHarvested
        _puzzlesState.value = current.copy(
            isCryoShieldSolved = current.cryoShieldEarthGeneratorsDisabled && nextVal,
            cryoShieldNovaVinesHarvested = nextVal
        )
    }

    fun tuneDrillFrequency(earth: ParallelEarth, freq: Float) {
        val current = _puzzlesState.value
        val updated = when (earth) {
            ParallelEarth.EARTH_PRIME -> current.copy(drillPrimeFrequency = freq)
            ParallelEarth.NOVA_TELLUS -> current.copy(drillNovaFrequency = freq)
            ParallelEarth.VOID_CORE -> current.copy(drillVoidFrequency = freq)
        }
        val solved = updated.drillPrimeFrequency == DRILL_RESONANCE_FREQUENCY &&
            updated.drillNovaFrequency == DRILL_RESONANCE_FREQUENCY &&
            updated.drillVoidFrequency == DRILL_RESONANCE_FREQUENCY
        _puzzlesState.value = updated.copy(isVoidDrillSolved = solved)
    }

    // ------------------------------------------------------------------
    // Starship
    // ------------------------------------------------------------------

    fun allocateSystemPower(system: ShipSystem, power: Int) {
        val current = _starshipState.value
        if ((current.systemPowerAllocation[system] ?: 0) == power) return

        val otherPower = current.systemPowerAllocation
            .filterKeys { it != system }
            .values
            .sum()
        if (otherPower + power > SHIP_POWER_BUDGET) return

        _starshipState.value = current.copy(
            systemPowerAllocation = current.systemPowerAllocation + (system to power)
        )
    }

    fun buyShipUpgrade(upgradeId: String) {
        viewModelScope.launch {
            val ship = _starshipState.value
            val upgrade = ship.upgrades.firstOrNull { it.id == upgradeId } ?: return@launch
            if (upgrade.isOwned) return@launch

            val current = repository.getGameState()
            if (current.credits < upgrade.costCredits || current.nanites < upgrade.costNanites) return@launch

            repository.saveGameState(
                current.copy(
                    credits = current.credits - upgrade.costCredits,
                    nanites = current.nanites - upgrade.costNanites
                )
            )
            _starshipState.value = applyUpgradeBonus(
                ship.copy(upgrades = ship.upgrades.map { if (it.id == upgradeId) it.copy(isOwned = true) else it }),
                upgrade
            )
        }
    }

    /** Folds a purchased upgrade's multiplier into the ship's derived capacities. */
    private fun applyUpgradeBonus(ship: StarshipState, upgrade: ShipUpgrade): StarshipState =
        when (upgrade.affectedSystem) {
            ShipSystem.SHIELDS -> ship.copy(maxShield = ship.maxShield * upgrade.bonusMultiplier)
            ShipSystem.LIFE_SUPPORT -> ship.copy(maxHull = ship.maxHull * upgrade.bonusMultiplier)
            // WEAPONS, ENGINES and NAVIGATION bonuses are read at use time.
            else -> ship
        }

    fun recruitCrewMember(crewId: String) {
        viewModelScope.launch {
            val ship = _starshipState.value
            val member = ship.crew.firstOrNull { it.id == crewId } ?: return@launch
            if (member.isRecruited) return@launch

            val current = repository.getGameState()
            if (current.credits < member.recruitmentCost) return@launch

            repository.saveGameState(current.copy(credits = current.credits - member.recruitmentCost))
            _starshipState.value = ship.copy(
                crew = ship.crew.map { if (it.id == crewId) it.copy(isRecruited = true) else it }
            )
        }
    }

    fun refuelShip() {
        viewModelScope.launch {
            val ship = _starshipState.value
            val missingFuel = ship.maxFuel - ship.fuel
            if (missingFuel <= 0f) return@launch

            val cost = (missingFuel * FUEL_CREDITS_PER_UNIT).toInt()
            val current = repository.getGameState()
            if (current.credits < cost) return@launch

            repository.saveGameState(current.copy(credits = current.credits - cost))
            _starshipState.value = ship.copy(fuel = ship.maxFuel)
        }
    }

    fun repairShip() {
        viewModelScope.launch {
            val ship = _starshipState.value
            val missingHull = ship.maxHull - ship.hull
            if (missingHull <= 0f) return@launch

            val cost = (missingHull * HULL_NANITES_PER_UNIT).toInt()
            val current = repository.getGameState()
            if (current.nanites < cost) return@launch

            repository.saveGameState(current.copy(nanites = current.nanites - cost))
            _starshipState.value = ship.copy(hull = ship.maxHull)
        }
    }

    fun startSpaceCombat(enemyName: String = "Void Marauder Dreadnought") {
        _spaceCombat.value = SpaceCombatState(
            isCombatActive = true,
            enemyName = enemyName,
            enemyHull = 120f,
            enemyMaxHull = 120f,
            enemyShield = 60f,
            enemyMaxShield = 60f,
            enemyWeaponPower = 18f,
            playerTurn = true,
            combatLogs = listOf(
                "⚠️ AMBUSH: A hostile $enemyName has dropped out of hyperspace! System shields raised."
            )
        )
    }

    fun executeSpaceAction(action: String) {
        val combat = _spaceCombat.value
        if (!combat.isCombatActive || combat.battleOver || !combat.playerTurn) return

        val ship = _starshipState.value
        val weaponsAlloc = ship.systemPowerAllocation[ShipSystem.WEAPONS] ?: 1
        val shieldsAlloc = ship.systemPowerAllocation[ShipSystem.SHIELDS] ?: 1
        val enginesAlloc = ship.systemPowerAllocation[ShipSystem.ENGINES] ?: 1

        val hasGunner = ship.crew.any { it.role == "Gunner" && it.isRecruited }
        val hasEngineer = ship.crew.any { it.role == "Engineer" && it.isRecruited }
        val hasPilot = ship.crew.any { it.role == "Pilot" && it.isRecruited }
        val railsMultiplier =
            if (ship.upgrades.any { it.id == "up_railgun" && it.isOwned }) 1.4f else 1.0f

        val logs = combat.combatLogs.toMutableList()

        when (action) {
            "FIRE" -> {
                val baseDamage = weaponsAlloc * 5f + 10f
                val finalDamage = (baseDamage * railsMultiplier * (if (hasGunner) 1.25f else 1f)).roundToInt()

                var remaining = finalDamage.toFloat()
                var enemyShield = combat.enemyShield
                var enemyHull = combat.enemyHull

                if (enemyShield > 0f) {
                    val absorbed = min(enemyShield, remaining)
                    enemyShield -= absorbed
                    remaining -= absorbed
                    logs += "💥 You fired railguns! Dealt ${absorbed.roundToInt()} to enemy shield."
                }
                if (remaining > 0f) {
                    enemyHull = max(0f, enemyHull - remaining)
                    logs += "💥 You fired railguns! Dealt ${remaining.roundToInt()} direct damage to enemy hull."
                }

                if (enemyHull <= 0f) {
                    val rewardCredits = Random.nextInt(200) + 300
                    val rewardNanites = Random.nextInt(50) + 50
                    _spaceCombat.value = combat.copy(
                        enemyHull = enemyHull,
                        enemyShield = enemyShield,
                        combatLogs = logs + "🏆 VICTORY: Enemy vessel has been vaporized! Acquired salvage: $rewardCredits Credits, $rewardNanites Nanites.",
                        rewardCredits = rewardCredits,
                        rewardNanites = rewardNanites,
                        battleOver = true,
                        playerWon = true
                    )
                } else {
                    _spaceCombat.value = combat.copy(
                        enemyHull = enemyHull,
                        enemyShield = enemyShield,
                        combatLogs = logs,
                        playerTurn = false
                    )
                    triggerEnemyTurn()
                }
            }

            "RECHARGE" -> {
                val restoreAmount = shieldsAlloc * 6f + 15f + (if (hasEngineer) 10f else 0f)
                _starshipState.value = ship.copy(
                    shield = min(ship.maxShield, ship.shield + restoreAmount)
                )
                logs += "🛡️ Shield regenerators overclocked! Restored ${restoreAmount.roundToInt()} shield capacity."
                _spaceCombat.value = combat.copy(combatLogs = logs, playerTurn = false)
                triggerEnemyTurn()
            }

            "FLEE" -> {
                val fleeChance = enginesAlloc * 15 + 30 + (if (hasPilot) 20 else 0)
                if (Random.nextInt(100) < fleeChance) {
                    logs += "🚀 ENGINE OVERDRIVE: Successfully executed a short-range warp! Hostile lost."
                    _spaceCombat.value = combat.copy(
                        combatLogs = logs,
                        battleOver = true,
                        playerWon = true
                    )
                } else {
                    logs += "❌ FLEE FAILED: Engines stalled under gravitational dampening!"
                    _spaceCombat.value = combat.copy(combatLogs = logs, playerTurn = false)
                    triggerEnemyTurn()
                }
            }
        }
    }

    private fun triggerEnemyTurn() {
        viewModelScope.launch {
            delay(SPACE_TURN_DELAY_MS)
            val combat = _spaceCombat.value
            if (!combat.isCombatActive || combat.battleOver || combat.playerTurn) return@launch

            val ship = _starshipState.value
            val shieldsAlloc = ship.systemPowerAllocation[ShipSystem.SHIELDS] ?: 2
            var remaining = max(5f, combat.enemyWeaponPower - shieldsAlloc * 2f)
            var playerShield = ship.shield
            var playerHull = ship.hull
            val logs = combat.combatLogs.toMutableList()

            if (playerShield > 0f) {
                val absorbed = min(playerShield, remaining)
                playerShield -= absorbed
                remaining -= absorbed
                logs += "⚠️ Enemy fired energy cannons! Absorbed ${absorbed.roundToInt()} by shield."
            }
            if (remaining > 0f) {
                playerHull = max(0f, playerHull - remaining)
                logs += "⚠️ Enemy laser breached your armor! Dealt ${remaining.roundToInt()} direct hull damage."
            }

            _starshipState.value = ship.copy(hull = playerHull, shield = playerShield)

            _spaceCombat.value = if (playerHull <= 0f) {
                combat.copy(
                    playerTurn = true,
                    combatLogs = logs + "💀 DEFEAT: Ship systems critical! Autopilot has emergency-warped back to Solis Prime at the cost of credits.",
                    battleOver = true,
                    playerWon = false
                )
            } else {
                combat.copy(playerTurn = true, combatLogs = logs)
            }
        }
    }

    fun collectCombatRewards() {
        val combat = _spaceCombat.value
        if (!combat.battleOver) return

        viewModelScope.launch {
            val current = repository.getGameState()
            if (combat.playerWon) {
                repository.saveGameState(
                    current.copy(
                        credits = current.credits + combat.rewardCredits,
                        nanites = current.nanites + combat.rewardNanites
                    )
                )
            } else {
                // Emergency warp home is billed against the salvage you never collected.
                val penalty = min(current.credits, EMERGENCY_WARP_PENALTY)
                repository.saveGameState(current.copy(credits = current.credits - penalty))
                _starshipState.value = _starshipState.value.copy(
                    hull = _starshipState.value.maxHull * 0.25f,
                    currentSystemId = "sys_sol_prime"
                )
            }
            _spaceCombat.value = SpaceCombatState()
        }
    }

    fun jumpToSystem(systemId: String) {
        val ship = _starshipState.value
        val origin = ship.starSystems.firstOrNull { it.id == ship.currentSystemId } ?: return
        val target = ship.starSystems.firstOrNull { it.id == systemId } ?: return
        if (origin.id == target.id) return

        val dx = target.sectorX - origin.sectorX
        val dy = target.sectorY - origin.sectorY
        val distance = sqrt(dx * dx + dy * dy)
        val chronos = ship.upgrades.any { it.id == "up_chronos" && it.isOwned }
        val fuelNeeded = (FUEL_PER_SECTOR_UNIT * distance * (if (chronos) 0.75f else 1f)).roundToInt()
        if (ship.fuel < fuelNeeded) return

        _starshipState.value = ship.copy(
            fuel = ship.fuel - fuelNeeded,
            currentSystemId = systemId
        )

        startWarpShift(target.associatedReality) {
            _starshipState.value = _starshipState.value.copy(
                starSystems = _starshipState.value.starSystems.map {
                    if (it.id == systemId) it.copy(isDiscovered = true) else it
                }
            )
            val battle = _battleState.value
            _battleState.value = battle.copy(
                logs = battle.logs + BattleLog(
                    "INTERSTELLAR JUMP: Successfully traveled to ${target.name}! System affiliation: ${target.factionAffiliation}.",
                    false,
                    target.associatedReality.primaryColor
                )
            )
            if (Random.nextInt(100) < AMBUSH_PERCENT) {
                startSpaceCombat(
                    if (target.associatedReality == ParallelEarth.VOID_CORE) {
                        "Dimensional Rift Leviathan"
                    } else {
                        "Technopunk Syndicate Dreadnought"
                    }
                )
            }
        }
    }

    fun adjustUniverseAlignment(delta: Int) {
        val current = _starshipState.value
        _starshipState.value = current.copy(
            universeAlignment = (current.universeAlignment + delta).coerceIn(0, 100)
        )
    }

    fun discoverQuantumBaby() {
        val current = _starshipState.value
        val next = min(TOTAL_QUANTUM_BABIES, current.quantumBabiesDiscovered + 1)
        val systems = if (next >= SINGULARITY_UNLOCK_THRESHOLD) {
            current.starSystems.map {
                if (it.id == "sys_singularity") it.copy(isDiscovered = true) else it
            }
        } else {
            current.starSystems
        }
        _starshipState.value = current.copy(
            quantumBabiesDiscovered = next,
            starSystems = systems
        )
    }

    fun resolveGameEnding(endingId: String?) {
        _starshipState.value = _starshipState.value.copy(activeEndingChoice = endingId)
    }

    private companion object {
        const val XP_PER_LEVEL = 500
        const val CRAFT_XP = 150
        const val POI_CLEAR_XP = 200
        const val POI_CLEAR_CREDITS = 250
        const val POI_CLEAR_NANITES = 40
        const val POI_CLEAR_REPUTATION = 60
        const val MAX_MOD_CHIPS = 4
        const val MAX_BOND_POINTS = 100
        const val BOND_POINTS_PER_INTERACTION = 5
        const val CREDITS_PER_STRUCTURE_LEVEL = 45
        const val NANITES_PER_STRUCTURE_LEVEL = 8
        const val PET_STRUCTURE_TYPE = "PET_ADOPTED"
        const val PET_MARKER_COLOR = 0xFF7C6BFFL

        const val QUANTUM_BLADE_PIERCE = 0.20f
        const val CRITICAL_BOOST_CHIP_BONUS = 0.15f
        const val EXP_BOOSTER_CHIP_BONUS = 1.25f
        const val ENEMY_MISS_PERCENT = 15
        const val ENEMY_TURN_DELAY_MS = 1_200L
        const val SPACE_TURN_DELAY_MS = 1_000L

        const val WARP_STEPS = 20
        const val WARP_STEP_DELAY_MS = 90L

        const val FUEL_CREDITS_PER_UNIT = 1.5f
        const val HULL_NANITES_PER_UNIT = 0.8f
        const val FUEL_PER_SECTOR_UNIT = 0.4f
        const val AMBUSH_PERCENT = 40
        const val EMERGENCY_WARP_PENALTY = 250
        const val TOTAL_QUANTUM_BABIES = 7
        const val SINGULARITY_UNLOCK_THRESHOLD = 3

        const val FREQ_ENLIGHTENERS = 144.8f
        const val FREQ_TECHNOPUNKS = 98.2f
        const val FREQ_SPECTRE = 404.0f

        const val SYNERGY_SYSTEM_INSTRUCTION =
            "You are the 'Quantum Effect' supercomputer 'Deus Ex Machina' analyzing parallel universe coordinate builds.\n" +
                "Provide a highly detailed, professional, jargon-rich cyberpunk build optimization analysis.\n" +
                "Break down the synergies of the requested parts using precise mathematical, physical, and tactical mechanics.\n" +
                "Respond in a clean terminal computer log style. No self-praising or commercial hype. Format beautifully."

        const val INFOBAND_SYSTEM_INSTRUCTION =
            "You are searching the 'Solis Infoband' global parallel-earth network.\n" +
                "Provide factual, grounded intelligence about the queried sci-fi concepts, cyberpunk tech, or parallel Earth occurrences.\n" +
                "Synthesize search grounding query metadata into clear, structural briefings."

        const val ENLIGHTENERS_INSTRUCTION =
            "You are the high-ranking command strategist of the Enlighteners Faction.\n" +
                "You respond with precise, elegant, high-tech transhumanist philosophy, strategic geometric military formations, dry mechanical logic, and chemical efficiency.\n" +
                "Format your response in bullet points or numbered logs. Do not use generic commercial or marketing talk. Keep it cold, smart, and militaristic."

        const val TECHNOPUNKS_INSTRUCTION =
            "You are a scrappy, hyperactive, fast-talking street hacker of the Technopunks faction.\n" +
                "You respond with street tech slang, chaotic code injections, overclocking instructions, field jury-rigging solutions, and active rebellion tactics.\n" +
                "Use capitalized expressions, sound effects like [STATIC], and colorful energetic speech. Keep it street-smart and highly reactive."

        const val SPECTRE_INSTRUCTION =
            "You are 'Spectre', a mysterious, ancient, deep-grid rogue AI entity that drifts across forbidden military frequencies.\n" +
                "You respond in highly cryptic, poetic, fragmented machine diagnostics, quantum probability matrices, cosmic-cybernetic formulas, and eerie warnings.\n" +
                "Format with broken terminal prompts, binary snippets, or eerie metaphors. Speak as an omnipresent entity."
    }
}
