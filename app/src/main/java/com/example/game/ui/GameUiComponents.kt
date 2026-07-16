package com.example.game.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.models.*
import com.example.ui.theme.*
import kotlin.random.Random

// --- Cyberpunk Neon Cards & Buttons ---

@Composable
fun CyberCard(
    modifier: Modifier = Modifier,
    borderColor: Color = QuantumNeonPurple,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(QuantumCardBg)
            .border(1.dp, borderColor.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .drawBehind {
                // Symmetrical decorative tech corners
                val length = 12.dp.toPx()
                val thickness = 2.dp.toPx()
                // Top-left
                drawRect(borderColor, Offset(0f, 0f), Size(length, thickness))
                drawRect(borderColor, Offset(0f, 0f), Size(thickness, length))
                // Bottom-right
                drawRect(borderColor, Offset(size.width - length, size.height - thickness), Size(length, thickness))
                drawRect(borderColor, Offset(size.width - thickness, size.height - length), Size(thickness, length))
            }
            .padding(14.dp)
    ) {
        Column {
            content()
        }
    }
}

@Composable
fun CyberButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    color: Color = QuantumNeonPurple,
    contentDescription: String? = null
) {
    val alpha = if (enabled) 1.0f else 0.4f
    Box(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(if (enabled) color.copy(alpha = 0.15f) else Color.DarkGray.copy(alpha = 0.1f))
            .border(1.dp, if (enabled) color else Color.Gray, RoundedCornerShape(4.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .drawBehind {
                if (enabled) {
                    // Left vertical neon bar
                    drawRect(color, Offset(0f, 0f), Size(3.dp.toPx(), size.height))
                }
            }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.uppercase(),
            color = if (enabled) QuantumLightText else Color.Gray,
            fontSize = 13.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CyberProgressBar(
    progress: Float, // 0.0f to 1.0f
    modifier: Modifier = Modifier,
    label: String,
    valueText: String,
    color: Color = QuantumNeonPurple
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0.0f, 1.0f),
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "Progress"
    )

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label.uppercase(),
                color = QuantumGrayText,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = valueText,
                color = color,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color(0xFF0F141F))
                .border(1.dp, QuantumBorder, RoundedCornerShape(3.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .background(
                        Brush.horizontalGradient(
                            listOf(color.copy(alpha = 0.5f), color)
                        )
                    )
            )
        }
    }
}

// --- Dynamic Pixel-Style "Quantum Baby" Renderer ---

@Composable
fun PixelQuantumBabyRenderer(
    outfit: Outfit,
    modifier: Modifier = Modifier,
    animationActive: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "BabyResonance")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Scale"
    )

    val energyRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Rotation"
    )

    Canvas(
        modifier = modifier
            .size(160.dp)
            .border(1.dp, QuantumBorder, RoundedCornerShape(12.dp))
            .background(QuantumDarkBg)
    ) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val baseRadius = size.width * 0.22f * pulseScale

        // Colors based on equipped outfit
        val suiteColor = when (outfit) {
            Outfit.DEFAULT -> QuantumNeonPurple
            Outfit.EXPLORER -> QuantumNeonOrange
            Outfit.STEALTH -> Color(0xFF2E0854)
            Outfit.ACADEMIC -> QuantumNeonBlue
            Outfit.VOID_TOUCH -> Color(0xFF0F0022)
        }

        val accentColor = when (outfit) {
            Outfit.DEFAULT -> QuantumNeonBlue
            Outfit.EXPLORER -> Color.Yellow
            Outfit.STEALTH -> QuantumNeonPurple
            Outfit.ACADEMIC -> QuantumNeonGreen
            Outfit.VOID_TOUCH -> QuantumNeonPurple
        }

        // Draw Ambient Quantum Orbit Rings
        drawCircle(
            color = accentColor.copy(alpha = 0.15f),
            radius = baseRadius * 1.8f,
            center = Offset(centerX, centerY),
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), energyRotation)
            )
        )

        drawCircle(
            color = suiteColor.copy(alpha = 0.25f),
            radius = baseRadius * 1.4f,
            center = Offset(centerX, centerY),
            style = Stroke(
                width = 1.5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), -energyRotation)
            )
        )

        // Draw Pixelated Body Structure (HD-2D)
        val pixelSize = 6.dp.toPx()

        // Helper to draw simulated "pixel block"
        fun drawPixelBlock(color: Color, rx: Int, ry: Int, widthBlocks: Int = 1, heightBlocks: Int = 1) {
            val ox = centerX + (rx * pixelSize)
            val oy = centerY + (ry * pixelSize)
            drawRect(
                color = color,
                topLeft = Offset(ox, oy),
                size = Size(pixelSize * widthBlocks, pixelSize * heightBlocks)
            )
        }

        // 1. Draw Legs
        drawPixelBlock(suiteColor, -3, 4, 2, 2)
        drawPixelBlock(suiteColor, 1, 4, 2, 2)
        drawPixelBlock(Color.Black, -3, 6, 2, 1) // feet outline
        drawPixelBlock(Color.Black, 1, 6, 2, 1)

        // 2. Draw Body / Torso
        drawPixelBlock(suiteColor, -4, -1, 8, 5)
        // Cybernetic heart reactor core glowing in center
        drawPixelBlock(accentColor, -1, 1, 2, 2)

        // 3. Draw Arms
        drawPixelBlock(suiteColor, -6, 0, 2, 3)
        drawPixelBlock(suiteColor, 4, 0, 2, 3)

        // 4. Head / Face (glowing skin)
        drawPixelBlock(Color(0xFFFFD1A9), -3, -6, 6, 5) // skin block

        // Headwear / Cowl depending on Outfit
        when (outfit) {
            Outfit.DEFAULT -> {
                // Simple cyber headset
                drawPixelBlock(suiteColor, -4, -7, 8, 1)
                drawPixelBlock(suiteColor, -4, -6, 1, 4)
                drawPixelBlock(suiteColor, 3, -6, 1, 4)
            }
            Outfit.EXPLORER -> {
                // Yellow Explorer Goggles
                drawPixelBlock(Color.Yellow, -3, -4, 6, 2)
                drawPixelBlock(Color.DarkGray, -4, -4, 1, 2)
                drawPixelBlock(Color.DarkGray, 3, -4, 1, 2)
            }
            Outfit.STEALTH -> {
                // Stealth hood covering face
                drawPixelBlock(suiteColor, -4, -7, 8, 2)
                drawPixelBlock(suiteColor, -4, -5, 2, 4)
                drawPixelBlock(suiteColor, 2, -5, 2, 4)
            }
            Outfit.ACADEMIC -> {
                // Archivist cowl / cap
                drawPixelBlock(suiteColor, -4, -8, 8, 2)
                drawPixelBlock(accentColor, -2, -9, 4, 1)
            }
            Outfit.VOID_TOUCH -> {
                // Sparkling dark matter void aura
                drawPixelBlock(suiteColor, -4, -7, 8, 1)
                for (i in 0..4) {
                    val sparkX = Random.nextInt(-6, 6)
                    val sparkY = Random.nextInt(-9, 7)
                    drawPixelBlock(QuantumNeonPurple.copy(alpha = 0.7f), sparkX, sparkY)
                }
            }
        }

        // Draw Glowing Cybernetic Eyes
        drawPixelBlock(accentColor, -2, -3, 1, 1)
        drawPixelBlock(accentColor, 1, -3, 1, 1)

        // Happy small pixel mouth
        drawPixelBlock(Color(0xFFE25B71), -1, -1, 2, 1)
    }
}

// --- Soundscape / Atmospheric helper tips ---

@Composable
fun SoundscapeThemeTip(
    currentLocation: String,
    biome: Biome
) {
    val soundTheme = when (biome) {
        Biome.GRASSLANDS -> "Solis City Hum: Distant automated air traffic, rain splatters, neon humming."
        Biome.SNOW -> "Frostveil Desolation: Cold howling wind sweeps, metallic ice creaking."
        Biome.DESERT -> "Emberfall Fissure: Low volcanic rumbling, crackling magma, radiation statics."
        Biome.VOLCANIC -> "Ironward Roar: Heavy machinery steam, clanging assembly gears, alarms."
        Biome.RUINS, Biome.VOID_SEA -> "Void Silence: Static electric crackles, whispering parallel feedback echoes."
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = QuantumDarkBg.copy(alpha = 0.5f)),
        border = BorderStroke(1.dp, QuantumBorder)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🎧",
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Column {
                Text(
                    text = "ACTIVE ATMOSPHERE: $currentLocation".uppercase(),
                    color = QuantumGrayText,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = soundTheme,
                    color = QuantumLightText,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }
    }
}
