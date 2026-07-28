package com.example.game.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.models.*
import com.example.game.viewmodel.GameViewModel
import com.example.ui.theme.*
import kotlin.math.sqrt
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.animation.core.*

import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun StarshipCommandView(
    viewModel: GameViewModel,
    gameState: com.example.game.db.GameState
) {
    val shipState by viewModel.starshipState.collectAsStateWithLifecycle()
    val combatState by viewModel.spaceCombat.collectAsStateWithLifecycle()
    
    var localTab by remember { mutableStateOf("BRIDGE") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF060B15))
            .testTag("starship_view_root")
    ) {
        // Starfield Ambient Background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val random = java.util.Random(42)
            for (i in 0..60) {
                val x = random.nextFloat() * size.width
                val y = random.nextFloat() * size.height
                val radius = random.nextFloat() * 2f + 1f
                val alpha = random.nextFloat() * 0.7f + 0.3f
                drawCircle(
                    color = Color.White.copy(alpha = alpha),
                    radius = radius,
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Header with status indicator
            ShipHeaderSection(shipState = shipState, playerCredits = gameState.credits, playerNanites = gameState.nanites)

            Spacer(modifier = Modifier.height(10.dp))

            // Sub tab bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                    .border(1.dp, QuantumBorder, RoundedCornerShape(8.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(
                    "BRIDGE" to "🛰️ BRIDGE",
                    "GALAXY" to "🌌 GALAXY",
                    "CREW" to "👥 CREW",
                    "UPGRADES" to "🛠️ ARMORY",
                    "TIMELINE" to "🧬 MATRIX"
                ).forEach { (tabKey, label) ->
                    val isSelected = localTab == tabKey
                    val activeColor = when (tabKey) {
                        "BRIDGE" -> QuantumNeonOrange
                        "GALAXY" -> QuantumNeonPurple
                        "CREW" -> QuantumNeonBlue
                        "UPGRADES" -> QuantumNeonGreen
                        "TIMELINE" -> QuantumNeonRed
                        else -> QuantumNeonPurple
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { localTab = tabKey }
                            .background(
                                if (isSelected) activeColor.copy(alpha = 0.15f) else Color.Transparent,
                                RoundedCornerShape(6.dp)
                            )
                            .border(
                                BorderStroke(1.dp, if (isSelected) activeColor else Color.Transparent),
                                RoundedCornerShape(6.dp)
                            )
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) activeColor else QuantumGrayText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Main Active Sub-view Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (localTab) {
                    "BRIDGE" -> BridgeControlPanel(shipState = shipState, viewModel = viewModel, playerCredits = gameState.credits, playerNanites = gameState.nanites)
                    "GALAXY" -> GalaxyNavigationPanel(shipState = shipState, viewModel = viewModel)
                    "CREW" -> CrewRosterPanel(shipState = shipState, viewModel = viewModel, playerCredits = gameState.credits)
                    "UPGRADES" -> ShipUpgradesPanel(shipState = shipState, viewModel = viewModel, playerCredits = gameState.credits, playerNanites = gameState.nanites)
                    "TIMELINE" -> TimelineMatrixPanel(shipState = shipState, viewModel = viewModel)
                }
            }
        }

        // Space Combat overlay if combat is active
        if (combatState.isCombatActive) {
            SpaceCombatOverlay(combatState = combatState, shipState = shipState, viewModel = viewModel)
        }
    }
}

@Composable
fun ShipHeaderSection(
    shipState: StarshipState,
    playerCredits: Int,
    playerNanites: Int
) {
    val activeSystem = shipState.starSystems.find { it.id == shipState.currentSystemId }
    val systemName = activeSystem?.name ?: "Deep Unknown Space"

    val infiniteTransition = rememberInfiniteTransition(label = "HeaderAlert")
    val alertAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "AlertAlpha"
    )

    val isCritical = (shipState.hull / shipState.maxHull) < 0.4f
    val isShieldLow = (shipState.shield / shipState.maxShield) < 0.3f && shipState.maxShield > 0f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            .border(
                BorderStroke(
                    1.dp,
                    if (isCritical) QuantumNeonRed.copy(alpha = alertAlpha)
                    else if (isShieldLow) QuantumNeonBlue.copy(alpha = alertAlpha)
                    else QuantumBorder
                ),
                RoundedCornerShape(10.dp)
            )
            .padding(12.dp)
    ) {
        if (isCritical || isShieldLow) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
                    .background(
                        if (isCritical) QuantumNeonRed.copy(alpha = 0.1f * alertAlpha)
                        else QuantumNeonBlue.copy(alpha = 0.1f * alertAlpha),
                        RoundedCornerShape(4.dp)
                    )
                    .border(
                        1.dp,
                        if (isCritical) QuantumNeonRed.copy(alpha = alertAlpha)
                        else QuantumNeonBlue.copy(alpha = alertAlpha),
                        RoundedCornerShape(4.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = if (isCritical) "🚨 SYSTEM ALERT: HULL INTEGRITY CRITICAL - REPAIR IMMEDIATELY!"
                           else "🛡️ WARNING: DEFENSIVE SHIELDS DEPLETED!",
                    color = if (isCritical) QuantumNeonRed else QuantumNeonBlue,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = shipState.name,
                    color = QuantumNeonOrange,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "Sector: $systemName",
                    color = QuantumNeonPurple,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "💰 $playerCredits Credits",
                    color = Color.Yellow,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "💎 $playerNanites Nanites",
                    color = QuantumNeonBlue,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Status bars
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // HULL bar
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("🛡️ SHIELD", color = QuantumNeonBlue, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                    Text("${shipState.shield.toInt()}/${shipState.maxShield.toInt()}", color = QuantumNeonBlue, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                }
                Spacer(modifier = Modifier.height(3.dp))
                LinearProgressIndicator(
                    progress = { shipState.shield / shipState.maxShield },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = QuantumNeonBlue,
                    trackColor = Color(0xFF111E2E)
                )
            }

            // HULL bar
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("❤️ HULL HP", color = QuantumNeonRed, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                    Text("${shipState.hull.toInt()}/${shipState.maxHull.toInt()}", color = QuantumNeonRed, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                }
                Spacer(modifier = Modifier.height(3.dp))
                LinearProgressIndicator(
                    progress = { shipState.hull / shipState.maxHull },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = QuantumNeonRed,
                    trackColor = Color(0xFF261214)
                )
            }

            // FUEL bar
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("⚡ HYPER-FUEL", color = QuantumNeonOrange, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                    Text("${shipState.fuel.toInt()}/${shipState.maxFuel.toInt()}", color = QuantumNeonOrange, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                }
                Spacer(modifier = Modifier.height(3.dp))
                LinearProgressIndicator(
                    progress = { shipState.fuel / shipState.maxFuel },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = QuantumNeonOrange,
                    trackColor = Color(0xFF2A1B0E)
                )
            }
        }
    }
}

@Composable
fun BridgeControlPanel(
    shipState: StarshipState,
    viewModel: GameViewModel,
    playerCredits: Int,
    playerNanites: Int
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            // Welcome banner / view description
            Text(
                text = "⚡ STARSHIP SYSTEM ALLOCATOR",
                color = Color.White,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        // Power Grid allocation cards
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                    .border(1.dp, QuantumBorder, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Reactor Core Grid", color = QuantumNeonPurple, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    val totalAllocated = shipState.systemPowerAllocation.values.sum()
                    Text("Power allocated: $totalAllocated / 12 Units", color = Color.White, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                }
                
                Spacer(modifier = Modifier.height(10.dp))

                ShipSystem.values().forEach { sys ->
                    val allocated = shipState.systemPowerAllocation[sys] ?: 0
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(sys.icon, modifier = Modifier.padding(end = 6.dp))
                                Text(sys.displayName, color = sys.color, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Button(
                                    onClick = { viewModel.allocateSystemPower(sys, maxOf(0, allocated - 1)) },
                                    colors = ButtonDefaults.buttonColors(containerColor = QuantumBorder),
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Text("-", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                                Text("$allocated", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace, modifier = Modifier.width(18.dp), textAlign = TextAlign.Center)
                                Button(
                                    onClick = { viewModel.allocateSystemPower(sys, minOf(4, allocated + 1)) },
                                    colors = ButtonDefaults.buttonColors(containerColor = QuantumBorder),
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Text("+", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Cargo inventory & Refinery refuel station
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Cargo list
                Column(
                    modifier = Modifier
                        .weight(1.2f)
                        .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .border(1.dp, QuantumBorder, RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text("📦 Cargo Manifest", color = QuantumNeonBlue, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Spacer(modifier = Modifier.height(6.dp))
                    shipState.cargo.forEach { (item, qty) ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(item, color = QuantumGrayText, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                            Text("x$qty", color = Color.White, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                        }
                    }
                }

                // Station Refueling and Repairs
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .border(1.dp, QuantumBorder, RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text("🛠️ Maintenance", color = QuantumNeonGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Spacer(modifier = Modifier.height(8.dp))

                    val missingFuel = shipState.maxFuel - shipState.fuel
                    val fuelCost = (missingFuel * 1.5f).toInt()
                    Button(
                        onClick = { viewModel.refuelShip() },
                        enabled = missingFuel > 0f && playerCredits >= fuelCost,
                        colors = ButtonDefaults.buttonColors(containerColor = QuantumNeonOrange.copy(alpha = 0.2f), disabledContainerColor = QuantumBorder),
                        border = BorderStroke(1.dp, if (missingFuel > 0f && playerCredits >= fuelCost) QuantumNeonOrange else QuantumBorder),
                        modifier = Modifier.fillMaxWidth().height(32.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Text(
                            text = if (missingFuel <= 0f) "Fuel Full" else "Refuel: ${fuelCost}cr",
                            color = if (missingFuel <= 0f) QuantumGrayText else QuantumNeonOrange,
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    val missingHull = shipState.maxHull - shipState.hull
                    val naniteCost = (missingHull * 0.8f).toInt()
                    Button(
                        onClick = { viewModel.repairShip() },
                        enabled = missingHull > 0f && playerNanites >= naniteCost,
                        colors = ButtonDefaults.buttonColors(containerColor = QuantumNeonGreen.copy(alpha = 0.2f), disabledContainerColor = QuantumBorder),
                        border = BorderStroke(1.dp, if (missingHull > 0f && playerNanites >= naniteCost) QuantumNeonGreen else QuantumBorder),
                        modifier = Modifier.fillMaxWidth().height(32.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Text(
                            text = if (missingHull <= 0f) "Hull Repaired" else "Fix: ${naniteCost}na",
                            color = if (missingHull <= 0f) QuantumGrayText else QuantumNeonGreen,
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        // Test space combat launch trigger
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(QuantumNeonRed.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                    .border(1.dp, QuantumNeonRed.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🚨 DEFENSE TERMINAL ACTIVE",
                        color = QuantumNeonRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "Execute deep-space combat test to claim rare components and salvage credits.",
                        color = QuantumGrayText,
                        fontSize = 9.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )
                    Button(
                        onClick = { viewModel.startSpaceCombat() },
                        colors = ButtonDefaults.buttonColors(containerColor = QuantumNeonRed),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text("SIMULATE ATTACK", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
    }
}

@Composable
fun GalaxyNavigationPanel(
    shipState: StarshipState,
    viewModel: GameViewModel
) {
    val currentSystem = shipState.starSystems.find { it.id == shipState.currentSystemId } ?: return

    val infiniteTransition = rememberInfiniteTransition(label = "RadarSweep")
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "SweepAngle"
    )

    val nodePulse by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "NodePulse"
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "🛰️ CHRONOS NAVIGATION COMPUTER",
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Interstellar Radar Grid
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                .border(1.dp, QuantumBorder, RoundedCornerShape(8.dp))
        ) {
            // Draw coordinate circles and scanning line
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = androidx.compose.ui.geometry.Offset(size.width / 2f, size.height / 2f)
                drawCircle(color = QuantumBorder.copy(alpha = 0.4f), radius = 50f, center = center, style = androidx.compose.ui.graphics.drawscope.Stroke(1f))
                drawCircle(color = QuantumBorder.copy(alpha = 0.3f), radius = 100f, center = center, style = androidx.compose.ui.graphics.drawscope.Stroke(1f))
                drawCircle(color = QuantumBorder.copy(alpha = 0.2f), radius = 150f, center = center, style = androidx.compose.ui.graphics.drawscope.Stroke(1f))
                
                drawLine(color = QuantumBorder.copy(alpha = 0.3f), start = androidx.compose.ui.geometry.Offset(0f, center.y), end = androidx.compose.ui.geometry.Offset(size.width, center.y))
                drawLine(color = QuantumBorder.copy(alpha = 0.3f), start = androidx.compose.ui.geometry.Offset(center.x, 0f), end = androidx.compose.ui.geometry.Offset(center.x, size.height))

                // Sweep Line
                val angleRad = sweepAngle * (Math.PI.toFloat() / 180f)
                val lineEndX = center.x + TrigLUT.cos(angleRad) * 150f
                val lineEndY = center.y + TrigLUT.sin(angleRad) * 150f
                drawLine(
                    color = QuantumNeonPurple.copy(alpha = 0.6f),
                    start = center,
                    end = androidx.compose.ui.geometry.Offset(lineEndX, lineEndY),
                    strokeWidth = 2f
                )

                // Arc Gradient Sweep Overlay
                drawArc(
                    color = QuantumNeonPurple.copy(alpha = 0.08f),
                    startAngle = sweepAngle - 35f,
                    sweepAngle = 35f,
                    useCenter = true,
                    size = androidx.compose.ui.geometry.Size(300f, 300f),
                    topLeft = androidx.compose.ui.geometry.Offset(center.x - 150f, center.y - 150f)
                )
            }

            // Draw system nodes
            shipState.starSystems.forEach { sys ->
                if (sys.isDiscovered) {
                    val screenX = 180f + sys.sectorX * 1.2f
                    val screenY = 90f + sys.sectorY * 0.8f
                    val isCurrent = sys.id == shipState.currentSystemId

                    val sizeMultiplier = if (isCurrent) (1.0f + nodePulse * 0.3f) else 1.0f
                    val baseNodeSize = if (isCurrent) 12.dp else 10.dp
                    val finalNodeSize = baseNodeSize * sizeMultiplier

                    Box(
                        modifier = Modifier
                            .offset(x = (screenX - (finalNodeSize.value / 2f)).dp, y = (screenY - (finalNodeSize.value / 2f)).dp)
                            .size(finalNodeSize)
                            .background(
                                if (isCurrent) QuantumNeonOrange else sys.associatedReality.primaryColor,
                                RoundedCornerShape(10.dp)
                            )
                            .border(
                                2.dp,
                                Color.White.copy(alpha = if (isCurrent) nodePulse else 0.4f),
                                RoundedCornerShape(10.dp)
                            )
                    )
                }
            }

            Text(
                text = "⚡ HYPER-SPACE COORDINATE RADAR (ACTIVE SCAN)",
                color = QuantumNeonPurple.copy(alpha = 0.8f),
                fontSize = 8.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Systems list
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(shipState.starSystems) { sys ->
                val isCurrent = sys.id == shipState.currentSystemId
                
                // Compute distance and fuel required
                val dx = sys.sectorX - currentSystem.sectorX
                val dy = sys.sectorY - currentSystem.sectorY
                val dist = sqrt(dx * dx + dy * dy)
                val chronosUpgrade = shipState.upgrades.find { it.id == "up_chronos" }?.isOwned == true
                val fuelNeeded = (dist * 0.4f * (if (chronosUpgrade) 0.75f else 1.0f)).toInt()
                val canJump = shipState.fuel >= fuelNeeded && sys.isDiscovered

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isCurrent) QuantumNeonPurple.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.3f),
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            if (isCurrent) QuantumNeonPurple else QuantumBorder,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(sys.associatedReality.primaryColor, RoundedCornerShape(4.dp))
                                        .padding(end = 4.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = sys.name + if (isCurrent) " (Current)" else "",
                                    color = if (isCurrent) QuantumNeonPurple else Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Text(
                                text = "Faction: ${sys.factionAffiliation} • Nexus: ${sys.specialNodeName}",
                                color = QuantumGrayText,
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        if (!sys.isDiscovered) {
                            Box(
                                modifier = Modifier
                                    .background(QuantumNeonRed.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                    .border(1.dp, QuantumNeonRed, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 4.dp)
                            ) {
                                Text("LOCKED", color = QuantumNeonRed, fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            }
                        } else if (!isCurrent) {
                            Button(
                                onClick = { viewModel.jumpToSystem(sys.id) },
                                enabled = canJump,
                                colors = ButtonDefaults.buttonColors(containerColor = QuantumNeonPurple, disabledContainerColor = QuantumBorder),
                                modifier = Modifier.height(28.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Text("WARP JUMP [⚡ $fuelNeeded]", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = sys.description,
                        color = QuantumGrayText,
                        fontSize = 9.sp,
                        lineHeight = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CrewRosterPanel(
    shipState: StarshipState,
    viewModel: GameViewModel,
    playerCredits: Int
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "👥 RECRUIT SPECIALIZED SHIP CREW",
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(shipState.crew) { crew ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (crew.isRecruited) QuantumNeonBlue.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.3f),
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            if (crew.isRecruited) QuantumNeonBlue else QuantumBorder,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(crew.avatarEmoji, fontSize = 20.sp, modifier = Modifier.padding(end = 8.dp))
                            Column {
                                Text(
                                    text = crew.name,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "Role: ${crew.role} • Rating: ${"⭐".repeat(crew.rating)}",
                                    color = QuantumNeonBlue,
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }

                        if (crew.isRecruited) {
                            Box(
                                modifier = Modifier
                                    .background(QuantumNeonBlue.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                    .border(1.dp, QuantumNeonBlue, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 4.dp)
                            ) {
                                Text("ACTIVE CREW", color = QuantumNeonBlue, fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            }
                        } else {
                            Button(
                                onClick = { viewModel.recruitCrewMember(crew.id) },
                                enabled = playerCredits >= crew.recruitmentCost,
                                colors = ButtonDefaults.buttonColors(containerColor = QuantumNeonBlue, disabledContainerColor = QuantumBorder),
                                modifier = Modifier.height(28.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Text("RECRUIT: ${crew.recruitmentCost}cr", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Specialty: ${crew.specialty} • ${crew.description}",
                        color = QuantumGrayText,
                        fontSize = 9.sp,
                        lineHeight = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ShipUpgradesPanel(
    shipState: StarshipState,
    viewModel: GameViewModel,
    playerCredits: Int,
    playerNanites: Int
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "🛠️ CHRONOS SHIP UPGRADES MARKET",
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(shipState.upgrades) { up ->
                val canAfford = playerCredits >= up.costCredits && playerNanites >= up.costNanites

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (up.isOwned) QuantumNeonGreen.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.3f),
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            if (up.isOwned) QuantumNeonGreen else QuantumBorder,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = up.name,
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = "System Affected: ${up.affectedSystem.displayName}",
                                color = QuantumNeonGreen,
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        if (up.isOwned) {
                            Box(
                                modifier = Modifier
                                    .background(QuantumNeonGreen.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                    .border(1.dp, QuantumNeonGreen, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 4.dp)
                            ) {
                                Text("INSTALLED", color = QuantumNeonGreen, fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            }
                        } else {
                            Button(
                                onClick = { viewModel.buyShipUpgrade(up.id) },
                                enabled = canAfford,
                                colors = ButtonDefaults.buttonColors(containerColor = QuantumNeonGreen, disabledContainerColor = QuantumBorder),
                                modifier = Modifier.height(28.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = "INSTALL [💰 ${up.costCredits}cr / 💎 ${up.costNanites}na]",
                                    color = Color.White,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = up.description,
                        color = QuantumGrayText,
                        fontSize = 9.sp,
                        lineHeight = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TimelineMatrixPanel(
    shipState: StarshipState,
    viewModel: GameViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "🧬 THE PARALLEL TIMELINE MATRIX",
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Status of Quantum Babies & Reality Stability
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .border(1.dp, QuantumBorder, RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Text(
                text = "Quantum Baby Discovery Progress",
                color = QuantumNeonRed,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Discovered: ${shipState.quantumBabiesDiscovered} / 3 Key Nodes",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
                Button(
                    onClick = { viewModel.discoverQuantumBaby() },
                    enabled = shipState.quantumBabiesDiscovered < 3,
                    colors = ButtonDefaults.buttonColors(containerColor = QuantumNeonRed),
                    modifier = Modifier.height(24.dp),
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("FIND BABY NODE", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (i in 1..3) {
                    val active = i <= shipState.quantumBabiesDiscovered
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(10.dp)
                            .background(
                                if (active) QuantumNeonRed else Color(0xFF231012),
                                RoundedCornerShape(5.dp)
                            )
                            .border(1.dp, if (active) QuantumNeonRed else QuantumBorder, RoundedCornerShape(5.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Faction Balance Slider
            Text(
                text = "Multi-Universe Faction Balance",
                color = QuantumNeonPurple,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = "Your actions sway the multi-universe between the cybernetic technocrats and the organic enlighteners.",
                color = QuantumGrayText,
                fontSize = 8.sp,
                lineHeight = 10.sp,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("⚙️ TECHNOPUNKS", color = QuantumNeonOrange, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                Text("LIGHTENERS 🌿", color = QuantumNeonGreen, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Slider(
                value = shipState.universeAlignment.toFloat(),
                onValueChange = { viewModel.adjustUniverseAlignment((it - shipState.universeAlignment).toInt()) },
                valueRange = 0f..100f,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = QuantumNeonPurple,
                    activeTrackColor = QuantumNeonGreen,
                    inactiveTrackColor = QuantumNeonOrange
                )
            )
        }

        // Endgame resolution choices
        Text(
            text = "🎬 UNIVERSE ENDGAME PATHWAYS",
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )

        val meetsCriteria = shipState.quantumBabiesDiscovered >= 3
        if (!meetsCriteria) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .border(1.dp, QuantumBorder, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🔒 ENDGAME LOCKED\nCollect 3 Quantum Babies across space to unlock parallel universe conclusions.",
                    color = QuantumGrayText,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val ending = shipState.activeEndingChoice

            if (ending != null) {
                // Display selected ending summary
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(QuantumNeonPurple.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .border(1.dp, QuantumNeonPurple, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = when (ending) {
                            "MERGE_TIMELINES" -> "🌌 TIMELINE HARMONIZATION RESOLVED"
                            "DESTROY_SHREDDER" -> "🔥 COGNITIVE APPARATUS CRUSHED"
                            "ENSLAVE_SINGULARIUM" -> "⚡ ASCENSION TO MULTI-REALITY DEITY"
                            else -> "CONCL_UNKNOWN"
                        },
                        color = QuantumNeonPurple,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (ending) {
                            "MERGE_TIMELINES" -> "By harmonizing the quantum alignment coordinates using the babies, you successfully dissolved the dimensional boundaries, unifying First Earth and Nova Tellus. Humanity merges into a peaceful, super-conscious cosmic race free from the industrial rot of Aurelian Corp."
                            "DESTROY_SHREDDER" -> "With direct nuclear warp-grid commands, you eradicated Aurelian's universe-mining apparatus. The alternate worlds are saved from destruction, but Solis Prime loses its power source, plunging First Earth into a clean but challenging neon dark age."
                            "ENSLAVE_SINGULARIUM" -> "You hooked yourself into the Singularium Engine, utilizing the infants as batteries to transcend the physical realm. You are now the omnipotent ruler of the Quantum multiverse, commanding all factions and realities as a technological deity."
                            else -> ""
                        },
                        color = Color.White,
                        fontSize = 10.sp,
                        lineHeight = 13.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { viewModel.resolveGameEnding(null) },
                        colors = ButtonDefaults.buttonColors(containerColor = QuantumBorder),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text("CHOOSE AN ALTERNATE TIMELINE PATHWAY", color = Color.White, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                    }
                }
            } else {
                // List of choices
                listOf(
                    Triple(
                        "MERGE_TIMELINES",
                        "🌌 Harmonize the Parallel Timelines",
                        "Unify all alternate Earths using the infants' alignment core. Peaceful, bio-organic ascension."
                    ),
                    Triple(
                        "DESTROY_SHREDDER",
                        "🔥 Eradicate the Reality-Siphon Apparatus",
                        "Save parallel dimensions by destroying Aurelian Corp's drill. Plunges Solis Prime into clean dark age."
                    ),
                    Triple(
                        "ENSLAVE_SINGULARIUM",
                        "⚡ Ascend as Multi-Reality Overlord",
                        "Siphon the babies' power directly to your brain, achieving absolute cybernetic godhood."
                    )
                ).forEach { (id, title, desc) ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .border(1.dp, QuantumBorder, RoundedCornerShape(8.dp))
                            .clickable { viewModel.resolveGameEnding(id) }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = title,
                            color = QuantumNeonOrange,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = desc,
                            color = QuantumGrayText,
                            fontSize = 9.sp,
                            lineHeight = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SpaceCombatOverlay(
    combatState: SpaceCombatState,
    shipState: StarshipState,
    viewModel: GameViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f))
            .clickable(enabled = false) {} // block click through
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F0608), RoundedCornerShape(12.dp))
                .border(2.dp, QuantumNeonRed, RoundedCornerShape(12.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // RED ALERT HEADER
            Text(
                text = "⚠️ RED ALERT: DEEP SPACE COMBAT",
                color = QuantumNeonRed,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            
            Spacer(modifier = Modifier.height(10.dp))

            // Enemy info card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .border(1.dp, QuantumBorder, RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🛰️ ${combatState.enemyName}",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "DANGER LEVEL: HIGH",
                        color = QuantumNeonOrange,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Enemy stats
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("ENEMY SHIELD", color = QuantumNeonBlue, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                            Text("${combatState.enemyShield.toInt()}/${combatState.enemyMaxShield.toInt()}", color = QuantumNeonBlue, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                        }
                        LinearProgressIndicator(
                            progress = { combatState.enemyShield / combatState.enemyMaxShield },
                            modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                            color = QuantumNeonBlue,
                            trackColor = Color(0xFF111E2E)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("ENEMY HULL HP", color = QuantumNeonRed, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                            Text("${combatState.enemyHull.toInt()}/${combatState.enemyMaxHull.toInt()}", color = QuantumNeonRed, fontSize = 8.sp, fontFamily = FontFamily.Monospace)
                        }
                        LinearProgressIndicator(
                            progress = { combatState.enemyHull / combatState.enemyMaxHull },
                            modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                            color = QuantumNeonRed,
                            trackColor = Color(0xFF261214)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // COMBAT LOGS CONSOLE
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.Black, RoundedCornerShape(6.dp))
                    .border(1.dp, QuantumBorder, RoundedCornerShape(6.dp))
                    .padding(8.dp)
            ) {
                Text(
                    text = "CONSOLE OUTPUT:",
                    color = QuantumNeonGreen,
                    fontSize = 8.sp,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    reverseLayout = true
                ) {
                    items(combatState.combatLogs.reversed()) { log ->
                        Text(
                            text = log,
                            color = if (log.contains("💥") || log.contains("🏆")) QuantumNeonGreen else if (log.contains("⚠️") || log.contains("💀")) QuantumNeonRed else Color.White,
                            fontSize = 8.sp,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action options or reward collection
            if (combatState.battleOver) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (combatState.playerWon) "🏆 COMBAT DE-ESCALATED: SYSTEM SUCCESS" else "💀 SHIP DEFLECTED: SYSTEM CRITICAL",
                        color = if (combatState.playerWon) QuantumNeonGreen else QuantumNeonRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { viewModel.collectCombatRewards() },
                        colors = ButtonDefaults.buttonColors(containerColor = if (combatState.playerWon) QuantumNeonGreen else QuantumNeonRed),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(
                            text = if (combatState.playerWon) "SALVAGE SHIP & RE-ENGAGE BRIDGE" else "ENGAGE ESCAPE AUTOPILOT",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.executeSpaceAction("FIRE") },
                        enabled = combatState.playerTurn,
                        colors = ButtonDefaults.buttonColors(containerColor = QuantumNeonRed),
                        modifier = Modifier.weight(1f).height(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("FIRE CANNONS ⚔️", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }

                    Button(
                        onClick = { viewModel.executeSpaceAction("RECHARGE") },
                        enabled = combatState.playerTurn,
                        colors = ButtonDefaults.buttonColors(containerColor = QuantumNeonBlue),
                        modifier = Modifier.weight(1.5f).height(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("RECHARGE SHIELD 🛡️", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }

                    Button(
                        onClick = { viewModel.executeSpaceAction("FLEE") },
                        enabled = combatState.playerTurn,
                        colors = ButtonDefaults.buttonColors(containerColor = QuantumBorder),
                        modifier = Modifier.weight(1f).height(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("EVADE JUMP 🚀", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
    }
}
