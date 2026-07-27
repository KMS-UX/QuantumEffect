package com.example.game.models

import androidx.compose.ui.graphics.Color

enum class ShipSystem(
    val displayName: String,
    val icon: String,
    val color: Color
) {
    WEAPONS("Hyper-Charged Cannons", "⚔️", Color(0xFFFF1744)),
    SHIELDS("Singularium Deflectors", "🛡️", Color(0xFF29B6F6)),
    ENGINES("Quantum Thrust Impellers", "🚀", Color(0xFFFF9100)),
    NAVIGATION("Chronos Core Computer", "🧭", Color(0xFFD500F9)),
    LIFE_SUPPORT("Biomechanical Biosphere", "🌿", Color(0xFF00E676))
}

data class ShipUpgrade(
    val id: String,
    val name: String,
    val description: String,
    val affectedSystem: ShipSystem,
    val costCredits: Int,
    val costNanites: Int,
    val bonusMultiplier: Float,
    val isOwned: Boolean = false
)

data class CrewMember(
    val id: String,
    val name: String,
    val role: String,
    val specialty: String,
    val rating: Int,
    val description: String,
    val isRecruited: Boolean = false,
    val recruitmentCost: Int = 500,
    val avatarEmoji: String
)

data class StarSystem(
    val id: String,
    val name: String,
    val sectorX: Float,
    val sectorY: Float,
    val factionAffiliation: String,
    val description: String,
    val associatedReality: ParallelEarth,
    val resourceMultiplier: Float,
    val specialNodeName: String,
    val isDiscovered: Boolean = false
)

/**
 * Total power that can be distributed across [ShipSystem]s at once.
 * [com.example.game.viewmodel.GameViewModel.allocateSystemPower] refuses any
 * allocation that would push the sum past this budget.
 */
const val SHIP_POWER_BUDGET: Int = 12

data class StarshipState(
    val name: String = "ISV Quantum Nomad",
    val hull: Float = 150f,
    val maxHull: Float = 150f,
    val shield: Float = 80f,
    val maxShield: Float = 80f,
    val fuel: Float = 100f,
    val maxFuel: Float = 100f,
    val systemPowerAllocation: Map<ShipSystem, Int> = ShipSystem.entries.associateWith { 2 },
    val cargo: Map<String, Int> = emptyMap(),
    val upgrades: List<ShipUpgrade> = emptyList(),
    val crew: List<CrewMember> = emptyList(),
    val starSystems: List<StarSystem> = emptyList(),
    val currentSystemId: String = "sys_sol_prime",
    val universeAlignment: Int = 50,
    val quantumBabiesDiscovered: Int = 0,
    val activeEndingChoice: String = ""
)

data class SpaceCombatState(
    val isCombatActive: Boolean = false,
    val enemyName: String = "",
    val enemyHull: Float = 0f,
    val enemyMaxHull: Float = 0f,
    val enemyShield: Float = 0f,
    val enemyMaxShield: Float = 0f,
    val enemyWeaponPower: Float = 15f,
    val playerTurn: Boolean = true,
    val combatLogs: List<String> = emptyList(),
    val rewardCredits: Int = 0,
    val rewardNanites: Int = 0,
    val battleOver: Boolean = false,
    val playerWon: Boolean = false
)
