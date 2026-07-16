package com.example.game.db

import com.example.game.models.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

// --- Core Fully-Typed State of the Game ---

data class GameState(
    val playerName: String = "Quantum Baby",
    val level: Int = 1,
    val xp: Int = 0,
    val credits: Int = 1200,
    val nanites: Int = 150,
    val health: Int = 520,
    val maxHealth: Int = 520,
    val mp: Int = 80,
    val maxMp: Int = 80,
    val currentWeapon: Weapon = Weapon.QUANTUM_BLADE,
    val currentOutfit: Outfit = Outfit.DEFAULT,
    
    // Installed augment chips per slot
    val installedAugments: Map<AugmentSlot, String> = emptyMap(),
    val installedModChips: List<ModChip> = emptyList(),
    val factionReputations: Map<Faction, Int> = emptyMap(),
    val companions: List<CompanionRecord> = emptyList(),
    val mapState: List<PointOfInterest> = emptyList(),
    val deployedStructures: List<DeployedStructure> = emptyList()
)

class GameRepository(private val dao: GameProgressDao) {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Types for serialization
    private val augmentType = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
    private val modChipListType = Types.newParameterizedType(List::class.java, String::class.java)
    private val factionRepType = Types.newParameterizedType(Map::class.java, String::class.java, Int::class.javaObjectType)
    private val companionListType = Types.newParameterizedType(List::class.java, CompanionRecord::class.java)
    private val mapStateListType = Types.newParameterizedType(List::class.java, PointOfInterest::class.java)
    private val deployedStructuresListType = Types.newParameterizedType(List::class.java, DeployedStructure::class.java)

    private val augmentAdapter = moshi.adapter<Map<String, String>>(augmentType)
    private val modChipListAdapter = moshi.adapter<List<String>>(modChipListType)
    private val factionRepAdapter = moshi.adapter<Map<String, Int>>(factionRepType)
    private val companionListAdapter = moshi.adapter<List<CompanionRecord>>(companionListType)
    private val mapStateListAdapter = moshi.adapter<List<PointOfInterest>>(mapStateListType)
    private val deployedStructuresListAdapter = moshi.adapter<List<DeployedStructure>>(deployedStructuresListType)

    // Expose flows to UI
    val gameStateFlow: Flow<GameState> = dao.getProgressFlow().map { progress ->
        if (progress == null) {
            val defaultState = createDefaultGameState()
            saveGameState(defaultState)
            defaultState
        } else {
            mapProgressToState(progress)
        }
    }

    suspend fun getGameState(): GameState = withContext(Dispatchers.IO) {
        val progress = dao.getProgress()
        if (progress == null) {
            val defaultState = createDefaultGameState()
            saveGameState(defaultState)
            defaultState
        } else {
            mapProgressToState(progress)
        }
    }

    suspend fun saveGameState(state: GameState) = withContext(Dispatchers.IO) {
        val progress = mapStateToProgress(state)
        dao.saveProgress(progress)
    }

    suspend fun clearSaveProgress() = withContext(Dispatchers.IO) {
        dao.clearProgress()
    }

    // --- Helper Converters ---

    private fun mapProgressToState(progress: GameProgress): GameState {
        // Deserialize installed augments
        val augmentsMapRaw = try {
            augmentAdapter.fromJson(progress.installedAugmentsJson) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
        val augmentsMap = augmentsMapRaw.mapNotNull { entry ->
            val slot = try { AugmentSlot.valueOf(entry.key) } catch (e: Exception) { null }
            if (slot != null) slot to entry.value else null
        }.toMap()

        // Deserialize mod chips
        val modChipsListRaw = try {
            modChipListAdapter.fromJson(progress.installedModChipsJson) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
        val modChips = modChipsListRaw.mapNotNull { raw ->
            try { ModChip.valueOf(raw) } catch (e: Exception) { null }
        }

        // Deserialize reputations
        val factionRepRaw = try {
            factionRepAdapter.fromJson(progress.factionReputationsJson) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
        val reputations = factionRepRaw.mapNotNull { entry ->
            val faction = try { Faction.valueOf(entry.key) } catch (e: Exception) { null }
            if (faction != null) faction to entry.value else null
        }.toMap()

        // Deserialize companions
        val companions = try {
            companionListAdapter.fromJson(progress.companionsJson) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        // Deserialize map State
        val mapState = try {
            mapStateListAdapter.fromJson(progress.mapStateJson) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        // Deserialize deployed structures
        val deployedStructures = try {
            deployedStructuresListAdapter.fromJson(progress.deployedStructuresJson) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        val weapon = try { Weapon.valueOf(progress.currentWeapon) } catch (e: Exception) { Weapon.QUANTUM_BLADE }
        val outfit = try { Outfit.valueOf(progress.currentOutfit) } catch (e: Exception) { Outfit.DEFAULT }

        // Recalculate stats based on level, outfit and augments!
        val (finalHealth, finalMaxHealth, finalMp, finalMaxMp) = recalculateDynamicStats(
            progress.level,
            progress.health,
            progress.mp,
            outfit,
            augmentsMap
        )

        return GameState(
            playerName = progress.playerName,
            level = progress.level,
            xp = progress.xp,
            credits = progress.credits,
            nanites = progress.nanites,
            health = finalHealth,
            maxHealth = finalMaxHealth,
            mp = finalMp,
            maxMp = finalMaxMp,
            currentWeapon = weapon,
            currentOutfit = outfit,
            installedAugments = augmentsMap,
            installedModChips = modChips,
            factionReputations = reputations,
            companions = companions,
            mapState = mapState,
            deployedStructures = deployedStructures
        )
    }

    private fun mapStateToProgress(state: GameState): GameProgress {
        val augmentsJson = augmentAdapter.toJson(state.installedAugments.mapKeys { it.key.name })
        val modChipsJson = modChipListAdapter.toJson(state.installedModChips.map { it.name })
        val factionRepJson = factionRepAdapter.toJson(state.factionReputations.mapKeys { it.key.name })
        val companionsJson = companionListAdapter.toJson(state.companions)
        val mapStateJson = mapStateListAdapter.toJson(state.mapState)
        val deployedStructuresJson = deployedStructuresListAdapter.toJson(state.deployedStructures)

        return GameProgress(
            playerName = state.playerName,
            level = state.level,
            xp = state.xp,
            credits = state.credits,
            nanites = state.nanites,
            health = state.health,
            maxHealth = state.maxHealth,
            mp = state.mp,
            maxMp = state.maxMp,
            currentWeapon = state.currentWeapon.name,
            currentOutfit = state.currentOutfit.name,
            installedAugmentsJson = augmentsJson,
            installedModChipsJson = modChipsJson,
            factionReputationsJson = factionRepJson,
            companionsJson = companionsJson,
            mapStateJson = mapStateJson,
            deployedStructuresJson = deployedStructuresJson
        )
    }

    // --- Dynamic Character Stats Engine ---

    fun recalculateDynamicStats(
        level: Int,
        baseHealth: Int,
        baseMp: Int,
        outfit: Outfit,
        augments: Map<AugmentSlot, String>
    ): List<Int> {
        val calculatedMaxHp = 450 + (level * 70) // Level base HP scale
        val calculatedMaxMp = 60 + (level * 20)  // Level base MP scale

        var augmentHp = 0
        var augmentMp = 0

        // Accumulate bonuses from augments
        augments.forEach { (slot, name) ->
            val augment = getAugmentData(name, slot)
            if (augment != null) {
                augmentHp += augment.hpBonus
                augmentMp += augment.mpBonus
            }
        }

        // Apply outfit modifiers
        val maxHp = ((calculatedMaxHp + augmentHp) * outfit.hpMult).toInt()
        val maxMp = (calculatedMaxMp + augmentMp) // academic outfit gets +25% directly in calculation
        val finalMaxMp = if (outfit == Outfit.ACADEMIC) (maxMp * 1.25).toInt() else maxMp

        // Ensure current stats are bound by max capacities
        val health = if (baseHealth > maxHp || baseHealth <= 0) maxHp else baseHealth
        val mp = if (baseMp > finalMaxMp || baseMp <= 0) finalMaxMp else baseMp

        return listOf(health, maxHp, mp, finalMaxMp)
    }

    // --- Predefined Database of Augments ---

    fun getAugmentData(name: String, slot: AugmentSlot): AugmentChip? {
        return augmentList.find { it.name == name && it.slot == slot }
    }

    val augmentList = listOf(
        // Cranial
        AugmentChip("Neural Processor", AugmentSlot.CRANIAL, "+15 ATK, overclock mental reasoning", atkBonus = 15, magBonus = 10),
        AugmentChip("Memory Expansion", AugmentSlot.CRANIAL, "+40 MP, increases timeline recall", mpBonus = 40),
        AugmentChip("Focus Amplifier", AugmentSlot.CRANIAL, "+20 MAG, concentrates resonance waves", magBonus = 20),
        AugmentChip("Vision Suite", AugmentSlot.CRANIAL, "+10 LCK, highlights tactical anomalies", lckBonus = 10),
        // Torso
        AugmentChip("Signal Heart", AugmentSlot.TORSO, "+100 HP, enhances adrenaline pumps", hpBonus = 100),
        AugmentChip("Lung Reinforcement", AugmentSlot.TORSO, "+20 DEF, mitigates toxic atmospheres", defBonus = 20),
        AugmentChip("Nano Fiber Mesh", AugmentSlot.TORSO, "+50 HP, absorbing kinetic shock", hpBonus = 50, defBonus = 10),
        AugmentChip("Bio-Reactor", AugmentSlot.TORSO, "+20 MP & +15 ATK, fuels biomechanics", mpBonus = 20, atkBonus = 15),
        // Arms
        AugmentChip("Cybernetic Muscles", AugmentSlot.ARMS, "+30 ATK, hydraulic punches", atkBonus = 30),
        AugmentChip("Smart Servo-Joint", AugmentSlot.ARMS, "+15 SPD, quick combat draw", spdBonus = 15),
        AugmentChip("Tactical Interface", AugmentSlot.ARMS, "+15 ATK & +10 MAG, lock-on targeting", atkBonus = 15, magBonus = 10),
        AugmentChip("Weapon Mount", AugmentSlot.ARMS, "+25 ATK, heavy stabilizer chassis", atkBonus = 25),
        // Legs
        AugmentChip("Magnetic Boosters", AugmentSlot.LEGS, "+25 SPD, slide on scrap metals", spdBonus = 25),
        AugmentChip("Shock Absorbers", AugmentSlot.LEGS, "+50 HP, safe leap down from buildings", hpBonus = 50),
        AugmentChip("Graviton Stabilizer", AugmentSlot.LEGS, "+15 DEF & +10 LCK, gravity defying steps", defBonus = 15, lckBonus = 10),
        AugmentChip("Silent Step System", AugmentSlot.LEGS, "+20 SPD, sound dampening soles", spdBonus = 20),
        // Sensory
        AugmentChip("Quantum Eye", AugmentSlot.SENSORY, "+30 MAG, sees light wave emissions", magBonus = 30),
        AugmentChip("Audio Enhancer", AugmentSlot.SENSORY, "+10 SPD & +10 LCK, listens to radio bands", spdBonus = 10, lckBonus = 10),
        AugmentChip("Threat Scanner", AugmentSlot.SENSORY, "+15 DEF, tactical danger overlay", defBonus = 15),
        AugmentChip("Datajack", AugmentSlot.SENSORY, "+15 MAG, instant network link", magBonus = 15),
        // Dermal
        AugmentChip("Adaptive Armor Skin", AugmentSlot.DERMAL, "+30 DEF, scales resist bullets", defBonus = 30),
        AugmentChip("Thermal Regulation", AugmentSlot.DERMAL, "+50 HP, works in lava/glacier biomes", hpBonus = 50),
        AugmentChip("Nano-Heal Layer", AugmentSlot.DERMAL, "Regenerates HP constantly, +15 DEF", defBonus = 15),
        AugmentChip("Camouflage Mesh", AugmentSlot.DERMAL, "+15 SPD, adapts color to shadows", spdBonus = 15),
        // Quantum Core
        AugmentChip("Resonance Core", AugmentSlot.QUANTUM_CORE, "+20 ATK, +20 MAG, stable alignment", atkBonus = 20, magBonus = 20),
        AugmentChip("Quantum Capacitor", AugmentSlot.QUANTUM_CORE, "+50 MP, condensed energy pool", mpBonus = 50),
        AugmentChip("Energy Conduit", AugmentSlot.QUANTUM_CORE, "+100 HP, rapid current distribution", hpBonus = 100),
        AugmentChip("Warp Stabilizer", AugmentSlot.QUANTUM_CORE, "+15 LCK, keeps coordinates safe", lckBonus = 15)
    )

    // --- Campaign Initialization Data ---

    private fun createDefaultGameState(): GameState {
        val mapPOIs = listOf(
            PointOfInterest("Neo Solis Citadel", Biome.GRASSLANDS, "The shining capital of the united earth. Controlled by Enlighteners, high-tech and heavily monitored.", "Minor", "Scrap Metal", factionPresent = Faction.AURELIAN_ORDER),
            PointOfInterest("Aurelian Archive", Biome.GRASSLANDS, "The majestic archives holding the original quantum teleportation coordinates.", "Moderate", "Data Fragment", factionPresent = Faction.AURELIAN_ORDER),
            PointOfInterest("Frostveil Outpost", Biome.SNOW, "A freezing research outpost on the polar ice cap. Secret bounty hunters hide here.", "Moderate", "Nano Fiber", factionPresent = Faction.SILENT_VEIL),
            PointOfInterest("Emberfall Volcano", Biome.DESERT, "A flowing volcanic fissure mining raw dark energy and lava minerals.", "Severe", "Void Crystal", factionPresent = Faction.EMBERPACT),
            PointOfInterest("Ironward Megafactory", Biome.VOLCANIC, "A giant automated mechanical plant producing security drone armors.", "Critical", "Alloy Plate", true, "Omega Prime Sentinal", Faction.IRONWARD),
            PointOfInterest("The Void Observatory", Biome.VOID_SEA, "An ancient ruins floating directly inside the dark-matter warp rift.", "Apocalyptic", "Singularity Shard", true, "Null Colossus", Faction.VOID_SEEKERS),
            PointOfInterest("Black Market Bazaar", Biome.RUINS, "A hidden backstreet block of crime, blackmarket cybernetics, and outlaws.", "Moderate", "Quantum Core", factionPresent = Faction.SILENT_VEIL)
        )

        val companions = listOf(
            CompanionRecord("Lyra", "The Scout", "Aurelian Order", bondPoints = 20, isRecruited = true, activeSkill = "Recon Sweep", skillDesc = "Mark all enemies on map, increasing player hit accuracy by 30% for 3 turns.", "🏹"),
            CompanionRecord("Drox", "The Bruiser", "Ironward", bondPoints = 0, isRecruited = false, activeSkill = "Shield Wall", skillDesc = "Deploys a reinforced energy dome, absorbing 250 incoming damage.", "🦖"),
            CompanionRecord("Nix", "The Techwiz", "Emberpact", bondPoints = 10, isRecruited = true, activeSkill = "Hacking Pulse", skillDesc = "Sends electric disruptors, disabling robot enemies for 1 turn.", "🧑‍💻"),
            CompanionRecord("Vayn", "The Shadow", "Void Seekers", bondPoints = 0, isRecruited = false, activeSkill = "Shadow Step", skillDesc = "Teleport behind enemies, delivering critical backstabs with 100% pierce.", "🥷"),
            CompanionRecord("Elsi", "The Medic", "Silent Veil", bondPoints = 15, isRecruited = true, activeSkill = "Restore Wave", skillDesc = "Heals all active allies for 200 health immediately.", "🩺"),
            CompanionRecord("Grum", "The Engineer", "Emberpact", bondPoints = 0, isRecruited = false, activeSkill = "Deploy Turret", skillDesc = "Erects an automated gatling laser turret firing 50 damage rounds.", "🔧")
        )

        val defaultReps = Faction.entries.associateWith { 0 }

        return GameState(
            playerName = "Quantum Baby",
            level = 1,
            xp = 0,
            credits = 1200,
            nanites = 150,
            health = 520,
            maxHealth = 520,
            mp = 80,
            maxMp = 80,
            currentWeapon = Weapon.QUANTUM_BLADE,
            currentOutfit = Outfit.DEFAULT,
            installedAugments = mapOf(
                AugmentSlot.CRANIAL to "Neural Processor",
                AugmentSlot.TORSO to "Nano Fiber Mesh",
                AugmentSlot.ARMS to "Cybernetic Muscles"
            ),
            installedModChips = listOf(ModChip.CRITICAL_BOOST, ModChip.LIFE_DRAIN),
            factionReputations = defaultReps,
            companions = companions,
            mapState = mapPOIs
        )
    }
}
