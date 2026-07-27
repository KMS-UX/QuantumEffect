package com.example.game.engine

import com.example.game.models.AugmentChip
import com.example.game.models.AugmentSlot

/**
 * Attribute binding for bio-mechanical augments, per section 3D of the Summer
 * Engine blueprint. Arm actuators drive melee reach and physical weight; cranial
 * and quantum-core hardware drive synergy trigger rate and cooldown recovery.
 */
object AugmentBinding {

    /** Melee sweep radius in world units with no arm hardware installed. */
    const val BASE_SWEEP_RADIUS: Float = 1.2f

    /** Baseline chance that a skill stamps its element onto the target. */
    const val BASE_SYNERGY_CHANCE: Float = 0.25f

    /**
     * Folds every installed chip into one derived profile.
     *
     * @param installed slot -> chip name, as stored on `GameState.installedAugments`
     * @param catalogue every chip the game knows about, from `GameRepository.augmentList`
     */
    fun derive(
        installed: Map<AugmentSlot, String>,
        catalogue: List<AugmentChip>
    ): AugmentProfile {
        val chips = installed.mapNotNull { (slot, name) ->
            catalogue.firstOrNull { it.slot == slot && it.name == name }
        }

        val armAtk = chips.filter { it.slot == AugmentSlot.ARMS }.sumOf { it.atkBonus }
        val neuralMag = chips
            .filter { it.slot == AugmentSlot.CRANIAL || it.slot == AugmentSlot.QUANTUM_CORE }
            .sumOf { it.magBonus }
        val totalSpd = chips.sumOf { it.spdBonus }
        val totalLck = chips.sumOf { it.lckBonus }

        return AugmentProfile(
            // Every 30 points of arm ATK adds a full +100% to strike scaling.
            strengthMultiplier = armAtk / 30f,
            sweepRadius = BASE_SWEEP_RADIUS * (1f + armAtk / 100f),
            physicalWeight = 1f + armAtk / 200f,
            synergyChance = (BASE_SYNERGY_CHANCE + neuralMag / 200f).coerceAtMost(0.95f),
            cooldownMultiplier = (1f - neuralMag / 300f).coerceAtLeast(0.4f),
            moveSpeedMultiplier = 1f + totalSpd / 200f,
            critChance = (totalLck / 200f).coerceIn(0f, 0.75f)
        )
    }
}

/**
 * Derived combat attributes produced by the installed augment loadout.
 *
 * @param strengthMultiplier the `CyberneticStrengthAugment` term of the blueprint
 *   damage formula, expressed as a fraction (0.5 means +50%)
 * @param sweepRadius melee arc radius in world units
 * @param physicalWeight knockback and stagger scaling for melee hits
 * @param synergyChance probability a skill applies its element marker
 * @param cooldownMultiplier applied to skill cooldowns; below 1.0 is faster
 * @param moveSpeedMultiplier applied to isometric movement speed
 * @param critChance probability of a critical strike, 0.0..0.75
 */
data class AugmentProfile(
    val strengthMultiplier: Float = 0f,
    val sweepRadius: Float = AugmentBinding.BASE_SWEEP_RADIUS,
    val physicalWeight: Float = 1f,
    val synergyChance: Float = AugmentBinding.BASE_SYNERGY_CHANCE,
    val cooldownMultiplier: Float = 1f,
    val moveSpeedMultiplier: Float = 1f,
    val critChance: Float = 0f
)
