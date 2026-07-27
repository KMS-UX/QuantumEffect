package com.example.game.db

import com.example.game.models.*

data class GameState @JvmOverloads constructor(
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
    val installedAugments: Map<AugmentSlot, String> = emptyMap(),
    val installedModChips: List<ModChip> = emptyList(),
    val factionReputations: Map<Faction, Int> = emptyMap(),
    val companions: List<CompanionRecord> = emptyList(),
    val mapState: List<PointOfInterest> = emptyList(),
    val deployedStructures: List<DeployedStructure> = emptyList(),
    val inventory: List<InventoryItem> = emptyList(),
    val quests: List<Quest> = emptyList()
) {
    val totalAtkBonus: Int
        get() = inventory.filter { it.isEquipped }.sumOf { it.atkBonus }

    val totalDefBonus: Int
        get() = inventory.filter { it.isEquipped }.sumOf { it.defBonus }
}
