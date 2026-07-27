package com.example.game.engine

/**
 * The Combo Engine from section 3C of the Summer Engine blueprint.
 *
 * Skills stamp temporary [Element] markers onto a target. When two markers that
 * form a known pair are live at the same time they detonate into a [Combo], and
 * both markers are consumed so a single application cannot chain twice.
 */
enum class Element {
    FIRE,
    WIND,
    ELECTRIC,
    ICE,
    EARTH,
    VOID,
    GRAVITY
}

/**
 * A detonation produced by two overlapping elements.
 *
 * @param pullsTargets drags nearby enemies toward the impact point
 * @param freezeSeconds how long targets are locked in place, 0 when it does not freeze
 * @param chainsToAdjacent arcs to neighbouring enemies
 * @param blocksNavigation spawns terrain that pathfinding must route around
 * @param armorShredFraction fraction of the target's armour stripped, 0 when none
 * @param damageOverTime true when the combo leaves a lingering damage field
 */
enum class Combo(
    val displayName: String,
    val trigger: Pair<Element, Element>,
    val durationSeconds: Float,
    val pullsTargets: Boolean = false,
    val freezeSeconds: Float = 0f,
    val chainsToAdjacent: Boolean = false,
    val blocksNavigation: Boolean = false,
    val armorShredFraction: Float = 0f,
    val damageOverTime: Boolean = false
) {
    FIRE_TORNADO(
        displayName = "Fire Tornado",
        trigger = Element.FIRE to Element.WIND,
        durationSeconds = 1.5f,
        pullsTargets = true
    ),
    ELECTRO_FROST(
        displayName = "Electro Frost",
        trigger = Element.ELECTRIC to Element.ICE,
        durationSeconds = 2f,
        freezeSeconds = 2f,
        chainsToAdjacent = true
    ),
    MAGMA_SPIKE(
        displayName = "Magma Spike",
        trigger = Element.EARTH to Element.FIRE,
        durationSeconds = 4f,
        blocksNavigation = true,
        damageOverTime = true
    ),
    VOID_NOVA(
        displayName = "Void Nova",
        trigger = Element.VOID to Element.GRAVITY,
        durationSeconds = 2f,
        pullsTargets = true,
        armorShredFraction = 0.30f
    );

    companion object {
        /** Combo formed by [a] and [b] in either order, or `null` if they do not react. */
        fun forPair(a: Element, b: Element): Combo? = entries.firstOrNull {
            (it.trigger.first == a && it.trigger.second == b) ||
                (it.trigger.first == b && it.trigger.second == a)
        }
    }
}

/** An element marker sitting on a target, with the time left before it expires. */
data class ElementStatus(val element: Element, val remainingSeconds: Float)

/**
 * Per-target registry of live element markers. One instance belongs to one enemy.
 *
 * Not thread safe: mutate it from the combat loop only.
 */
class ElementalStatusRegistry(
    private val defaultDurationSeconds: Float = 6f
) {
    private val statuses = mutableMapOf<Element, Float>()

    val active: List<ElementStatus>
        get() = statuses.map { (element, remaining) -> ElementStatus(element, remaining) }

    fun has(element: Element): Boolean = statuses.containsKey(element)

    /**
     * Stamps [element] onto the target and detonates if it completes a pair.
     *
     * Returns the [Combo] that fired, or `null` when the marker simply landed. On
     * a detonation both contributing markers are cleared.
     */
    fun apply(
        element: Element,
        durationSeconds: Float = defaultDurationSeconds
    ): Combo? {
        val partner = statuses.keys.firstNotNullOfOrNull { existing ->
            Combo.forPair(existing, element)?.let { existing to it }
        }

        if (partner != null) {
            val (existingElement, combo) = partner
            statuses.remove(existingElement)
            statuses.remove(element)
            return combo
        }

        statuses[element] = durationSeconds
        return null
    }

    /** Ages every marker by [deltaSeconds] and drops the ones that ran out. */
    fun tick(deltaSeconds: Float) {
        if (deltaSeconds <= 0f) return
        val expired = mutableListOf<Element>()
        for ((element, remaining) in statuses) {
            val next = remaining - deltaSeconds
            if (next <= 0f) expired += element else statuses[element] = next
        }
        expired.forEach(statuses::remove)
    }

    fun clear() = statuses.clear()
}
