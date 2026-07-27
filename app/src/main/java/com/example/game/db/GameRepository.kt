package com.example.game.db

import com.example.game.models.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.lang.reflect.ParameterizedType

class GameRepository(val dao: GameProgressDao) {
    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val augmentType: ParameterizedType = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
    private val modChipListType: ParameterizedType = Types.newParameterizedType(List::class.java, String::class.java)
    private val factionRepType: ParameterizedType = Types.newParameterizedType(Map::class.java, String::class.java, Int::class.javaObjectType)
    private val companionListType: ParameterizedType = Types.newParameterizedType(List::class.java, CompanionRecord::class.java)
    private val mapStateListType: ParameterizedType = Types.newParameterizedType(List::class.java, PointOfInterest::class.java)
    private val deployedStructuresListType: ParameterizedType = Types.newParameterizedType(List::class.java, DeployedStructure::class.java)
    private val inventoryListType: ParameterizedType = Types.newParameterizedType(List::class.java, InventoryItem::class.java)
    private val questListType: ParameterizedType = Types.newParameterizedType(List::class.java, Quest::class.java)

    private val augmentAdapter: JsonAdapter<Map<String, String>> = moshi.adapter(augmentType)
    private val modChipListAdapter: JsonAdapter<List<String>> = moshi.adapter(modChipListType)
    private val factionRepAdapter: JsonAdapter<Map<String, Int>> = moshi.adapter(factionRepType)
    private val companionListAdapter: JsonAdapter<List<CompanionRecord>> = moshi.adapter(companionListType)
    private val mapStateListAdapter: JsonAdapter<List<PointOfInterest>> = moshi.adapter(mapStateListType)
    private val deployedStructuresListAdapter: JsonAdapter<List<DeployedStructure>> = moshi.adapter(deployedStructuresListType)
    private val inventoryListAdapter: JsonAdapter<List<InventoryItem>> = moshi.adapter(inventoryListType)
    private val questListAdapter: JsonAdapter<List<Quest>> = moshi.adapter(questListType)

    val gameStateFlow: Flow<GameState> = dao.getProgressFlow().map { progress ->
        if (progress == null) {
            createDefaultGameState()
        } else {
            mapProgressToState(progress)
        }
    }

    val augmentList: List<AugmentChip> = listOf(
        AugmentChip("Neural Processor", AugmentSlot.CRANIAL, "+15 ATK, overclock mental reasoning", 0, 0, 15, 0, 10, 0, 0),
        AugmentChip("Memory Expansion", AugmentSlot.CRANIAL, "+40 MP, increases timeline recall", 0, 40, 0, 0, 0, 0, 0),
        AugmentChip("Focus Amplifier", AugmentSlot.CRANIAL, "+20 MAG, concentrates resonance waves", 0, 0, 0, 0, 20, 0, 0),
        AugmentChip("Vision Suite", AugmentSlot.CRANIAL, "+10 LCK, highlights tactical anomalies", 0, 0, 0, 0, 0, 0, 10),
        AugmentChip("Signal Heart", AugmentSlot.TORSO, "+100 HP, enhances adrenaline pumps", 100, 0, 0, 0, 0, 0, 0),
        AugmentChip("Lung Reinforcement", AugmentSlot.TORSO, "+20 DEF, mitigates toxic atmospheres", 0, 0, 0, 20, 0, 0, 0),
        AugmentChip("Nano Fiber Mesh", AugmentSlot.TORSO, "+50 HP, absorbing kinetic shock", 50, 0, 0, 10, 0, 0, 0),
        AugmentChip("Bio-Reactor", AugmentSlot.TORSO, "+20 MP & +15 ATK, fuels biomechanics", 0, 20, 15, 0, 0, 0, 0),
        AugmentChip("Cybernetic Muscles", AugmentSlot.ARMS, "+30 ATK, hydraulic punches", 0, 0, 30, 0, 0, 0, 0),
        AugmentChip("Smart Servo-Joint", AugmentSlot.ARMS, "+15 SPD, quick combat draw", 0, 0, 0, 0, 0, 15, 0),
        AugmentChip("Tactical Interface", AugmentSlot.ARMS, "+15 ATK & +10 MAG, lock-on targeting", 0, 0, 15, 0, 10, 0, 0),
        AugmentChip("Weapon Mount", AugmentSlot.ARMS, "+25 ATK, heavy stabilizer chassis", 0, 0, 25, 0, 0, 0, 0),
        AugmentChip("Magnetic Boosters", AugmentSlot.LEGS, "+25 SPD, slide on scrap metals", 0, 0, 0, 0, 0, 25, 0),
        AugmentChip("Shock Absorbers", AugmentSlot.LEGS, "+50 HP, safe leap down from buildings", 50, 0, 0, 0, 0, 0, 0),
        AugmentChip("Graviton Stabilizer", AugmentSlot.LEGS, "+15 DEF & +10 LCK, gravity defying steps", 0, 0, 0, 15, 0, 0, 10),
        AugmentChip("Silent Step System", AugmentSlot.LEGS, "+20 SPD, sound dampening soles", 0, 0, 0, 0, 0, 20, 0),
        AugmentChip("Quantum Eye", AugmentSlot.SENSORY, "+30 MAG, sees light wave emissions", 0, 0, 0, 0, 30, 0, 0),
        AugmentChip("Audio Enhancer", AugmentSlot.SENSORY, "+10 SPD & +10 LCK, listens to radio bands", 0, 0, 0, 0, 0, 10, 10),
        AugmentChip("Threat Scanner", AugmentSlot.SENSORY, "+15 DEF, tactical danger overlay", 0, 0, 0, 15, 0, 0, 0),
        AugmentChip("Datajack", AugmentSlot.SENSORY, "+15 MAG, instant network link", 0, 0, 0, 0, 15, 0, 0),
        AugmentChip("Adaptive Armor Skin", AugmentSlot.DERMAL, "+30 DEF, scales resist bullets", 0, 0, 0, 30, 0, 0, 0),
        AugmentChip("Thermal Regulation", AugmentSlot.DERMAL, "+50 HP, works in lava/glacier biomes", 50, 0, 0, 0, 0, 0, 0),
        AugmentChip("Nano-Heal Layer", AugmentSlot.DERMAL, "Regenerates HP constantly, +15 DEF", 0, 0, 0, 15, 0, 0, 0),
        AugmentChip("Camouflage Mesh", AugmentSlot.DERMAL, "+15 SPD, adapts color to shadows", 0, 0, 0, 0, 0, 15, 0),
        AugmentChip("Resonance Core", AugmentSlot.QUANTUM_CORE, "+20 ATK, +20 MAG, stable alignment", 0, 0, 20, 0, 20, 0, 0),
        AugmentChip("Quantum Capacitor", AugmentSlot.QUANTUM_CORE, "+50 MP, condensed energy pool", 0, 50, 0, 0, 0, 0, 0),
        AugmentChip("Energy Conduit", AugmentSlot.QUANTUM_CORE, "+100 HP, rapid current distribution", 100, 0, 0, 0, 0, 0, 0),
        AugmentChip("Warp Stabilizer", AugmentSlot.QUANTUM_CORE, "+15 LCK, keeps coordinates safe", 0, 0, 0, 0, 0, 0, 15)
    )

    suspend fun getGameState(): GameState = withContext(Dispatchers.IO) {
        val progress = dao.getProgress()
        if (progress == null) {
            createDefaultGameState()
        } else {
            mapProgressToState(progress)
        }
    }

    suspend fun saveGameState(state: GameState) = withContext(Dispatchers.IO) {
        dao.saveProgress(mapStateToProgress(state))
    }

    suspend fun clearSaveProgress() = withContext(Dispatchers.IO) {
        dao.clearProgress()
    }

    fun mapProgressToState(progress: GameProgress): GameState {
        val augmentsMapRaw: Map<String, String> = try {
            augmentAdapter.fromJson(progress.installedAugmentsJson) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }

        val augmentsMap = augmentsMapRaw.mapNotNull { entry ->
            val slot = try { AugmentSlot.valueOf(entry.key) } catch (e: Exception) { null }
            if (slot != null) slot to entry.value else null
        }.toMap()

        val modChipsListRaw: List<String> = try {
            modChipListAdapter.fromJson(progress.installedModChipsJson) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        val modChips = modChipsListRaw.mapNotNull { name ->
            try { ModChip.valueOf(name) } catch (e: Exception) { null }
        }

        val factionRepRaw: Map<String, Int> = try {
            factionRepAdapter.fromJson(progress.factionReputationsJson) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }

        val reputations = factionRepRaw.mapNotNull { entry ->
            val faction = try { Faction.valueOf(entry.key) } catch (e: Exception) { null }
            if (faction != null) faction to entry.value else null
        }.toMap()

        val companions: List<CompanionRecord> = try {
            companionListAdapter.fromJson(progress.companionsJson) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        val mapState: List<PointOfInterest> = try {
            mapStateListAdapter.fromJson(progress.mapStateJson) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        val deployedStructures: List<DeployedStructure> = try {
            deployedStructuresListAdapter.fromJson(progress.deployedStructuresJson) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        val inventory: List<InventoryItem> = try {
            inventoryListAdapter.fromJson(progress.inventoryJson) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        val quests: List<Quest> = try {
            questListAdapter.fromJson(progress.questsJson) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        val weapon = try { Weapon.valueOf(progress.currentWeapon) } catch (e: Exception) { Weapon.QUANTUM_BLADE }
        val outfit = try { Outfit.valueOf(progress.currentOutfit) } catch (e: Exception) { Outfit.DEFAULT }

        val stats = recalculateDynamicStats(progress.level, progress.health, progress.mp, outfit, augmentsMap, inventory)
        val finalHealth = stats[0]
        val finalMaxHealth = stats[1]
        val finalMp = stats[2]
        val finalMaxMp = stats[3]

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
            deployedStructures = deployedStructures,
            inventory = inventory,
            quests = quests
        )
    }

    private fun mapStateToProgress(state: GameState): GameProgress {
        val augmentsJson = augmentAdapter.toJson(state.installedAugments.mapKeys { it.key.name })
        val modChipsJson = modChipListAdapter.toJson(state.installedModChips.map { it.name })
        val factionRepJson = factionRepAdapter.toJson(state.factionReputations.mapKeys { it.key.name })
        val companionsJson = companionListAdapter.toJson(state.companions)
        val mapStateJson = mapStateListAdapter.toJson(state.mapState)
        val deployedStructuresJson = deployedStructuresListAdapter.toJson(state.deployedStructures)
        val inventoryJson = inventoryListAdapter.toJson(state.inventory)
        val questsJson = questListAdapter.toJson(state.quests)

        return GameProgress(
            id = 1,
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
            deployedStructuresJson = deployedStructuresJson,
            inventoryJson = inventoryJson,
            questsJson = questsJson
        )
    }

    fun recalculateDynamicStats(
        level: Int,
        baseHealth: Int,
        baseMp: Int,
        outfit: Outfit,
        augments: Map<AugmentSlot, String>,
        inventory: List<InventoryItem>
    ): List<Int> {
        val calculatedMaxHp = (level * 70) + 450
        val calculatedMaxMp = (level * 20) + 60
        var augmentHp = 0
        var augmentMp = 0

        for ((slot, value) in augments) {
            val augmentData = getAugmentData(value, slot)
            if (augmentData != null) {
                augmentHp += augmentData.hpBonus
                augmentMp += augmentData.mpBonus
            }
        }

        for (item in inventory) {
            if (item.isEquipped) {
                augmentHp += item.hpBonus
                augmentMp += item.mpBonus
            }
        }

        val maxHp = ((calculatedMaxHp + augmentHp) * outfit.hpMult).toInt()
        val maxMp = calculatedMaxMp + augmentMp
        val finalMaxMp = if (outfit == Outfit.ACADEMIC) (maxMp * 1.25).toInt() else maxMp
        val health = if (baseHealth > maxHp || baseHealth <= 0) maxHp else baseHealth
        val mp = if (baseMp > finalMaxMp || baseMp <= 0) finalMaxMp else baseMp

        return listOf(health, maxHp, mp, finalMaxMp)
    }

    fun getAugmentData(name: String, slot: AugmentSlot): AugmentChip? {
        return augmentList.firstOrNull { it.name == name && it.slot == slot }
    }

    fun createDefaultGameState(): GameState {
        val mapPOIs = listOf(
            PointOfInterest("Neo Solis Citadel", Biome.GRASSLANDS, "The shining capital of the united earth. Controlled by Enlighteners, high-tech and heavily monitored.", "Minor", "Scrap Metal", false, "", Faction.AURELIAN_ORDER, false),
            PointOfInterest("Aurelian Archive", Biome.GRASSLANDS, "The majestic archives holding the original quantum teleportation coordinates.", "Moderate", "Data Fragment", false, "", Faction.AURELIAN_ORDER, false),
            PointOfInterest("Frostveil Outpost", Biome.SNOW, "A freezing research outpost on the polar ice cap. Secret bounty hunters hide here.", "Moderate", "Nano Fiber", false, "", Faction.SILENT_VEIL, false),
            PointOfInterest("Emberfall Volcano", Biome.DESERT, "A flowing volcanic fissure mining raw dark energy and lava minerals.", "Severe", "Void Crystal", false, "", Faction.EMBERPACT, false),
            PointOfInterest("Ironward Megafactory", Biome.VOLCANIC, "A giant automated mechanical plant producing security drone armors.", "Critical", "Alloy Plate", true, "Omega Prime Sentinal", Faction.IRONWARD, false),
            PointOfInterest("The Void Observatory", Biome.VOID_SEA, "An ancient ruins floating directly inside the dark-matter warp rift.", "Apocalyptic", "Singularity Shard", true, "Null Colossus", Faction.VOID_SEEKERS, false),
            PointOfInterest("Black Market Bazaar", Biome.RUINS, "A hidden backstreet block of crime, blackmarket cybernetics, and outlaws.", "Moderate", "Quantum Core", false, "", Faction.SILENT_VEIL, false)
        )
        val companions = listOf(
            CompanionRecord("Lyra", "The Scout", "Aurelian Order", 20, true, "Recon Sweep", "Mark all enemies on map, increasing player hit accuracy by 30% for 3 turns.", "🏹", 1, "None", "None", "None", "None"),
            CompanionRecord("Drox", "The Bruiser", "Ironward", 0, false, "Shield Wall", "Deploys a reinforced energy dome, absorbing 250 incoming damage.", "🦖", 1, "None", "None", "None", "None"),
            CompanionRecord("Nix", "The Techwiz", "Emberpact", 10, true, "Hacking Pulse", "Sends electric disruptors, disabling robot enemies for 1 turn.", "🧑\u200d💻", 1, "None", "None", "None", "None"),
            CompanionRecord("Vayn", "The Shadow", "Void Seekers", 0, false, "Shadow Step", "Teleport behind enemies, delivering critical backstabs with 100% pierce.", "🥷", 1, "None", "None", "None", "None"),
            CompanionRecord("Elsi", "The Medic", "Silent Veil", 15, true, "Restore Wave", "Heals all active allies for 200 health immediately.", "🩺", 1, "None", "None", "None", "None"),
            CompanionRecord("Grum", "The Engineer", "Emberpact", 0, false, "Deploy Turret", "Erects an automated gatling laser turret firing 50 damage rounds.", "🔧", 1, "None", "None", "None", "None")
        )
        val defaultReps = Faction.values().associateWith { 0 }
        val defaultInventory = listOf(
            InventoryItem("item_solis_ration", "Solis Ration Pack", "Food", "🍏", 3, "A packaged meal designed for long expeditions. Restores 60 HP.", "+60 HP", 60, 0, 0, 0, true, false, false),
            InventoryItem("item_plasma_cell", "Plasma Battery", "Energy Cell", "🔋", 2, "Overcharged energy cell. Restores 30 Quantum MP.", "+30 MP", 0, 30, 0, 0, true, false, false),
            InventoryItem("item_suture_kit", "Nanite Stim-Injector", "Medicine", "💉", 1, "Instant surgical repair. Restores 150 HP.", "+150 HP", 150, 0, 0, 0, true, false, false),
            InventoryItem("item_aegis_core", "Aegis Shield Core", "Equipment", "🛡️", 1, "Tactical dynamic deflection system.", "+50 Max HP, +20 DEF", 50, 0, 0, 20, false, true, false),
            InventoryItem("item_power_gloves", "Titan Knuckle Clamps", "Equipment", "👊", 1, "Hydraulic hand frames that boost punch torque.", "+30 ATK", 0, 0, 30, 0, false, true, false),
            InventoryItem("item_scrap_metal", "Scrap Metal", "Scrap", "⚙️", 25, "Salvaged mechanical sheets used for trading and engineering.", "", 0, 0, 0, 0, false, false, false)
        )
        val defaultQuests = listOf(
            Quest("q_resonance_rift", "The Quantum Resonance Rift", "Secure data from the Aurelian Archive to stabilize the coordinate grid.", "Enlighteners", 400, 50, 250, 80, "AVAILABLE", 0, 0, "TALK", "Meet Sector Aegis Guard in Grasslands.", "Sector Aegis Guard"),
            Quest("q_scrapyard_jam", "Scrapyard Signal Jammer", "Locate and disrupt the Enlighteners' monitoring console to protect Technopunk scrap mining.", "Technopunks", 350, 70, 1000, 90, "AVAILABLE", 0, 0, "TALK", "Meet Desert Outlaw in Desert.", "Desert Outlaw"),
            Quest("q_nanite_epidemic", "Nanotech Epidemic Control", "Synthesize stabilize vaccines or clear cryogenic impurities to contain a nanite outbreak.", "Silent Veil", 500, 40, 300, 100, "AVAILABLE", 0, 0, "TALK", "Speak with the Nanite Alchemist in Snow biome.", "Nanite Alchemist"),
            Quest("q_recruit_drox", "Recruit Drox the Bruiser", "Secure coordinates to recruit Drox. He wants to see your biomechanical strength or be paid off.", "Ironward", 100, 10, 150, 50, "AVAILABLE", 0, 0, "TALK", "Talk to Drox directly in the Companions terminal or the wild lands.", "Drox")
        )
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
            mapState = mapPOIs,
            deployedStructures = emptyList(),
            inventory = defaultInventory,
            quests = defaultQuests
        )
    }
}
