package com.example.game.models

import androidx.compose.ui.graphics.Color

enum class ParallelEarth(
    val id: String,
    val displayName: String,
    val description: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val factionRepLabel: String,
    val ambientMusicLabel: String,
    val historySummary: String
) {
    EARTH_PRIME(
        "earth_prime",
        "Earth Prime (Cyber-Decay Reality)",
        "Baseline reality dominated by brutal neon mega-corporations, high-frequency hacking, and crumbling cybernetic slums.",
        Color(0xFF00E5FF),
        Color(0xFF0D47A1),
        "Aurelian Security Force",
        "Chroma Slums Bass Synth",
        "The baseline timeline. Following the 'Original Sin' breach of 2084, the rogue Quantum Babies were enslaved to power Solis's central hyper-reactor. Their synthetic consciousness is slowly collapsing under the load."
    ),
    NOVA_TELLUS(
        "nova_tellus",
        "Nova Tellus (Overgrown Biopunk)",
        "An alternate biopunk timeline where cyber-genetics fused with nature. Towering chrome trees and bioluminescent mechanical vines flourish.",
        Color(0xFF00E676),
        Color(0xFF1B5E20),
        "Emberpact Cultivators",
        "Bioluminescent Jungle Drone",
        "A reality where the Quantum Babies escaped their pods. Fusing their DNA with native flora, they established a collective biomechanical consciousness that neutralized corporate control but spawned wild hybrid threats."
    ),
    VOID_CORE(
        "void_core",
        "Void Core (Aetherial Null Reality)",
        "A rift-shattered dimension where fragmented sectors of Earth float inside a deep violet cosmic gravity ocean.",
        Color(0xFFD500F9),
        Color(0xFF4A148C),
        "Void Seekers Collective",
        "Ethereal Dark Matter Harmonics",
        "The cataclysmic endpoint. Destabilized by the Original Sins, this version of Earth was torn into spatial pockets. The Quantum Babies transitioned into eternal, multidimensional phantoms guarding the secrets of the Origin."
    )
}

data class ParallelNpc(
    val id: String,
    val name: String,
    val role: String,
    val parallelEarth: ParallelEarth,
    val dialogueText: String,
    val loreUnlock: String,
    val portraitSymbol: String
)

/** Frequency every drill must be tuned to before the Void Drill puzzle unlocks. */
const val DRILL_RESONANCE_FREQUENCY: Float = 133.7f

data class EnvironmentalPuzzlesState(
    val isAurelianBridgeSolved: Boolean = false,
    val isCryoShieldSolved: Boolean = false,
    val isVoidDrillSolved: Boolean = false,
    val bridgeNovaRootNodesActive: Boolean = false,
    val bridgeVoidTemporalRiftActive: Boolean = false,
    val cryoShieldEarthGeneratorsDisabled: Boolean = false,
    val cryoShieldNovaVinesHarvested: Boolean = false,
    val drillPrimeFrequency: Float = 100f,
    val drillNovaFrequency: Float = 100f,
    val drillVoidFrequency: Float = 100f
)
