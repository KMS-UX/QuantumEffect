package com.example.game.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.game.models.EnvironmentalPuzzlesState
import com.example.game.models.ParallelEarth
import com.example.game.models.ParallelNpc
import com.example.game.viewmodel.GameViewModel
import com.example.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun QuantumWarpView(
    viewModel: GameViewModel,
    gameState: com.example.game.db.GameState
) {
    val currentEarth by viewModel.currentParallelEarth.collectAsStateWithLifecycle()
    val unlockedGates by viewModel.unlockedGates.collectAsStateWithLifecycle()
    val puzzlesState by viewModel.puzzlesState.collectAsStateWithLifecycle()
    val isWarping by viewModel.isWarping.collectAsStateWithLifecycle()
    val warpProgress by viewModel.warpProgress.collectAsStateWithLifecycle()
    val warpMessage by viewModel.warpMessage.collectAsStateWithLifecycle()

    var activeCompanionTab by remember { mutableStateOf("Lyra") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(QuantumDarkBg)
    ) {
        if (isWarping) {
            // Quantum Warp Sequence transition (Shader-based simulation)
            WarpTransitionScreen(
                progress = warpProgress,
                message = warpMessage,
                targetColor = currentEarth.primaryColor
            )
        } else {
            // Main reality station console
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Main Terminal Header
                CyberCard(borderColor = currentEarth.primaryColor) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🌀 QUANTUM WARP CONTROL STATION",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = currentEarth.primaryColor,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "COGNITIVE ENTANGLEMENT AXIS: STABILIZED",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            color = QuantumLightText.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                .border(1.dp, currentEarth.primaryColor.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "ACTIVE HARMONIC FREQUENCY:".uppercase(),
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = currentEarth.primaryColor
                                )
                                Text(
                                    text = currentEarth.displayName.uppercase(),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.White
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "MUTATOR RATE:".uppercase(),
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = QuantumNeonOrange
                                )
                                Text(
                                    text = when(currentEarth) {
                                        ParallelEarth.EARTH_PRIME -> "BASELINE"
                                        ParallelEarth.NOVA_TELLUS -> "+50% REGEN, -20% DEF"
                                        ParallelEarth.VOID_CORE -> "+100% SPATIAL DMG"
                                    },
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                // 1. Reality Shifting Hub
                Text(
                    text = "🌐 AVAILABLE REALITIES FOR CONSCIOUS TRANSIT",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = QuantumLightText
                )

                ParallelEarth.entries.forEach { earth ->
                    val isCurrent = currentEarth == earth
                    CyberCard(borderColor = if (isCurrent) QuantumNeonGreen else earth.primaryColor) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = earth.displayName.uppercase(),
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = earth.primaryColor
                                )
                                if (isCurrent) {
                                    Text(
                                        text = "✓ STABILIZED ACTIVE",
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp,
                                        color = QuantumNeonGreen,
                                        modifier = Modifier
                                            .background(QuantumNeonGreen.copy(alpha = 0.15f))
                                            .border(1.dp, QuantumNeonGreen, RoundedCornerShape(3.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                } else {
                                    Text(
                                        text = "DISCOVERED GATE",
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp,
                                        color = QuantumNeonOrange,
                                        modifier = Modifier
                                            .background(QuantumNeonOrange.copy(alpha = 0.1f))
                                            .border(1.dp, QuantumNeonOrange.copy(alpha = 0.4f), RoundedCornerShape(3.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = earth.description,
                                fontSize = 11.sp,
                                color = QuantumLightText.copy(alpha = 0.8f),
                                lineHeight = 15.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Ambient Frequency: ${earth.ambientMusicLabel} | Faction: ${earth.factionRepLabel}".uppercase(),
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace,
                                color = QuantumNeonBlue
                            )
                            
                            if (!isCurrent) {
                                Spacer(modifier = Modifier.height(10.dp))
                                CyberButton(
                                    onClick = { viewModel.startWarpShift(earth) {} },
                                    text = "Initiate Warp Shift",
                                    color = earth.primaryColor,
                                    modifier = Modifier.fillMaxWidth().testTag("warp_btn_${earth.id}")
                                )
                            }
                        }
                    }
                }

                // 2. Environmental Reality Puzzles
                Text(
                    text = "🧩 SYNCHRONIZED ENVIRONMENTAL REALITY PUZZLES",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = QuantumLightText
                )

                // Puzzle 1
                CyberCard(borderColor = if (puzzlesState.isAurelianBridgeSolved) QuantumNeonGreen else QuantumNeonPurple) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🌉 AURELIAN ARCHIVE HARD-LIGHT BRIDGE",
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (puzzlesState.isAurelianBridgeSolved) QuantumNeonGreen else Color.White
                            )
                            Text(
                                text = if (puzzlesState.isAurelianBridgeSolved) "RESOLVED" else "BLOCKED",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = if (puzzlesState.isAurelianBridgeSolved) QuantumNeonGreen else QuantumNeonRed
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "A deep chasm blocks the path to the original coordinates in Earth Prime (Grasslands). Complete reality-spanning conduits to deploy a hard-light bridge.",
                            fontSize = 11.sp,
                            color = QuantumLightText.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Sub-elements
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Nova node
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "NOVA TELLUS CONDUIT:".uppercase(),
                                        fontSize = 9.sp,
                                        fontFamily = FontFamily.Monospace,
                                        color = QuantumNeonGreen
                                    )
                                    Text(
                                        text = "Biomechanical Moss Roots",
                                        fontSize = 11.sp,
                                        color = Color.White
                                    )
                                }
                                CyberButton(
                                    onClick = { viewModel.toggleBridgeNovaRootNodes() },
                                    text = if (puzzlesState.bridgeNovaRootNodesActive) "ACTIVE" else "OFFLINE",
                                    color = if (puzzlesState.bridgeNovaRootNodesActive) QuantumNeonGreen else Color.Gray,
                                    modifier = Modifier.width(100.dp)
                                )
                            }
                            
                            // Void node
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "VOID CORE CONDUIT:".uppercase(),
                                        fontSize = 9.sp,
                                        fontFamily = FontFamily.Monospace,
                                        color = QuantumNeonPurple
                                    )
                                    Text(
                                        text = "Gravitational Core Rift",
                                        fontSize = 11.sp,
                                        color = Color.White
                                    )
                                }
                                CyberButton(
                                    onClick = { viewModel.toggleBridgeVoidTemporalRift() },
                                    text = if (puzzlesState.bridgeVoidTemporalRiftActive) "CHAFED" else "STABLE",
                                    color = if (puzzlesState.bridgeVoidTemporalRiftActive) QuantumNeonPurple else Color.Gray,
                                    modifier = Modifier.width(100.dp)
                                )
                            }
                        }

                        if (puzzlesState.isAurelianBridgeSolved) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "⚡ HARD-LIGHT BRIDGE ONLINE: A previously blocked path in Aurelian Grasslands has been bridged. Proceed to the Archive ruins to retrieve lost archives!",
                                fontSize = 11.sp,
                                color = QuantumNeonGreen,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                // Puzzle 2
                CyberCard(borderColor = if (puzzlesState.isCryoShieldSolved) QuantumNeonGreen else QuantumNeonBlue) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "❄️ CRYO-VAULT THERMAL SHIELD DECAY",
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (puzzlesState.isCryoShieldSolved) QuantumNeonGreen else Color.White
                            )
                            Text(
                                text = if (puzzlesState.isCryoShieldSolved) "OFFLINE" else "SHIELD ACTIVE",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = if (puzzlesState.isCryoShieldSolved) QuantumNeonGreen else QuantumNeonRed
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "A high-frequency cold laser shield blocks access to the Frozen Crypt Container in Snow Biome. Re-route cooling units across timelines to trigger decay.",
                            fontSize = 11.sp,
                            color = QuantumLightText.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Sub-elements
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Earth Prime node
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "EARTH PRIME DECK:".uppercase(),
                                        fontSize = 9.sp,
                                        fontFamily = FontFamily.Monospace,
                                        color = QuantumNeonBlue
                                    )
                                    Text(
                                        text = "Main Generator Cooling",
                                        fontSize = 11.sp,
                                        color = Color.White
                                    )
                                }
                                CyberButton(
                                    onClick = { viewModel.toggleCryoShieldEarthGenerators() },
                                    text = if (puzzlesState.cryoShieldEarthGeneratorsDisabled) "SHUTDOWN" else "COOLING",
                                    color = if (puzzlesState.cryoShieldEarthGeneratorsDisabled) QuantumNeonOrange else QuantumNeonBlue,
                                    modifier = Modifier.width(110.dp)
                                )
                            }
                            
                            // Nova node
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "NOVA TELLUS DECK:".uppercase(),
                                        fontSize = 9.sp,
                                        fontFamily = FontFamily.Monospace,
                                        color = QuantumNeonGreen
                                    )
                                    Text(
                                        text = "Thermal Vine Enzymes",
                                        fontSize = 11.sp,
                                        color = Color.White
                                    )
                                }
                                CyberButton(
                                    onClick = { viewModel.toggleCryoShieldNovaVines() },
                                    text = if (puzzlesState.cryoShieldNovaVinesHarvested) "HARVESTED" else "CLOSED",
                                    color = if (puzzlesState.cryoShieldNovaVinesHarvested) QuantumNeonGreen else Color.Gray,
                                    modifier = Modifier.width(110.dp)
                                )
                            }
                        }

                        if (puzzlesState.isCryoShieldSolved) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "⚡ CRYO SHIELD MUTATED: The Sub-Zero Crypt Vault in the polar snow caps has decayed. You can now gather singularium shards and craft top-tier auxiliary cores!",
                                fontSize = 11.sp,
                                color = QuantumNeonGreen,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                // Puzzle 3
                CyberCard(borderColor = if (puzzlesState.isVoidDrillSolved) QuantumNeonGreen else QuantumNeonOrange) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⛏️ VOIDIUM CORE DRILL MATRIX HARMONIZATION",
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (puzzlesState.isVoidDrillSolved) QuantumNeonGreen else Color.White
                            )
                            Text(
                                text = if (puzzlesState.isVoidDrillSolved) "ONLINE" else "DE-TUNED",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                color = if (puzzlesState.isVoidDrillSolved) QuantumNeonGreen else QuantumNeonRed
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "To power the Heavy Mining Drill and breach the Ruins' subterranean observatory, all three spatial nodes must be tuned to exactly 133.7 Hz.",
                            fontSize = 11.sp,
                            color = QuantumLightText.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        // Sub-elements (3 frequency controllers)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Prime Node
                            FrequencyTuningRow(
                                label = "Earth Prime Node",
                                currentFreq = puzzlesState.drillPrimeFrequency,
                                color = QuantumNeonBlue,
                                onFreqChange = { viewModel.tuneDrillFrequency(ParallelEarth.EARTH_PRIME, it) }
                            )

                            // Nova Node
                            FrequencyTuningRow(
                                label = "Nova Tellus Node",
                                currentFreq = puzzlesState.drillNovaFrequency,
                                color = QuantumNeonGreen,
                                onFreqChange = { viewModel.tuneDrillFrequency(ParallelEarth.NOVA_TELLUS, it) }
                            )

                            // Void Node
                            FrequencyTuningRow(
                                label = "Void Core Node",
                                currentFreq = puzzlesState.drillVoidFrequency,
                                color = QuantumNeonPurple,
                                onFreqChange = { viewModel.tuneDrillFrequency(ParallelEarth.VOID_CORE, it) }
                            )
                        }

                        if (puzzlesState.isVoidDrillSolved) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "⚡ DRILL HARMONIZED: Subterranean Obsidian Drill activated! The path is open in Ruins to discover the ancient Void Observatory containing files on the Sins.",
                                fontSize = 11.sp,
                                color = QuantumNeonGreen,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                // 3. Alternate NPC Timelines
                Text(
                    text = "👥 ALTERNATE HISTORY NPC PORTRAITS",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = QuantumLightText
                )

                CyberCard(borderColor = currentEarth.primaryColor) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Observe how companions' consciousnesses are split across timelines. Tap on a companion to inspect their alternative records:",
                            fontSize = 11.sp,
                            color = QuantumLightText.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Horiz Companion Selectors
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf("Lyra", "Nix", "Drox").forEach { name ->
                                val isActive = activeCompanionTab == name
                                Box(
                                    modifier = Modifier
                                        .clickable { activeCompanionTab = name }
                                        .background(
                                            if (isActive) currentEarth.primaryColor.copy(alpha = 0.15f) else Color.Transparent,
                                            RoundedCornerShape(4.dp)
                                        )
                                        .border(
                                            1.dp,
                                            if (isActive) currentEarth.primaryColor else Color.Gray.copy(alpha = 0.3f),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = name.uppercase(),
                                        fontSize = 10.sp,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isActive) Color.White else Color.Gray
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Side by side alternative profiles
                        val companionNpcs = when (activeCompanionTab) {
                            "Lyra" -> listOf(
                                ParallelNpc(
                                    "lyra_prime", "Lyra (Prime)", "Solis Aegis Guard", ParallelEarth.EARTH_PRIME,
                                    "Sentinel Commander Lyra. 'The Neo Solis sector is under strict lockdown. Corporate security overrides local comfort. Keep moving, kid.'",
                                    "Unlock: Recover files on her corporate cybernetic eyes.", "🏹"
                                ),
                                ParallelNpc(
                                    "lyra_nova", "Lyra (Nova)", "Wildwood Shaman", ParallelEarth.NOVA_TELLUS,
                                    "Biopunk druid. 'The Chrome Spores have infested my mechanical wings, sibling. They whisper that your arrival was written in the rings of the Iron Oaks.'",
                                    "Unlock: Solve Bridge Puzzle to read her rebellion logs.", "🌿"
                                ),
                                ParallelNpc(
                                    "lyra_void", "Lyra (Void)", "Temporal Spectre", ParallelEarth.VOID_CORE,
                                    "Gravity Phantom. 'A reflection of an infant in an incubator... Do you remember the cold water? Our timeline splits started in the same nursery.'",
                                    "Unlock: All 3 puzzles solved for final integration.", "🔮"
                                )
                            )
                            "Nix" -> listOf(
                                ParallelNpc(
                                    "nix_prime", "Nix (Prime)", "Grid Hacker", ParallelEarth.EARTH_PRIME,
                                    "Grid Hacker Nix. 'I've tapped the central Aurelian reactor lines. They're mining pure spatial matter. We need to trigger an emergency vent!'",
                                    "Unlock: Access the Scrapyard Jammer quest.", "🧑‍💻"
                                ),
                                ParallelNpc(
                                    "nix_nova", "Nix (Nova)", "Silicon Cultivator", ParallelEarth.NOVA_TELLUS,
                                    "Arbor-technician. 'My neural port is spliced into this towering oak tree. Bioluminescent sap is a much faster superconductor than copper.'",
                                    "Unlock: Discover the Moss Spore Nodes.", "🌲"
                                ),
                                ParallelNpc(
                                    "nix_void", "Nix (Void)", "Cosmic Nomad", ParallelEarth.VOID_CORE,
                                    "Void Hermit. 'Spacetime has no bugs. Only echoes of dead coordinate files. Stop trying to repair the server. Let it dissolve.'",
                                    "Unlock: Solve Matrix Drill puzzle to hear his final console log.", "🌌"
                                )
                            )
                            else -> listOf(
                                ParallelNpc(
                                    "drox_prime", "Drox (Prime)", "Security Brute", ParallelEarth.EARTH_PRIME,
                                    "Exo Enforcer. 'My shield block has 300 armor. I don't give a damn about corporate politics, but they pay the nanites. Don't test me.'",
                                    "Unlock: Recruit Drox at level 5.", "🦖"
                                ),
                                ParallelNpc(
                                    "drox_nova", "Drox (Nova)", "Iron Golem", ParallelEarth.NOVA_TELLUS,
                                    "Colossus golem. 'The moss shields my core. Corporate steel has decayed. I protect the nursery sprouts now.'",
                                    "Unlock: Feed 10 organic materials in biopunk ruins.", "🍄"
                                ),
                                ParallelNpc(
                                    "drox_void", "Drox (Void)", "Gravitational Warden", ParallelEarth.VOID_CORE,
                                    "Gravity Golem. 'Do not slip over the floating edges. The violet gravity ocean consumes all atomic structures. Stand behind my shield.'",
                                    "Unlock: Solve cryo puzzle.", "☄️"
                                )
                            )
                        }

                        companionNpcs.forEach { npc ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .background(npc.parallelEarth.primaryColor.copy(alpha = 0.05f), RoundedCornerShape(4.dp))
                                    .border(1.dp, npc.parallelEarth.primaryColor.copy(alpha = 0.25f), RoundedCornerShape(4.dp))
                                    .padding(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = npc.portraitSymbol,
                                        fontSize = 24.sp,
                                        modifier = Modifier
                                            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                            .padding(6.dp)
                                    )
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = npc.name,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.Monospace,
                                            color = npc.parallelEarth.primaryColor
                                        )
                                        Text(
                                            text = npc.role.uppercase() + " | " + npc.parallelEarth.displayName,
                                            fontSize = 8.sp,
                                            fontFamily = FontFamily.Monospace,
                                            color = QuantumLightText.copy(alpha = 0.5f)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = npc.dialogueText,
                                            fontSize = 10.5.sp,
                                            color = QuantumLightText,
                                            lineHeight = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // 4. Story Progression & Revelations
                val solvedCount = listOf(puzzlesState.isAurelianBridgeSolved, puzzlesState.isCryoShieldSolved, puzzlesState.isVoidDrillSolved).count { it }
                
                Text(
                    text = "📖 MEMORY CHRONICLES & ORIGINAL SINS ARCHIVE ($solvedCount/3 FILES UNLOCKED)",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = QuantumLightText
                )

                // Log 1
                RevelationLogCard(
                    title = "ARCHIVE LOG 01: THE SEVEN ORIGINAL QUANTUM BABIES",
                    desc = "Exposed to raw Singularium particles at birth, seven infants became organically linked to parallel timelines.",
                    expandedText = "In 2081, rogue Aurelian quantum physicists initiated Project Genesis. By placing newborn infants inside the Singularium Core during a localized rift breach, their neural synapses became permanently entangled with their physical counterparts on alternate versions of Earth. They became a biological GPS—their thoughts could anchor dimensional portals. They survived, but were torn into multi-plane consciousnesses.",
                    isUnlocked = puzzlesState.isAurelianBridgeSolved,
                    themeColor = QuantumNeonPurple
                )

                // Log 2
                RevelationLogCard(
                    title = "ARCHIVE LOG 02: THE ORIGINAL SIN OF 2084",
                    desc = "Aurelian Corporations built Dimension Shredders to harvest oil and minerals from parallel Earths, causing decay.",
                    expandedText = "The Original Sin was not scientific curiosity; it was greed. Neo Solis Citadel leaders built the 'Dimension Shredders' around the children's brains, forcing open rifts to siphons coal, voidium, and oil from alternate, undefended Earths. This siphoning drained the life-force of alternate worlds, rotting their ecosystems and spawning the Void Sea rifts that now threaten to swallow our baseline reality.",
                    isUnlocked = puzzlesState.isCryoShieldSolved,
                    themeColor = QuantumNeonBlue
                )

                // Log 3
                RevelationLogCard(
                    title = "ARCHIVE LOG 03: THE TIMELINE CONSENSUS RESOLUTION",
                    desc = "The final records detailing how the Quantum Babies can merge the dimensional fragments.",
                    expandedText = "The Void Sea is the blood of parallel Earths we mined to death. The Quantum Babies, now scattered as phantoms across Earth Prime, Nova Tellus, and Void Core, are calling. By stabilizing the Quantum Gates and harmonizing all reality frequencies to 133.7 Hz, you can merge the timeline splinters back into a single, unified Earth, healing the spatial wounds and curing the void corruption forever.",
                    isUnlocked = puzzlesState.isVoidDrillSolved,
                    themeColor = QuantumNeonOrange
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun FrequencyTuningRow(
    label: String,
    currentFreq: Float,
    color: Color,
    onFreqChange: (Float) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label.uppercase(),
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace,
                color = color
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = String.format("%.1f Hz", currentFreq),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = if (currentFreq == 133.7f) QuantumNeonGreen else Color.White
                )
                if (currentFreq == 133.7f) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "✓ SYNCED",
                        fontSize = 8.sp,
                        color = QuantumNeonGreen,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            CyberButton(
                onClick = { onFreqChange((currentFreq - 10f).coerceAtLeast(0f)) },
                text = "-10",
                color = Color.DarkGray,
                modifier = Modifier.width(55.dp)
            )
            CyberButton(
                onClick = { onFreqChange(currentFreq + 10f) },
                text = "+10",
                color = Color.DarkGray,
                modifier = Modifier.width(55.dp)
            )
            CyberButton(
                onClick = { onFreqChange(133.7f) },
                text = "TUNE",
                color = color,
                modifier = Modifier.width(65.dp)
            )
        }
    }
}

@Composable
fun RevelationLogCard(
    title: String,
    desc: String,
    expandedText: String,
    isUnlocked: Boolean,
    themeColor: Color
) {
    var isExpanded by remember { mutableStateOf(false) }

    CyberCard(borderColor = if (isUnlocked) themeColor else Color.Gray.copy(alpha = 0.5f)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = if (isUnlocked) themeColor else Color.Gray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (isUnlocked) "UNLOCKED" else "LOCKED",
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = if (isUnlocked) QuantumNeonGreen else QuantumNeonRed
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = desc,
                fontSize = 10.sp,
                color = QuantumLightText.copy(alpha = 0.6f),
                lineHeight = 14.sp
            )

            if (isUnlocked) {
                Spacer(modifier = Modifier.height(8.dp))
                if (isExpanded) {
                    Text(
                        text = expandedText,
                        fontSize = 10.5.sp,
                        fontFamily = FontFamily.Monospace,
                        color = QuantumLightText,
                        lineHeight = 15.sp,
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                            .border(0.5.dp, themeColor.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CyberButton(
                        onClick = { isExpanded = false },
                        text = "Close File",
                        color = themeColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    CyberButton(
                        onClick = { isExpanded = true },
                        text = "Read Chronicle File",
                        color = themeColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "🔒 Resolve associated reality-puzzles above to unlock decryption keys.",
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    color = QuantumNeonRed.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun WarpTransitionScreen(
    progress: Float,
    message: String,
    targetColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "warp")
    
    // Pulse scale animation
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Ring rotation animation
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotate"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Starfield / Grid Warp Canvas background (simulated HD-2D cyberpunk portal)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cx = size.width / 2f
            val cy = size.height / 2f
            
            // Draw radial warp grid lines
            for (i in 0..12) {
                val angle = (i * 30 + rotationAngle) * (Math.PI.toFloat() / 180f)
                val ex = cx + TrigLUT.cos(angle) * size.width
                val ey = cy + TrigLUT.sin(angle) * size.height
                drawLine(
                    color = targetColor.copy(alpha = 0.12f),
                    start = Offset(cx, cy),
                    end = Offset(ex, ey),
                    strokeWidth = 2f
                )
            }

            // Draw concentric expanding rings of reality distortion
            for (r in 1..4) {
                val radius = (r * 100f * pulseScale) % (size.width / 1.5f)
                drawCircle(
                    color = targetColor.copy(alpha = (1.0f - (radius / (size.width / 1.5f))) * 0.4f),
                    radius = radius,
                    center = Offset(cx, cy),
                    style = Stroke(
                        width = 4f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
                    )
                )
            }

            // Draw scanning glitch matrices
            val randY = (System.currentTimeMillis() % 100) / 100f * size.height
            drawLine(
                color = targetColor.copy(alpha = 0.35f),
                start = Offset(0f, randY),
                end = Offset(size.width, randY),
                strokeWidth = 3f
            )

            // Random virtual space debris / floating qubits
            for (j in 0..15) {
                val rand = Random(j * 333L)
                val rx = rand.nextFloat() * size.width
                val ry = rand.nextFloat() * size.height
                val rsize = rand.nextFloat() * 12f + 4f
                drawRect(
                    color = targetColor.copy(alpha = rand.nextFloat() * 0.4f + 0.1f),
                    topLeft = Offset(rx, ry),
                    size = Size(rsize, rsize)
                )
            }
        }

        // Concentric physical HUD overlay
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "🌀 SHIFTING DIMENSIONS",
                color = targetColor,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "SPATIAL COORDINATES MUTATION UNDERWAY",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 8.sp,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Pulse center reactor
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(targetColor.copy(alpha = 0.05f), RoundedCornerShape(60.dp))
                    .border(2.dp, targetColor, RoundedCornerShape(60.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = String.format("%.0f%%", progress * 100f),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Hacking text logs
            Text(
                text = message,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().height(40.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tech Progress bar
            LinearProgressIndicator(
                progress = { progress },
                color = targetColor,
                trackColor = Color.DarkGray.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .border(0.5.dp, targetColor.copy(alpha = 0.5f), RoundedCornerShape(3.dp))
            )
        }
    }
}
