package com.example.game.engine

import com.example.game.models.ActiveSkill
import com.example.game.models.AugmentChip
import com.example.game.models.AugmentSlot
import com.example.game.models.Faction
import com.example.game.models.ReputationTier
import com.example.game.models.SHIP_POWER_BUDGET
import com.example.game.models.ShipSystem
import com.example.game.models.StarshipState
import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private fun assertClose(expected: Float, actual: Float, tolerance: Float = 1e-4f) {
    assertTrue(abs(expected - actual) <= tolerance, "expected $expected but was $actual")
}

class IsometricMathTest {

    @Test
    fun `worldToScreen matches the blueprint projection formula`() {
        val p = IsometricMath.worldToScreen(4f, 2f)
        assertClose((4f - 2f) * IsometricMath.cosProjection, p.x)
        assertClose((4f + 2f) * IsometricMath.sinProjection, p.y)
    }

    @Test
    fun `screenToWorld inverts worldToScreen`() {
        for ((wx, wy) in listOf(0f to 0f, 3f to 7f, -5f to 2f, 12.5f to -8.25f)) {
            val screen = IsometricMath.worldToScreen(wx, wy)
            val back = IsometricMath.screenToWorld(screen.x, screen.y)
            assertClose(wx, back.x, 1e-3f)
            assertClose(wy, back.y, 1e-3f)
        }
    }

    @Test
    fun `origin projects to origin`() {
        assertEquals(Vec2(0f, 0f), IsometricMath.worldToScreen(0f, 0f))
    }

    @Test
    fun `snapToOctant buckets input into eight facings`() {
        assertEquals(IsoDirection.RIGHT, IsometricMath.snapToOctant(1f, 0f))
        assertEquals(IsoDirection.DOWN_RIGHT, IsometricMath.snapToOctant(1f, 1f))
        assertEquals(IsoDirection.DOWN, IsometricMath.snapToOctant(0f, 1f))
        assertEquals(IsoDirection.DOWN_LEFT, IsometricMath.snapToOctant(-1f, 1f))
        assertEquals(IsoDirection.LEFT, IsometricMath.snapToOctant(-1f, 0f))
        assertEquals(IsoDirection.UP_LEFT, IsometricMath.snapToOctant(-1f, -1f))
        assertEquals(IsoDirection.UP, IsometricMath.snapToOctant(0f, -1f))
        assertEquals(IsoDirection.UP_RIGHT, IsometricMath.snapToOctant(1f, -1f))
    }

    @Test
    fun `snapToOctant ignores input inside the dead zone`() {
        assertNull(IsometricMath.snapToOctant(0f, 0f))
        assertNull(IsometricMath.snapToOctant(0.05f, 0.05f))
    }

    @Test
    fun `friction decays velocity monotonically and latches to zero`() {
        var v = Vec2(10f, -10f)
        var previous = Float.MAX_VALUE
        repeat(40) {
            v = IsometricMath.applyFriction(v, deltaSeconds = 1f / 30f)
            val speed = abs(v.x) + abs(v.y)
            assertTrue(speed < previous, "velocity must strictly decay")
            previous = speed
            if (v == Vec2.ZERO) return
        }
        error("velocity never reached the idle latch")
    }

    @Test
    fun `friction is a no-op for a zero timestep`() {
        val v = Vec2(3f, 4f)
        assertEquals(v, IsometricMath.applyFriction(v, deltaSeconds = 0f))
    }
}

class CombatMathTest {

    @Test
    fun `damage follows weapon power times skill multiplier times augment scaling`() {
        // 100 * 1.5 * (1 + 0.5) = 225, no defense, no crit.
        val result = CombatMath.resolveAttack(
            weaponPower = 100,
            skillMultiplier = 1.5f,
            strengthAugment = 0.5f,
            targetDefense = 0,
            targetMaxHp = 10_000,
            critChance = 0f
        )
        assertEquals(225, result.damage)
        assertTrue(!result.isCritical)
    }

    @Test
    fun `defense is subtracted after scaling and armor pierce reduces it`() {
        val plain = CombatMath.resolveAttack(
            weaponPower = 100, targetDefense = 40, targetMaxHp = 10_000
        )
        assertEquals(60, plain.damage)

        // 20% pierce leaves 32 effective defense.
        val pierced = CombatMath.resolveAttack(
            weaponPower = 100, targetDefense = 40, targetMaxHp = 10_000, armorPierce = 0.20f
        )
        assertEquals(68, pierced.damage)
    }

    @Test
    fun `heavily armored targets still take chip damage`() {
        val result = CombatMath.resolveAttack(
            weaponPower = 10, targetDefense = 9_000, targetMaxHp = 500
        )
        assertEquals(1, result.damage)
    }

    @Test
    fun `hit tiers follow the blueprint damage fractions`() {
        assertEquals(HitTier.LIGHT, CombatMath.tierFor(50f, 1000))
        assertEquals(HitTier.MEDIUM, CombatMath.tierFor(200f, 1000))
        assertEquals(HitTier.HEAVY, CombatMath.tierFor(400f, 1000))
        assertEquals(HitTier.CRITICAL, CombatMath.tierFor(900f, 1000))
    }

    @Test
    fun `tier classification does not divide by zero`() {
        assertEquals(HitTier.LIGHT, CombatMath.tierFor(100f, 0))
        assertEquals(HitTier.LIGHT, CombatMath.tierFor(100f, -5))
    }

    @Test
    fun `a guaranteed crit multiplies by the tier modifier and reports CRITICAL`() {
        // 100 damage against a 1000 HP target is a LIGHT hit, crit modifier 1.5.
        val result = CombatMath.resolveAttack(
            weaponPower = 100, targetMaxHp = 1000, critChance = 1f
        )
        assertTrue(result.isCritical)
        assertEquals(HitTier.CRITICAL, result.tier)
        assertEquals(150, result.damage)
    }

    @Test
    fun `zero crit chance never crits over many rolls`() {
        repeat(500) {
            val r = CombatMath.resolveAttack(weaponPower = 50, targetMaxHp = 500, critChance = 0f)
            assertTrue(!r.isCritical)
        }
    }

    @Test
    fun `particle budget stays inside the blueprint range for every tier`() {
        val random = Random(1234)
        for (tier in HitTier.entries) {
            repeat(200) {
                val n = CombatMath.particleBudget(tier, random)
                assertTrue(
                    n in tier.minParticles..tier.maxParticles,
                    "$tier produced $n outside ${tier.minParticles}..${tier.maxParticles}"
                )
            }
        }
    }

    @Test
    fun `impact flags escalate with tier`() {
        assertTrue(!HitTier.LIGHT.shockwave)
        assertTrue(HitTier.MEDIUM.shockwave && !HitTier.MEDIUM.screenShake)
        assertTrue(HitTier.HEAVY.screenShake && !HitTier.HEAVY.debrisBurst)
        assertTrue(HitTier.CRITICAL.debrisBurst)
    }
}

class ElementalSynergyTest {

    @Test
    fun `all four blueprint combos are reachable in either order`() {
        assertEquals(Combo.FIRE_TORNADO, Combo.forPair(Element.FIRE, Element.WIND))
        assertEquals(Combo.FIRE_TORNADO, Combo.forPair(Element.WIND, Element.FIRE))
        assertEquals(Combo.ELECTRO_FROST, Combo.forPair(Element.ICE, Element.ELECTRIC))
        assertEquals(Combo.MAGMA_SPIKE, Combo.forPair(Element.FIRE, Element.EARTH))
        assertEquals(Combo.VOID_NOVA, Combo.forPair(Element.GRAVITY, Element.VOID))
    }

    @Test
    fun `unrelated elements do not react`() {
        assertNull(Combo.forPair(Element.WIND, Element.ICE))
        assertNull(Combo.forPair(Element.FIRE, Element.FIRE))
    }

    @Test
    fun `combo effects match the blueprint`() {
        assertClose(1.5f, Combo.FIRE_TORNADO.durationSeconds)
        assertTrue(Combo.FIRE_TORNADO.pullsTargets)

        assertClose(2f, Combo.ELECTRO_FROST.freezeSeconds)
        assertTrue(Combo.ELECTRO_FROST.chainsToAdjacent)

        assertTrue(Combo.MAGMA_SPIKE.blocksNavigation && Combo.MAGMA_SPIKE.damageOverTime)

        assertClose(0.30f, Combo.VOID_NOVA.armorShredFraction)
        assertTrue(Combo.VOID_NOVA.pullsTargets)
    }

    @Test
    fun `a single marker does not detonate`() {
        val registry = ElementalStatusRegistry()
        assertNull(registry.apply(Element.FIRE))
        assertTrue(registry.has(Element.FIRE))
    }

    @Test
    fun `overlapping markers detonate and are consumed`() {
        val registry = ElementalStatusRegistry()
        registry.apply(Element.FIRE)
        val combo = registry.apply(Element.WIND)

        assertEquals(Combo.FIRE_TORNADO, combo)
        assertTrue(!registry.has(Element.FIRE), "fire marker must be consumed")
        assertTrue(!registry.has(Element.WIND), "wind marker must be consumed")
    }

    @Test
    fun `a consumed pair cannot chain a second detonation`() {
        val registry = ElementalStatusRegistry()
        registry.apply(Element.ELECTRIC)
        assertNotNull(registry.apply(Element.ICE))
        // Re-applying ice alone finds no partner left.
        assertNull(registry.apply(Element.ICE))
    }

    @Test
    fun `markers expire on tick`() {
        val registry = ElementalStatusRegistry(defaultDurationSeconds = 2f)
        registry.apply(Element.FIRE)

        registry.tick(1f)
        assertTrue(registry.has(Element.FIRE))

        registry.tick(1.5f)
        assertTrue(!registry.has(Element.FIRE))
        // An expired marker can no longer complete a pair.
        assertNull(registry.apply(Element.WIND))
    }

    @Test
    fun `every damaging skill seeds an element the combo engine understands`() {
        val damaging = ActiveSkill.entries.filter { it.healAmount == 0 }
        assertTrue(damaging.isNotEmpty())
        for (skill in damaging) {
            assertNotNull(skill.element, "${skill.name} should carry an element marker")
        }
    }
}

class FactionReputationEngineTest {

    @Test
    fun `gaining standing costs half as much with the opposed faction`() {
        val start = mapOf(Faction.AURELIAN_ORDER to 0, Faction.EMBERPACT to 0)
        val after = FactionReputationEngine.applyShift(start, Faction.AURELIAN_ORDER, 100)

        assertEquals(100, after[Faction.AURELIAN_ORDER])
        assertEquals(-50, after[Faction.EMBERPACT])
    }

    @Test
    fun `the opposed axis is symmetric`() {
        assertEquals(Faction.EMBERPACT, FactionReputationEngine.opposedTo(Faction.AURELIAN_ORDER))
        assertEquals(Faction.AURELIAN_ORDER, FactionReputationEngine.opposedTo(Faction.EMBERPACT))
        assertEquals(Faction.SILENT_VEIL, FactionReputationEngine.opposedTo(Faction.IRONWARD))
        assertNull(FactionReputationEngine.opposedTo(Faction.VOID_SEEKERS))
    }

    @Test
    fun `an unaligned faction shift touches nobody else`() {
        val start = Faction.entries.associateWith { 0 }
        val after = FactionReputationEngine.applyShift(start, Faction.VOID_SEEKERS, 200)
        assertEquals(200, after[Faction.VOID_SEEKERS])
        assertTrue(after.filterKeys { it != Faction.VOID_SEEKERS }.values.all { it == 0 })
    }

    @Test
    fun `reputation is clamped to the tier range`() {
        val start = mapOf(Faction.AURELIAN_ORDER to 900)
        val after = FactionReputationEngine.applyShift(start, Faction.AURELIAN_ORDER, 5_000)
        assertEquals(1000, after[Faction.AURELIAN_ORDER])

        val floored = FactionReputationEngine.applyShift(
            mapOf(Faction.AURELIAN_ORDER to -900), Faction.AURELIAN_ORDER, -5_000
        )
        assertEquals(-1000, floored[Faction.AURELIAN_ORDER])
    }

    @Test
    fun `applyShift does not mutate the input map`() {
        val start = mapOf(Faction.AURELIAN_ORDER to 0, Faction.EMBERPACT to 0)
        FactionReputationEngine.applyShift(start, Faction.AURELIAN_ORDER, 100)
        assertEquals(0, start[Faction.AURELIAN_ORDER])
        assertEquals(0, start[Faction.EMBERPACT])
    }

    @Test
    fun `tiers map onto the reputation scale`() {
        assertEquals(ReputationTier.HATED, FactionReputationEngine.tierFor(-800))
        assertEquals(ReputationTier.HOSTILE, FactionReputationEngine.tierFor(-200))
        assertEquals(ReputationTier.NEUTRAL, FactionReputationEngine.tierFor(0))
        assertEquals(ReputationTier.FAVORABLE, FactionReputationEngine.tierFor(300))
        assertEquals(ReputationTier.REVERED, FactionReputationEngine.tierFor(900))
        assertEquals(ReputationTier.REVERED, FactionReputationEngine.tierFor(5_000))
    }

    @Test
    fun `standing discounts cyber-ware and hatred marks it up`() {
        assertClose(0.75f, FactionReputationEngine.priceMultiplier(900))
        assertClose(1.00f, FactionReputationEngine.priceMultiplier(0))
        assertClose(1.25f, FactionReputationEngine.priceMultiplier(-800))
        // Monotonically non-increasing as standing improves.
        val samples = listOf(-800, -200, 0, 300, 900).map(FactionReputationEngine::priceMultiplier)
        assertEquals(samples.sortedDescending(), samples)
    }

    @Test
    fun `elite hunters only spawn once a faction turns on you`() {
        assertClose(0f, FactionReputationEngine.eliteHunterChance(Faction.entries.associateWith { 0 }))
        assertClose(0f, FactionReputationEngine.eliteHunterChance(Faction.entries.associateWith { 500 }))

        val hated = FactionReputationEngine.eliteHunterChance(mapOf(Faction.EMBERPACT to -1000))
        assertClose(0.75f, hated)

        val mild = FactionReputationEngine.eliteHunterChance(mapOf(Faction.EMBERPACT to -200))
        assertTrue(mild in 0.15f..0.25f, "expected a modest chance, got $mild")
    }
}

class AugmentBindingTest {

    private val catalogue = listOf(
        AugmentChip("Cybernetic Muscles", AugmentSlot.ARMS, "+30 ATK", atkBonus = 30),
        AugmentChip("Neural Processor", AugmentSlot.CRANIAL, "+15 ATK/+10 MAG", atkBonus = 15, magBonus = 10),
        AugmentChip("Quantum Eye", AugmentSlot.SENSORY, "+30 MAG", magBonus = 30),
        AugmentChip("Magnetic Boosters", AugmentSlot.LEGS, "+25 SPD", spdBonus = 25),
        AugmentChip("Vision Suite", AugmentSlot.CRANIAL, "+10 LCK", lckBonus = 10)
    )

    @Test
    fun `an empty loadout yields baseline attributes`() {
        val profile = AugmentBinding.derive(emptyMap(), catalogue)
        assertClose(0f, profile.strengthMultiplier)
        assertClose(AugmentBinding.BASE_SWEEP_RADIUS, profile.sweepRadius)
        assertClose(AugmentBinding.BASE_SYNERGY_CHANCE, profile.synergyChance)
        assertClose(1f, profile.cooldownMultiplier)
        assertClose(0f, profile.critChance)
    }

    @Test
    fun `arm actuators drive strike scaling, sweep radius and weight`() {
        val profile = AugmentBinding.derive(
            mapOf(AugmentSlot.ARMS to "Cybernetic Muscles"), catalogue
        )
        assertClose(1.0f, profile.strengthMultiplier)          // 30 / 30
        assertClose(AugmentBinding.BASE_SWEEP_RADIUS * 1.3f, profile.sweepRadius)
        assertClose(1.15f, profile.physicalWeight)
    }

    @Test
    fun `only arm hardware feeds strength`() {
        val sensoryOnly = AugmentBinding.derive(
            mapOf(AugmentSlot.SENSORY to "Quantum Eye"), catalogue
        )
        assertClose(0f, sensoryOnly.strengthMultiplier)
    }

    @Test
    fun `neural hardware raises synergy chance and cuts cooldowns`() {
        val profile = AugmentBinding.derive(
            mapOf(AugmentSlot.CRANIAL to "Neural Processor"), catalogue
        )
        assertTrue(profile.synergyChance > AugmentBinding.BASE_SYNERGY_CHANCE)
        assertTrue(profile.cooldownMultiplier < 1f)
    }

    @Test
    fun `sensory MAG does not count as neural hardware`() {
        val profile = AugmentBinding.derive(
            mapOf(AugmentSlot.SENSORY to "Quantum Eye"), catalogue
        )
        assertClose(AugmentBinding.BASE_SYNERGY_CHANCE, profile.synergyChance)
        assertClose(1f, profile.cooldownMultiplier)
    }

    @Test
    fun `derived attributes stay inside their guard rails`() {
        // A catalogue entry far beyond anything the game ships, to exercise the clamps.
        val absurd = listOf(
            AugmentChip("Overclock", AugmentSlot.CRANIAL, "test", magBonus = 100_000),
            AugmentChip("Fortune", AugmentSlot.SENSORY, "test", lckBonus = 100_000)
        )
        val profile = AugmentBinding.derive(
            mapOf(AugmentSlot.CRANIAL to "Overclock", AugmentSlot.SENSORY to "Fortune"), absurd
        )
        assertTrue(profile.synergyChance <= 0.95f)
        assertTrue(profile.cooldownMultiplier >= 0.4f)
        assertTrue(profile.critChance <= 0.75f)
    }

    @Test
    fun `chips absent from the catalogue are ignored rather than crashing`() {
        val profile = AugmentBinding.derive(
            mapOf(AugmentSlot.ARMS to "Nonexistent Chip"), catalogue
        )
        assertClose(0f, profile.strengthMultiplier)
    }

    @Test
    fun `a chip installed in the wrong slot does not apply`() {
        val profile = AugmentBinding.derive(
            mapOf(AugmentSlot.LEGS to "Cybernetic Muscles"), catalogue
        )
        assertClose(0f, profile.strengthMultiplier)
    }
}

class ModelInvariantsTest {

    @Test
    fun `the default power allocation fits inside the ship power budget`() {
        val allocated = StarshipState().systemPowerAllocation.values.sum()
        assertTrue(
            allocated <= SHIP_POWER_BUDGET,
            "default allocation $allocated exceeds budget $SHIP_POWER_BUDGET"
        )
        assertEquals(ShipSystem.entries.size, StarshipState().systemPowerAllocation.size)
    }

    @Test
    fun `the default starting system exists so warp jumps have an origin`() {
        assertEquals("sys_sol_prime", StarshipState().currentSystemId)
    }

    @Test
    fun `reputation tiers are ordered by ascending threshold`() {
        val thresholds = ReputationTier.entries.map { it.threshold }
        assertEquals(thresholds.sorted(), thresholds)
    }
}
