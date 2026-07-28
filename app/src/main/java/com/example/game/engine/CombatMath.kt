package com.example.game.engine

import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * Kinetic damage and critical-hit resolution, per section 3B of the Summer Engine
 * blueprint:
 *
 *   Damage = WeaponPower x SkillMultiplier x (1 + CyberneticStrengthAugment)
 *
 * A hit is first classified into a [HitTier] by how much of the target's health
 * bar it removes; the tier then decides both the critical multiplier and how much
 * VFX the renderer should spend on the impact.
 */
object CombatMath {

    /**
     * Resolves a single attack.
     *
     * @param weaponPower base attack rating of the equipped weapon
     * @param skillMultiplier 1.0 for a basic swing, higher for an active skill
     * @param strengthAugment fractional bonus from ARMS augments, e.g. 0.30 for +30%
     * @param targetDefense flat mitigation subtracted after scaling
     * @param targetMaxHp used to classify the hit into a tier
     * @param critChance 0.0..1.0 probability of a critical strike
     * @param armorPierce fraction of [targetDefense] ignored, e.g. 0.20 for the Quantum Blade
     */
    fun resolveAttack(
        weaponPower: Int,
        skillMultiplier: Float = 1f,
        strengthAugment: Float = 0f,
        targetDefense: Int = 0,
        targetMaxHp: Int,
        critChance: Float = 0f,
        armorPierce: Float = 0f,
        random: Random = Random.Default
    ): AttackResult {
        val scaled = weaponPower * skillMultiplier * (1f + strengthAugment)
        val effectiveDefense = targetDefense * (1f - armorPierce.coerceIn(0f, 1f))
        val mitigated = (scaled - effectiveDefense).coerceAtLeast(MINIMUM_DAMAGE)

        val tier = tierFor(mitigated, targetMaxHp)
        val isCritical = critChance > 0f && random.nextFloat() < critChance
        val finalDamage = if (isCritical) mitigated * tier.criticalMultiplier else mitigated

        return AttackResult(
            damage = finalDamage.roundToInt().coerceAtLeast(1),
            tier = if (isCritical) HitTier.CRITICAL else tier,
            isCritical = isCritical
        )
    }

    /**
     * Classifies raw damage by the fraction of the target's health bar it removes.
     * A non-positive [targetMaxHp] cannot be scored against, so such hits read as
     * [HitTier.LIGHT] rather than dividing by zero.
     */
    fun tierFor(damage: Float, targetMaxHp: Int): HitTier {
        if (targetMaxHp <= 0) return HitTier.LIGHT
        return when (damage / targetMaxHp) {
            in 0f..0.10f -> HitTier.LIGHT
            in 0.10f..0.25f -> HitTier.MEDIUM
            in 0.25f..0.50f -> HitTier.HEAVY
            else -> HitTier.CRITICAL
        }
    }

    /** Particle count for an impact, sampled from the tier's blueprint range. */
    fun particleBudget(tier: HitTier, random: Random = Random.Default): Int =
        random.nextInt(tier.minParticles, tier.maxParticles + 1)

    /** Damage floor so a heavily armoured target still takes chip damage. */
    const val MINIMUM_DAMAGE: Float = 1f
}

/**
 * Visual and mechanical weight of one impact. Particle ranges come straight from
 * the blueprint's "Impact Visual Density" table.
 */
enum class HitTier(
    val criticalMultiplier: Float,
    val minParticles: Int,
    val maxParticles: Int,
    val shockwave: Boolean,
    val screenShake: Boolean,
    val debrisBurst: Boolean
) {
    LIGHT(1.5f, 10, 20, shockwave = false, screenShake = false, debrisBurst = false),
    MEDIUM(2.0f, 25, 50, shockwave = true, screenShake = false, debrisBurst = false),
    HEAVY(2.5f, 60, 100, shockwave = true, screenShake = true, debrisBurst = false),
    CRITICAL(3.0f, 120, 200, shockwave = true, screenShake = true, debrisBurst = true)
}

data class AttackResult(
    val damage: Int,
    val tier: HitTier,
    val isCritical: Boolean
)
