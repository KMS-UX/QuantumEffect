package com.example.game.models

import androidx.compose.ui.graphics.Color
import com.example.game.engine.Element
import com.example.ui.theme.QuantumNeonBlue
import com.example.ui.theme.QuantumNeonGreen
import com.example.ui.theme.QuantumNeonOrange
import com.example.ui.theme.QuantumNeonPurple
import com.example.ui.theme.QuantumNeonRed

// ---------------------------------------------------------------------------
// Weapons
// ---------------------------------------------------------------------------

enum class WeaponType {
    MELEE,
    PISTOL,
    RIFLE,
    HEAVY,
    ENERGY,
    QUANTUM
}

enum class Weapon(
    val displayName: String,
    val type: WeaponType,
    val baseAtk: Int,
    val baseMag: Int,
    val energyCost: Int,
    val description: String,
    val specialtyDesc: String
) {
    QUANTUM_BLADE(
        "Quantum Blade", WeaponType.MELEE, 120, 40, 5,
        "A katana-style blade that vibrates with localized spatial anomalies.",
        "Ignores 20% Armor"
    ),
    MONO_KATANA(
        "Monomolecular Katana", WeaponType.MELEE, 100, 10, 0,
        "A super-sharp carbon-nanotube edge that slices on contact.",
        "+15% Critical Chance"
    ),
    PULSE_PISTOL(
        "Pulse Pistol", WeaponType.PISTOL, 70, 20, 2,
        "A compact energy blaster with auto-stabilizing magnetic coils.",
        "Drains shield on hit"
    ),
    VOID_HANDCANNON(
        "Void Handcannon", WeaponType.PISTOL, 150, 80, 8,
        "Fires condensed dark matter shells. High recoil, catastrophic force.",
        "+25% Critical Damage"
    ),
    RAILGUN_AR(
        "Railgun AR", WeaponType.RIFLE, 110, 10, 4,
        "An electromagnetic assault rifle capable of armor-piercing kinetic rounds.",
        "Pierce through enemies"
    ),
    PLASMA_CARBINE(
        "Plasma Carbine", WeaponType.RIFLE, 90, 60, 5,
        "Fires localized plasma charges that splash on impact.",
        "Applies burn effect over time"
    ),
    GAUSS_CANNON(
        "Gauss Cannon", WeaponType.HEAVY, 180, 20, 10,
        "A heavy micro-projectile launcher designed to halt tactical mechs.",
        "Chance to stun targets"
    ),
    SIEGE_RAILGUN(
        "Siege Railgun", WeaponType.HEAVY, 220, 50, 15,
        "A shoulder-mounted weapon utilizing orbital dreadnought tech.",
        "High damage but -10% Speed"
    ),
    LASER_LANCE(
        "Laser Lance", WeaponType.ENERGY, 80, 100, 6,
        "A continuous beam focuser delivering thermal energy.",
        "Melts defense stacks by 5%"
    ),
    PARTICLE_SCYTHE(
        "Particle Scythe", WeaponType.ENERGY, 110, 130, 8,
        "Harvests subatomic collisions, sweeping fields with heavy damage.",
        "+10% HP lifesteal"
    ),
    SINGULARITY_LANCE(
        "Singularity Lance", WeaponType.QUANTUM, 140, 180, 12,
        "Fires miniature gravitational collapses, dragging threats together.",
        "Attacks drag in & slow"
    ),
    REALITY_SHREDDER(
        "Reality Shredder", WeaponType.QUANTUM, 200, 250, 20,
        "Warp technology that temporarily creates localized rift breaches.",
        "+30% Void corruption damage"
    )
}

// ---------------------------------------------------------------------------
// Outfits, augments and mod chips
// ---------------------------------------------------------------------------

enum class Outfit(
    val displayName: String,
    val description: String,
    val bonusDesc: String,
    val hpMult: Float,
    val defMult: Float,
    val spdMult: Float
) {
    DEFAULT(
        "Default Uniform",
        "Standard survivalist suit from the Unknown Earth experimental labs.",
        "+0% base stats", 1.0f, 1.0f, 1.0f
    ),
    EXPLORER(
        "Scout Exosuit",
        "Rigged with thrusters and thermal plating for desolate frontiers.",
        "+15% Mobility & Speed", 1.0f, 0.9f, 1.15f
    ),
    STEALTH(
        "Phantom Shroud",
        "Dampens electromagnetic signatures and sound. Preferred by the Eclipse Syndicate.",
        "+20% Dodge Rate & +10% Crit", 0.9f, 0.9f, 1.1f
    ),
    ACADEMIC(
        "Archivist Robe",
        "Reinforced with nanite fabrics and mental dampeners. Sourced from the Aurelian Order.",
        "+25% Magic & MP capacity", 0.9f, 1.1f, 0.9f
    ),
    VOID_TOUCH(
        "Void Singularity Mesh",
        "Infused with raw condensed Voidium crystals. Absorbs ambient radiation.",
        "+20% Max Health & Lifesteal", 1.2f, 1.1f, 0.9f
    )
}

enum class AugmentSlot(val displayName: String, val category: String) {
    CRANIAL("Cranial Core", "Mind & Cognition"),
    TORSO("Torso Chassis", "Core & Vitality"),
    ARMS("Arm Actuators", "Strength & Combat"),
    LEGS("Leg Boosters", "Mobility & Agility"),
    SENSORY("Sensory Array", "Awareness & Perception"),
    DERMAL("Dermal Shell", "Skin & Defense"),
    QUANTUM_CORE("Quantum Reactor", "Power Resonance")
}

data class AugmentChip(
    val name: String,
    val slot: AugmentSlot,
    val modifierDesc: String,
    val hpBonus: Int = 0,
    val mpBonus: Int = 0,
    val atkBonus: Int = 0,
    val defBonus: Int = 0,
    val magBonus: Int = 0,
    val spdBonus: Int = 0,
    val lckBonus: Int = 0
)

enum class ModChip(
    val displayName: String,
    val bonusDesc: String,
    val category: String
) {
    CRITICAL_BOOST("Critical Boost", "Increases critical strike rate by 15%.", "Offensive"),
    LIFE_DRAIN("Life Drain", "Restores 10% of dealt damage as HP.", "Defensive"),
    SHIELD_OVERDRIVE("Shield Overdrive", "Increases maximum shield capacity by 20%.", "Defensive"),
    QUANTUM_LEECH("Quantum Leech", "Drains 5 MP from target on attack.", "Offensive"),
    COOLDOWN_REDUCER("Cooldown Reducer", "Reduces all skill cooldowns by 15%.", "Utility"),
    STEALTH_MODULE("Stealth Module", "Reduces enemy detection range on the map by 30%.", "Utility"),
    EXP_BOOSTER("EXP Booster", "Increases experience points gained by 25%.", "Special"),
    DROP_RATE_UP("Drop Rate Up", "Increases material drop rate from bosses by 30%.", "Special")
}

// ---------------------------------------------------------------------------
// Skills
// ---------------------------------------------------------------------------

/**
 * Player active skills.
 *
 * [element] is the marker the skill stamps onto its target; when two markers form
 * a known pair the Combo Engine detonates them (see `engine/ElementalSynergy.kt`).
 * A `null` element means the skill is purely utility and never seeds a synergy.
 */
enum class ActiveSkill(
    val displayName: String,
    val mpCost: Int,
    val description: String,
    val color: Color,
    val element: Element? = null,
    val damageMultiplier: Float = 1f,
    val healAmount: Int = 0
) {
    QUANTUM_BURST(
        "Quantum Burst", 20, "Deal 120 Magic damage to all active threats.", QuantumNeonPurple,
        element = Element.VOID, damageMultiplier = 1.4f
    ),
    TIME_STEP(
        "Time Step", 15, "Gain +50% evasion and blink forward, dealing 40 Kinetic damage.", QuantumNeonBlue,
        element = Element.WIND, damageMultiplier = 0.6f
    ),
    PHASE_SHIELD(
        "Phase Shield", 25, "Create an energy barrier absorbing up to 150 incoming damage.", QuantumNeonGreen,
        element = Element.ICE, damageMultiplier = 0.4f
    ),
    RESONANCE_WAVE(
        "Resonance Wave", 18, "Emit an anomaly wave reducing enemy armor by 40% for 3 turns.", QuantumNeonOrange,
        element = Element.ELECTRIC, damageMultiplier = 1.1f
    ),
    GRAVITY_WELL(
        "Gravity Well", 30, "Pull enemies together, trapping them and dealing 30 damage per turn.", QuantumNeonPurple,
        element = Element.GRAVITY, damageMultiplier = 1.2f
    ),
    HEALING_PULSE(
        "Healing Pulse", 22, "Activate dynamic bio-regenerators to restore 150 HP immediately.", QuantumNeonGreen,
        healAmount = 150
    ),
    VOID_TOUCH(
        "Void Touch", 12, "Empower strikes to convert 30% of physical damage into health.", QuantumNeonPurple,
        element = Element.VOID, damageMultiplier = 0.9f
    ),
    TEMPORAL_LOOP(
        "Temporal Loop", 35, "Rewind timeline by 3 seconds, removing all debuffs and restoring 80 HP.", QuantumNeonBlue,
        healAmount = 80
    ),
    ENERGY_OVERLOAD(
        "Energy Overload", 28, "Enter an overcharged state, boosting ATK and MAG by 50% for 2 turns.", QuantumNeonOrange,
        element = Element.FIRE, damageMultiplier = 1.5f
    )
}

// ---------------------------------------------------------------------------
// World: biomes, factions, points of interest
// ---------------------------------------------------------------------------

enum class Biome(val displayName: String, val color: Color) {
    GRASSLANDS("Aurelian Heartlands", QuantumNeonGreen),
    SNOW("Frostveil Reaches", QuantumNeonBlue),
    DESERT("Emberfall Wastes", QuantumNeonOrange),
    VOLCANIC("Ironward Highlands", QuantumNeonRed),
    RUINS("Sunken Ruins", QuantumNeonPurple),
    VOID_SEA("The Void Sea", QuantumNeonPurple)
}

enum class Faction(
    val displayName: String,
    val shortDesc: String,
    val alignment: String,
    val baseLocation: String,
    val perkName: String,
    val perkDesc: String,
    val color: Color
) {
    AURELIAN_ORDER(
        "Aurelian Order",
        "A technomystic theocracy dedicated to the preservation of ancient space coordinates.",
        "Lawful • Structured", "Aurelian Prime",
        "Advanced Tech Access", "Discount on Tech & Medical Supplies",
        QuantumNeonBlue
    ),
    EMBERPACT(
        "Emberpact",
        "A fierce coalition of frontier mining clans and survivalist raiders.",
        "Chaotic • Fierce", "Kharag Wastes",
        "Heavy Weapon Mastery", "Increased loot from defeated mechanoids",
        QuantumNeonOrange
    ),
    VOID_SEEKERS(
        "Void Seekers",
        "Mystics and outcasts utilizing dark-matter technology to transcend physical boundaries.",
        "Mysterious • Insightful", "Nyx Expanse",
        "Void Magic Mastery", "Increases Void anomaly resistances by 30%",
        QuantumNeonPurple
    ),
    IRONWARD(
        "Ironward",
        "A highly militarized industrial union focused on defense, security, and robotic engineering.",
        "Lawful • Practical", "Vestra Industrial",
        "Armored Vehicle Support", "Ability to deploy battle turrets & drones",
        QuantumNeonGreen
    ),
    SILENT_VEIL(
        "Silent Veil",
        "A secretive network of shadow spies, data brokers, and cutthroat herbalists.",
        "Neutral • Stealthy", "Unknown",
        "Stealth Expertise", "Black market trades & hidden path access",
        QuantumNeonRed
    )
}

enum class ReputationTier(val title: String, val threshold: Int, val color: Color) {
    HATED("Hated", -500, QuantumNeonRed),
    HOSTILE("Hostile", -100, QuantumNeonOrange),
    NEUTRAL("Neutral", 100, Color.LightGray),
    FAVORABLE("Favorable", 500, QuantumNeonBlue),
    REVERED("Revered", 1000, QuantumNeonGreen);

    companion object {
        /** Lowest tier whose threshold the given reputation score has not yet exceeded. */
        fun forScore(score: Int): ReputationTier =
            entries.firstOrNull { score < it.threshold } ?: REVERED
    }
}

data class PointOfInterest(
    val name: String,
    val biome: Biome,
    val description: String,
    val dangerLevel: String,
    val resource: String,
    val hasBoss: Boolean = false,
    val bossName: String? = null,
    val factionPresent: Faction,
    val isCleared: Boolean = false
)

data class DeployedStructure(
    val id: Long,
    val type: String,
    val name: String,
    val factionName: String,
    val biomeName: String,
    val x: Float,
    val y: Float,
    val colorVal: Long,
    val isUpgraded: Boolean = false,
    val level: Int = 1
)

// ---------------------------------------------------------------------------
// Party, inventory and quests
// ---------------------------------------------------------------------------

data class CompanionRecord(
    val name: String,
    val role: String,
    val faction: String,
    val bondPoints: Int = 0,
    val isRecruited: Boolean = false,
    val activeSkill: String,
    val skillDesc: String,
    val portraitSymbol: String,
    val level: Int = 1,
    val weaponEquipped: String = "None",
    val armorEquipped: String = "None",
    val accessoryEquipped: String = "None",
    val moduleEquipped: String = "None"
)

data class InventoryItem(
    val id: String,
    val name: String,
    val category: String,
    val iconSymbol: String,
    val quantity: Int,
    val description: String,
    val statModifierDesc: String = "",
    val hpBonus: Int = 0,
    val mpBonus: Int = 0,
    val atkBonus: Int = 0,
    val defBonus: Int = 0,
    val isConsumable: Boolean = false,
    val isEquippable: Boolean = false,
    val isEquipped: Boolean = false
)

data class Quest(
    val id: String,
    val title: String,
    val description: String,
    val faction: String,
    val rewardCredits: Int,
    val rewardNanites: Int,
    val rewardXp: Int,
    val rewardReputationPoints: Int,
    val status: String = "AVAILABLE",
    val progress: Int = 0,
    val targetCount: Int = 1,
    val objectiveType: String = "TALK",
    val objectiveDesc: String = "Speak with the representative",
    val giverName: String = "Operator"
)

// ---------------------------------------------------------------------------
// Ground combat
// ---------------------------------------------------------------------------

data class Enemy(
    val name: String,
    val maxHp: Int,
    val currentHp: Int,
    val atk: Int,
    val defense: Int,
    val speed: Int,
    val description: String,
    val resistanceType: String,
    val vulnerability: String,
    val modName: String,
    val statusEffects: List<String> = emptyList()
)

data class BattleLog(
    val text: String,
    val isPlayerAction: Boolean = true,
    val color: Color = Color.White
)

data class BattleState(
    val isActive: Boolean = false,
    val playerHp: Int = 520,
    val playerMaxHp: Int = 520,
    val playerMp: Int = 80,
    val playerMaxMp: Int = 80,
    val activeEnemy: Enemy? = null,
    val logs: List<BattleLog> = emptyList(),
    val turnNumber: Int = 1,
    val isPlayerTurn: Boolean = true,
    val battleRewardXp: Int = 0,
    val battleRewardCredits: Int = 0,
    val victory: Boolean = false,
    val defeat: Boolean = false
)
