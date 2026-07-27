package com.example.game.engine

import com.example.game.models.Faction
import com.example.game.models.ReputationTier

/**
 * The Faction & Reputation engine from section 3E of the Summer Engine blueprint.
 *
 * Standings move on an opposed axis: courting the structured Aurelian Order costs
 * you standing with the Emberpact frontier clans and vice versa. Gains then feed
 * back into cyber-ware pricing and into how aggressively the losing side hunts you.
 */
object FactionReputationEngine {

    /** Reputation is clamped to this window; the extremes match [ReputationTier]. */
    val REPUTATION_RANGE: IntRange = -1000..1000

    /**
     * Factions that sit opposite each other on the order/chaos axis. A gain with
     * one applies [OPPOSED_BACKLASH_RATIO] of that gain as a loss to the other.
     */
    val OPPOSED_PAIRS: List<Pair<Faction, Faction>> = listOf(
        Faction.AURELIAN_ORDER to Faction.EMBERPACT,
        Faction.IRONWARD to Faction.SILENT_VEIL
    )

    const val OPPOSED_BACKLASH_RATIO: Float = 0.5f

    /**
     * Applies [delta] to [faction] and pushes the opposed faction the other way.
     *
     * @return a new map; [current] is not modified.
     */
    fun applyShift(
        current: Map<Faction, Int>,
        faction: Faction,
        delta: Int
    ): Map<Faction, Int> {
        val updated = current.toMutableMap()
        updated[faction] = ((updated[faction] ?: 0) + delta).coerceIn(REPUTATION_RANGE)

        opposedTo(faction)?.let { rival ->
            val backlash = -(delta * OPPOSED_BACKLASH_RATIO).toInt()
            updated[rival] = ((updated[rival] ?: 0) + backlash).coerceIn(REPUTATION_RANGE)
        }
        return updated
    }

    /** The faction on the other end of [faction]'s axis, or null if unaligned. */
    fun opposedTo(faction: Faction): Faction? = OPPOSED_PAIRS.firstNotNullOfOrNull { (a, b) ->
        when (faction) {
            a -> b
            b -> a
            else -> null
        }
    }

    fun tierFor(reputation: Int): ReputationTier = ReputationTier.forScore(reputation)

    /**
     * Price multiplier for goods sold by [faction]. Revered standing lands at a
     * 25% discount; hatred marks the price up by 25%.
     */
    fun priceMultiplier(reputation: Int): Float =
        when (tierFor(reputation)) {
            ReputationTier.HATED -> 1.25f
            ReputationTier.HOSTILE -> 1.10f
            ReputationTier.NEUTRAL -> 1.00f
            ReputationTier.FAVORABLE -> 0.90f
            ReputationTier.REVERED -> 0.75f
        }

    /**
     * Chance per zone transition that the faction opposing your highest standing
     * sends an elite bounty hunter after you. Scales with how far the axis has
     * tipped, so a fence-sitter is left alone.
     */
    fun eliteHunterChance(reputations: Map<Faction, Int>): Float {
        val worstStanding = reputations.values.minOrNull() ?: return 0f
        if (worstStanding >= 0) return 0f
        return (-worstStanding / REPUTATION_RANGE.last.toFloat()).coerceIn(0f, 0.75f)
    }
}
