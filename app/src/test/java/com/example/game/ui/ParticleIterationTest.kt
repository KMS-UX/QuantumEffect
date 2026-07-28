package com.example.game.ui

import androidx.compose.runtime.mutableStateListOf
import java.util.ConcurrentModificationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Pins the invariant behind the particle buffer in [ExploreScreen]'s physics loop.
 *
 * The loop walks `particles` with an explicit iterator so spent particles can be
 * removed in place. Impact effects used to be appended to the same list from
 * inside that walk, which throws the moment a projectile connects with an enemy —
 * the first shot that lands takes the screen down. Spawns are now buffered and
 * flushed after the iteration finishes.
 */
class ParticleIterationTest {

    @Test
    fun `adding to a SnapshotStateList while iterating it throws`() {
        val particles = mutableStateListOf(1, 2, 3)

        assertFailsWith<ConcurrentModificationException> {
            val iterator = particles.iterator()
            while (iterator.hasNext()) {
                iterator.next()
                particles.add(99)
            }
        }
    }

    @Test
    fun `removing through the iterator while iterating is fine`() {
        val particles = mutableStateListOf(1, 2, 3, 4)

        val iterator = particles.iterator()
        while (iterator.hasNext()) {
            if (iterator.next() % 2 == 0) iterator.remove()
        }

        assertEquals(listOf(1, 3), particles.toList())
    }

    @Test
    fun `buffering spawns and flushing after the walk is safe`() {
        val particles = mutableStateListOf(1, 2, 3)
        val spawned = mutableListOf<Int>()

        val iterator = particles.iterator()
        while (iterator.hasNext()) {
            val value = iterator.next()
            // Spend the particle, and spawn an impact effect from it.
            if (value == 2) iterator.remove() else spawned += value * 10
        }
        particles.addAll(spawned)

        assertEquals(listOf(1, 3, 10, 30), particles.toList())
    }
}
