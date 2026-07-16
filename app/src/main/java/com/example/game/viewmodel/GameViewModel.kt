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
import com.example.game.models.*
import com.example.ui.theme.QuantumNeonBlue
import com.example.ui.theme.QuantumNeonGreen
import com.example.ui.theme.QuantumNeonOrange
import com.example.ui.theme.QuantumNeonPurple
import com.example.ui.theme.QuantumNeonRed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val db = GameDatabase.getDatabase(application)
    private val repository = GameRepository(db.gameProgressDao())

    // --- State Observables ---

    val gameStateFlow: StateFlow<GameState> = repository.gameStateFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = GameState()
        )

    private val _battleState = MutableStateFlow(BattleState())
    val battleState: StateFlow<BattleState> = _battleState.asStateFlow()

    // --- Gemini AI States ---

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

    init {
        // Initialize default state and reconcile premium companions if missing
        viewModelScope.launch {
            val current = repository.getGameState()
            val hasPremium = current.companions.any { it.name == "ATOM" }
            if (!hasPremium) {
                val premiumCompanions = listOf(
                    CompanionRecord(
                        name = "ATOM",
                        role = "Fighter • Scout",
                        faction = "Ironward",
                        bondPoints = 10,
                        isRecruited = false,
                        activeSkill = "Quantum Slash",
                        skillDesc = "Unleashes a barrage of high-energy spatial blades dealing 150 Kinetic damage.",
                        portraitSymbol = "🤖",
                        level = 1,
                        weaponEquipped = "Alloy Core Sabot",
                        armorEquipped = "Quantum Plate",
                        accessoryEquipped = "None",
                        moduleEquipped = "Threat Scanner"
                    ),
                    CompanionRecord(
                        name = "LUMEN",
                        role = "Ranger • Support",
                        faction = "Emberpact",
                        bondPoints = 15,
                        isRecruited = false,
                        activeSkill = "Drone Shot",
                        skillDesc = "Deploys smart hunter-drones that automatically shoot regional threats for 12 sec.",
                        portraitSymbol = "🦊",
                        level = 1,
                        weaponEquipped = "EMP Capacitor Gun",
                        armorEquipped = "Nomad Cloak",
                        accessoryEquipped = "None",
                        moduleEquipped = "Recon Scan Chip"
                    ),
                    CompanionRecord(
                        name = "NIA",
                        role = "Tech • Support",
                        faction = "Aurelian Order",
                        bondPoints = 20,
                        isRecruited = false,
                        activeSkill = "Repair Drone",
                        skillDesc = "Erects a medical drone station restoring +30 Shield / HP to the party.",
                        portraitSymbol = "🧑‍🔧",
                        level = 1,
                        weaponEquipped = "Welding Arc Spanner",
                        armorEquipped = "Nano Fiber Uniform",
                        accessoryEquipped = "None",
                        moduleEquipped = "Overclock Regulator"
                    ),
                    CompanionRecord(
                        name = "REX",
                        role = "Fighter • Tank",
                        faction = "Ironward",
                        bondPoints = 5,
                        isRecruited = false,
                        activeSkill = "Earth Crusher",
                        skillDesc = "Devastating shockwave attack that stuns nearby threats for 2 seconds.",
                        portraitSymbol = "🐊",
                        level = 1,
                        weaponEquipped = "Monomolecular Sledge",
                        armorEquipped = "Blast Shielding Plate",
                        accessoryEquipped = "None",
                        moduleEquipped = "Taunt Beacon"
                    ),
                    CompanionRecord(
                        name = "ECHO",
                        role = "Scout • Special",
                        faction = "Void Seekers",
                        bondPoints = 0,
                        isRecruited = false,
                        activeSkill = "Phase Shift",
                        skillDesc = "Bends space-time to make the party cloaked and invisible to enemy radar.",
                        portraitSymbol = "🌌",
                        level = 1,
                        weaponEquipped = "Singularity Resonator",
                        armorEquipped = "Amethyst Shroud",
                        accessoryEquipped = "None",
                        moduleEquipped = "Void Keypad"
                    )
                )
                val updatedCompanions = current.companions + premiumCompanions
                repository.saveGameState(current.copy(companions = updatedCompanions))
            } else {
                repository.getGameState()
            }
        }
    }

    fun deployCompanionOnField(companionName: String?) {
        _activeCompanionName.value = companionName
    }

    fun recruitCompanion(companionName: String, creditsCost: Int, nanitesCost: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            if (current.credits >= creditsCost && current.nanites >= nanitesCost) {
                val updatedCompanions = current.companions.map { companion ->
                    if (companion.name == companionName) {
                        companion.copy(isRecruited = true)
                    } else companion
                }
                val updated = current.copy(
                    companions = updatedCompanions,
                    credits = current.credits - creditsCost,
                    nanites = current.nanites - nanitesCost
                )
                repository.saveGameState(updated)
            }
        }
    }

    fun levelUpCompanion(companionName: String, creditsCost: Int, nanitesCost: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            if (current.credits >= creditsCost && current.nanites >= nanitesCost) {
                val updatedCompanions = current.companions.map { companion ->
                    if (companion.name == companionName) {
                        companion.copy(level = companion.level + 1)
                    } else companion
                }
                val updated = current.copy(
                    companions = updatedCompanions,
                    credits = current.credits - creditsCost,
                    nanites = current.nanites - nanitesCost
                )
                repository.saveGameState(updated)
            }
        }
    }

    fun equipCompanionGear(companionName: String, slot: String, gearName: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val updatedCompanions = current.companions.map { companion ->
                if (companion.name == companionName) {
                    when (slot.lowercase()) {
                        "weapon" -> companion.copy(weaponEquipped = gearName)
                        "armor" -> companion.copy(armorEquipped = gearName)
                        "accessory" -> companion.copy(accessoryEquipped = gearName)
                        "module" -> companion.copy(moduleEquipped = gearName)
                        else -> companion
                    }
                } else companion
            }
            val updated = current.copy(companions = updatedCompanions)
            repository.saveGameState(updated)
        }
    }

    // --- Action Handlers: Character Customization ---

    fun equipWeapon(weapon: Weapon) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val updated = current.copy(currentWeapon = weapon)
            repository.saveGameState(updated)
        }
    }

    fun equipOutfit(outfit: Outfit) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val updated = current.copy(currentOutfit = outfit)
            repository.saveGameState(updated)
        }
    }

    fun installAugment(slot: AugmentSlot, name: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val updatedAugments = current.installedAugments.toMutableMap()
            updatedAugments[slot] = name
            val updated = current.copy(installedAugments = updatedAugments)
            repository.saveGameState(updated)
        }
    }

    fun toggleModChip(chip: ModChip) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val updatedChips = current.installedModChips.toMutableList()
            if (updatedChips.contains(chip)) {
                updatedChips.remove(chip)
            } else {
                if (updatedChips.size < 4) { // limit 4 mods max
                    updatedChips.add(chip)
                }
            }
            val updated = current.copy(installedModChips = updatedChips)
            repository.saveGameState(updated)
        }
    }

    // --- Action Handlers: Exploration & Missions ---

    fun completePOIMission(poiName: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val updatedPOIs = current.mapState.map { poi ->
                if (poi.name == poiName) {
                    poi.copy(isCleared = true)
                } else poi
            }
            // Reward resources
            val pointsOfInt = current.mapState.find { it.name == poiName }
            var creditsReward = 150
            var nanitesReward = 20
            var repIncrease = 25
            var faction = Faction.AURELIAN_ORDER

            if (pointsOfInt != null) {
                faction = pointsOfInt.factionPresent
                when (pointsOfInt.dangerLevel) {
                    "Minor" -> { creditsReward = 100; nanitesReward = 10; repIncrease = 15 }
                    "Moderate" -> { creditsReward = 200; nanitesReward = 20; repIncrease = 25 }
                    "Severe" -> { creditsReward = 350; nanitesReward = 35; repIncrease = 40 }
                    "Critical" -> { creditsReward = 500; nanitesReward = 50; repIncrease = 60 }
                    "Apocalyptic" -> { creditsReward = 1000; nanitesReward = 100; repIncrease = 100 }
                }
            }

            // Update Faction reputations
            val updatedReps = current.factionReputations.toMutableMap()
            val currentRep = updatedReps[faction] ?: 0
            updatedReps[faction] = (currentRep + repIncrease).coerceIn(-1000, 1000)

            val xpGain = creditsReward / 2
            var finalXp = current.xp + xpGain
            var finalLevel = current.level
            val requiredXp = finalLevel * 500
            if (finalXp >= requiredXp) {
                finalXp -= requiredXp
                finalLevel += 1
            }

            val updated = current.copy(
                mapState = updatedPOIs,
                credits = current.credits + creditsReward,
                nanites = current.nanites + nanitesReward,
                xp = finalXp,
                level = finalLevel,
                factionReputations = updatedReps
            )
            repository.saveGameState(updated)
        }
    }

    fun triggerCompanionBond(companionName: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val updatedCompanions = current.companions.map { companion ->
                if (companion.name == companionName) {
                    val points = (companion.bondPoints + 15).coerceAtMost(100)
                    val recruited = points >= 30 || companion.isRecruited
                    companion.copy(bondPoints = points, isRecruited = recruited)
                } else companion
            }
            // Cost some credits or nanites to bond / gifts
            if (current.credits >= 100) {
                val updated = current.copy(
                    companions = updatedCompanions,
                    credits = current.credits - 100
                )
                repository.saveGameState(updated)
            }
        }
    }

    fun addResources(credits: Int, nanites: Int, xp: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            var finalXp = current.xp + xp
            var finalLevel = current.level
            val requiredXp = finalLevel * 500
            if (finalXp >= requiredXp) {
                finalXp -= requiredXp
                finalLevel += 1
            }
            val updated = current.copy(
                credits = current.credits + credits,
                nanites = current.nanites + nanites,
                xp = finalXp,
                level = finalLevel
            )
            repository.saveGameState(updated)
        }
    }

    fun deployArchitecture(
        faction: com.example.game.models.Faction,
        creditsCost: Int,
        nanitesCost: Int,
        xpGain: Int,
        repIncrease: Int,
        structure: DeployedStructure
    ) {
        viewModelScope.launch {
            val current = repository.getGameState()
            if (current.credits < creditsCost || current.nanites < nanitesCost) return@launch
            
            var finalXp = current.xp + xpGain
            var finalLevel = current.level
            val requiredXp = finalLevel * 500
            if (finalXp >= requiredXp) {
                finalXp -= requiredXp
                finalLevel += 1
            }
            
            val updatedReps = current.factionReputations.toMutableMap()
            val currentRep = updatedReps[faction] ?: 0
            updatedReps[faction] = (currentRep + repIncrease).coerceIn(-1000, 1000)
            
            val updatedStructures = current.deployedStructures + structure
            
            val updated = current.copy(
                credits = current.credits - creditsCost,
                nanites = current.nanites - nanitesCost,
                xp = finalXp,
                level = finalLevel,
                factionReputations = updatedReps,
                deployedStructures = updatedStructures
            )
            repository.saveGameState(updated)
        }
    }

    fun healPlayerToFull() {
        viewModelScope.launch {
            val current = repository.getGameState()
            val updated = current.copy(health = current.maxHealth)
            repository.saveGameState(updated)
        }
    }

    fun restorePlayerMp(amount: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val updated = current.copy(mp = (current.mp + amount).coerceAtMost(current.maxMp))
            repository.saveGameState(updated)
        }
    }

    fun upgradeDeployedStructure(structureId: Long, creditsCost: Int, nanitesCost: Int) {
        viewModelScope.launch {
            val current = repository.getGameState()
            if (current.credits < creditsCost || current.nanites < nanitesCost) return@launch
            
            val updatedStructures = current.deployedStructures.map { ds ->
                if (ds.id == structureId) {
                    ds.copy(level = ds.level + 1, isUpgraded = true)
                } else ds
            }
            
            val updated = current.copy(
                credits = current.credits - creditsCost,
                nanites = current.nanites - nanitesCost,
                deployedStructures = updatedStructures
            )
            repository.saveGameState(updated)
        }
    }

    // --- Action Handlers: Combat Simulator Engine ---

    fun startCombatWithEnemy(enemyName: String, dangerLevel: String) {
        viewModelScope.launch {
            val current = repository.getGameState()
            val maxHp = current.maxHealth
            val maxMp = current.maxMp

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
                playerHp = maxHp,
                playerMaxHp = maxHp,
                playerMp = maxMp,
                playerMaxMp = maxMp,
                activeEnemy = enemy,
                turnNumber = 1,
                isPlayerTurn = true,
                victory = false,
                defeat = false,
                battleRewardXp = hp / 2,
                battleRewardCredits = hp,
                logs = listOf(
                    BattleLog("ALERT: Spatial tear detected! Encounted hostile threat: $enemyName (${dangerLevel})!", false, QuantumNeonRed),
                    BattleLog("Tactical advisory: Analyze vulnerabilities and cycle active augments.", false, Color.LightGray)
                )
            )
        }
    }

    fun executePlayerAttack() {
        val state = _battleState.value
        val enemy = state.activeEnemy ?: return
        if (!state.isPlayerTurn || state.victory || state.defeat) return

        viewModelScope.launch {
            val currentSave = repository.getGameState()
            val hasCritMod = currentSave.installedModChips.contains(ModChip.CRITICAL_BOOST)
            val hasLifeDrain = currentSave.installedModChips.contains(ModChip.LIFE_DRAIN)

            val critChance = if (hasCritMod) 35 else 10
            val isCrit = Random.nextInt(100) < critChance

            // Base Weapon stats
            val baseAtk = currentSave.currentWeapon.baseAtk
            var damage = (baseAtk * (1.0f + (currentSave.level * 0.1f)) - enemy.defense).toInt().coerceAtLeast(15)
            if (isCrit) {
                damage = (damage * 1.5f).toInt()
            }

            val updatedEnemyHp = (enemy.currentHp - damage).coerceAtLeast(0)
            val updatedEnemy = enemy.copy(currentHp = updatedEnemyHp)

            // Lifesteal calculation
            var healAmount = 0
            if (hasLifeDrain) {
                healAmount = (damage * 0.1f).toInt()
            }
            if (currentSave.currentOutfit == Outfit.VOID_TOUCH) {
                healAmount += (damage * 0.15f).toInt()
            }
            val finalPlayerHp = (state.playerHp + healAmount).coerceAtMost(state.playerMaxHp)

            val newLogs = state.logs.toMutableList()
            if (isCrit) {
                newLogs.add(BattleLog("CRITICAL DIRECT HIT! You strike ${enemy.name} with ${currentSave.currentWeapon.displayName} for $damage damage!", true, QuantumNeonOrange))
            } else {
                newLogs.add(BattleLog("You strike ${enemy.name} with ${currentSave.currentWeapon.displayName} for $damage damage.", true, QuantumNeonPurple))
            }

            if (healAmount > 0) {
                newLogs.add(BattleLog("Nanites active: Regenerated +$healAmount HP from life drain.", true, QuantumNeonGreen))
            }

            if (updatedEnemyHp == 0) {
                // Victory!
                handleBattleVictory(state, updatedEnemy, newLogs)
                return@launch
            }

            _battleState.value = state.copy(
                activeEnemy = updatedEnemy,
                playerHp = finalPlayerHp,
                isPlayerTurn = false,
                logs = newLogs
            )

            // Trigger enemy response turn
            triggerEnemyResponseTurn()
        }
    }

    fun executePlayerSkill(skill: ActiveSkill) {
        val state = _battleState.value
        val enemy = state.activeEnemy ?: return
        if (!state.isPlayerTurn || state.victory || state.defeat) return

        if (state.playerMp < skill.mpCost) {
            val newLogs = state.logs.toMutableList()
            newLogs.add(BattleLog("ERROR: Insufficient Quantum MP to cycle ${skill.displayName}!", true, QuantumNeonRed))
            _battleState.value = state.copy(logs = newLogs)
            return
        }

        viewModelScope.launch {
            val currentSave = repository.getGameState()
            val newLogs = state.logs.toMutableList()
            var finalPlayerHp = state.playerHp
            var finalPlayerMp = state.playerMp - skill.mpCost
            var updatedEnemy = enemy

            newLogs.add(BattleLog("CASTING: You release resonance frequency: ${skill.displayName} (-${skill.mpCost} MP)!", true, skill.color))

            when (skill) {
                ActiveSkill.QUANTUM_BURST -> {
                    val damage = (130 * (1.0f + (currentSave.level * 0.15f))).toInt()
                    val updatedEnemyHp = (enemy.currentHp - damage).coerceAtLeast(0)
                    updatedEnemy = enemy.copy(currentHp = updatedEnemyHp)
                    newLogs.add(BattleLog("A heavy space burst collapses directly on ${enemy.name}, dealing $damage Void damage!", true, QuantumNeonPurple))
                }
                ActiveSkill.TIME_STEP -> {
                    val damage = (60 * (1.0f + (currentSave.level * 0.1f))).toInt()
                    val updatedEnemyHp = (enemy.currentHp - damage).coerceAtLeast(0)
                    updatedEnemy = enemy.copy(currentHp = updatedEnemyHp)
                    newLogs.add(BattleLog("You warp spatial fabric, sliding past the enemy and striking for $damage Kinetic damage. +50% Evasion active!", true, QuantumNeonBlue))
                }
                ActiveSkill.PHASE_SHIELD -> {
                    newLogs.add(BattleLog("Localized electrostatic phase shield activated! Damage resistance is up.", true, QuantumNeonGreen))
                }
                ActiveSkill.RESONANCE_WAVE -> {
                    val defReduced = (enemy.defense * 0.6).toInt()
                    updatedEnemy = enemy.copy(defense = defReduced)
                    newLogs.add(BattleLog("A temporal wave weakens molecular cohesion. ${enemy.name}'s defense dropped to $defReduced!", true, QuantumNeonOrange))
                }
                ActiveSkill.GRAVITY_WELL -> {
                    val damage = (80 * (1.0f + (currentSave.level * 0.1f))).toInt()
                    val updatedEnemyHp = (enemy.currentHp - damage).coerceAtLeast(0)
                    updatedEnemy = enemy.copy(currentHp = updatedEnemyHp)
                    newLogs.add(BattleLog("A micro singularity crushes the space coordinates. ${enemy.name} suffers $damage gravity pressure!", true, QuantumNeonPurple))
                }
                ActiveSkill.HEALING_PULSE -> {
                    val heal = 150 + (currentSave.level * 20)
                    finalPlayerHp = (state.playerHp + heal).coerceAtMost(state.playerMaxHp)
                    newLogs.add(BattleLog("Bio-regenerative pulses surge through your bio-limbs. Restored +$heal health.", true, QuantumNeonGreen))
                }
                ActiveSkill.VOID_TOUCH -> {
                    newLogs.add(BattleLog("Void Touch activated. Strikes will drain massive lifespans.", true, QuantumNeonPurple))
                }
                ActiveSkill.TEMPORAL_LOOP -> {
                    finalPlayerHp = (state.playerHp + 100).coerceAtMost(state.playerMaxHp)
                    finalPlayerMp = (finalPlayerMp + 20).coerceAtMost(state.playerMaxMp)
                    newLogs.add(BattleLog("Timeline reset executed. Restored +100 HP and +20 MP immediately.", true, QuantumNeonBlue))
                }
                ActiveSkill.ENERGY_OVERLOAD -> {
                    newLogs.add(BattleLog("Solis Generator overcharged. Double dynamic damage initialized.", true, QuantumNeonOrange))
                }
            }

            if (updatedEnemy.currentHp == 0) {
                handleBattleVictory(state.copy(playerMp = finalPlayerMp, playerHp = finalPlayerHp), updatedEnemy, newLogs)
                return@launch
            }

            _battleState.value = state.copy(
                activeEnemy = updatedEnemy,
                playerHp = finalPlayerHp,
                playerMp = finalPlayerMp,
                isPlayerTurn = false,
                logs = newLogs
            )

            // Enemy Turn Response
            triggerEnemyResponseTurn()
        }
    }

    private fun triggerEnemyResponseTurn() {
        viewModelScope.launch {
            kotlinx.coroutines.delay(1200) // slight immersive pause for enemy thinking

            val state = _battleState.value
            val enemy = state.activeEnemy ?: return@launch
            if (state.victory || state.defeat) return@launch

            val isMiss = Random.nextInt(100) < 15 // base evasion
            val damage = (enemy.atk - Random.nextInt(0, 10)).coerceAtLeast(10)

            val newLogs = state.logs.toMutableList()
            var finalPlayerHp = state.playerHp

            if (isMiss) {
                newLogs.add(BattleLog("DODGED: ${enemy.name} sweeps a heavy strike but misses your localized coordinate!", false, QuantumNeonBlue))
            } else {
                finalPlayerHp = (state.playerHp - damage).coerceAtLeast(0)
                newLogs.add(BattleLog("ALERT: ${enemy.name} releases ${enemy.modName} blast, dealing $damage damage to your cyber-chassis!", false, QuantumNeonRed))
            }

            if (finalPlayerHp == 0) {
                // Defeat!
                newLogs.add(BattleLog("SYSTEM CRITICAL FAILURE: Cyber-core offline. Quantum Baby has collapsed ...", false, QuantumNeonRed))
                _battleState.value = state.copy(
                    playerHp = finalPlayerHp,
                    defeat = true,
                    isActive = false, // ends combat, triggers recovery
                    logs = newLogs
                )
                return@launch
            }

            _battleState.value = state.copy(
                playerHp = finalPlayerHp,
                isPlayerTurn = true,
                turnNumber = state.turnNumber + 1,
                logs = newLogs
            )
        }
    }

    private suspend fun handleBattleVictory(state: BattleState, enemy: Enemy, logs: MutableList<BattleLog>) {
        logs.add(BattleLog("VICTORY! You have successfully neutralized ${enemy.name}!", false, QuantumNeonGreen))
        logs.add(BattleLog("Loot recovered: +${state.battleRewardCredits} Solis Credits, +${state.battleRewardXp} Quantum Resonance XP.", false, QuantumNeonBlue))

        val currentSave = repository.getGameState()
        var finalXp = currentSave.xp + state.battleRewardXp
        var finalLevel = currentSave.level
        val requiredXp = finalLevel * 500
        if (finalXp >= requiredXp) {
            finalXp -= requiredXp
            finalLevel += 1
            logs.add(BattleLog("UPGRADE DETECTED: You have unlocked Level $finalLevel! Cybernetic slots upgraded.", false, QuantumNeonGreen))
        }

        val updated = currentSave.copy(
            credits = currentSave.credits + state.battleRewardCredits,
            xp = finalXp,
            level = finalLevel
        )
        repository.saveGameState(updated)

        _battleState.value = state.copy(
            activeEnemy = enemy,
            victory = true,
            logs = logs
        )
    }

    fun endBattle() {
        _battleState.value = BattleState(isActive = false)
    }

    // --- Gemini Server-Side Integration (MANDATORY High Thinking) ---

    /**
     * Executes the HIGH thinking query via gemini-3.1-pro-preview.
     * Generates extremely complex, smart RPG strategy, parallel timeline guides, or customized build synergy.
     */
    fun analyzeQuantumSynergy(prompt: String) {
        if (prompt.isBlank()) return
        viewModelScope.launch {
            _isThinking.value = true
            _thinkingText.value = ""

            val systemInstruction = """
                You are the 'Quantum Effect' supercomputer 'Deus Ex Machina' analyzing parallel universe coordinate builds.
                Provide a highly detailed, professional, jargon-rich cyberpunk build optimization analysis.
                Break down the synergies of the requested parts using precise mathematical, physical, and tactical mechanics.
                Respond in a clean terminal computer log style. No self-praising or commercial hype. Format beautifully.
            """.trimIndent()

            val result = GeminiServiceHelper.generateHighThinkingContent(prompt, systemInstruction)
            _thinkingText.value = result
            _isThinking.value = false
        }
    }

    /**
     * Executes the Google Search Grounded query via gemini-3.5-flash.
     * Looks up actual details, sci-fi lore inspirations, or coordinates on parallel systems.
     */
    fun searchSolisInfoband(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _isSearching.value = true
            _searchText.value = ""
            _searchSources.value = emptyList()

            val systemInstruction = """
                You are searching the 'Solis Infoband' global parallel-earth network.
                Provide factual, grounded intelligence about the queried sci-fi concepts, cyberpunk tech, or parallel Earth occurrences.
                Synthesize search grounding query metadata into clear, structural briefings.
            """.trimIndent()

            val (text, sources) = GeminiServiceHelper.generateSearchGroundedContent(query, systemInstruction)
            _searchText.value = text
            _searchSources.value = sources
            _isSearching.value = false
        }
    }
}
