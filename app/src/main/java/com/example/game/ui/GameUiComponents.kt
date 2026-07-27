package com.example.game.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game.models.Biome
import com.example.game.models.Outfit
import com.example.ui.theme.*

@Composable
fun CyberCard(
    modifier: Modifier = Modifier,
    borderColor: Color = QuantumBorder,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .drawBehind {
                val length = 12.dp.toPx()
                val thickness = 2.dp.toPx()
                // Top-left horizontal
                drawRect(
                    color = borderColor,
                    topLeft = Offset(0f, 0f),
                    size = Size(length, thickness)
                )
                // Top-left vertical
                drawRect(
                    color = borderColor,
                    topLeft = Offset(0f, 0f),
                    size = Size(thickness, length)
                )
                // Bottom-right horizontal
                drawRect(
                    color = borderColor,
                    topLeft = Offset(size.width - length, size.height - thickness),
                    size = Size(length, thickness)
                )
                // Bottom-right vertical
                drawRect(
                    color = borderColor,
                    topLeft = Offset(size.width - thickness, size.height - length),
                    size = Size(thickness, length)
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = QuantumCardBg
        ),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, borderColor.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
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
    Box(
        modifier = modifier
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(if (enabled) QuantumCardBg else QuantumCardBg.copy(alpha = 0.5f))
            .border(1.dp, if (enabled) color.copy(alpha = 0.8f) else QuantumBorder)
            .clickable(enabled = enabled, onClick = onClick)
            .drawBehind {
                if (enabled) {
                    drawRect(
                        color = color,
                        topLeft = Offset(0f, 0f),
                        size = Size(3.dp.toPx(), size.height)
                    )
                }
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (enabled) QuantumLightText else QuantumGrayText,
            fontSize = 14.sp,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun CyberProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    label: String,
    valueText: String,
    color: Color = QuantumNeonGreen
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                color = QuantumLightText,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = valueText,
                color = color,
                style = MaterialTheme.typography.labelMedium
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(QuantumDarkBg)
                .border(1.dp, QuantumBorder, RoundedCornerShape(2.dp))
        ) {
            val progressClamped = progress.coerceIn(0f, 1f)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progressClamped)
                    .background(color)
            )
        }
    }
}

@Composable
fun PixelQuantumBabyRenderer(
    outfit: Outfit,
    modifier: Modifier = Modifier,
    animationActive: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by if (animationActive) {
        infiniteTransition.animateFloat(
            initialValue = 0.9f,
            targetValue = 1.1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1200, easing = LinearOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulseScale"
        )
    } else {
        remember { mutableStateOf(1.0f) }
    }

    val energyRotation by if (animationActive) {
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(4000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "energyRotation"
        )
    } else {
        remember { mutableStateOf(0f) }
    }

    val suiteColor = when (outfit) {
        Outfit.DEFAULT -> QuantumNeonPurple
        Outfit.EXPLORER -> QuantumNeonOrange
        Outfit.STEALTH -> Color(0xFF2E2E38)
        Outfit.ACADEMIC -> QuantumNeonBlue
        Outfit.VOID_TOUCH -> Color(0xFF1E1B4B)
    }

    val accentColor = when (outfit) {
        Outfit.DEFAULT -> QuantumNeonBlue
        Outfit.EXPLORER -> Color(0xFFFBBF24) // Yellow
        Outfit.STEALTH -> QuantumNeonPurple
        Outfit.ACADEMIC -> QuantumNeonGreen
        Outfit.VOID_TOUCH -> QuantumNeonPurple
    }

    Canvas(modifier = modifier.fillMaxSize().aspectRatio(1f)) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val baseRadius = size.width * 0.22f * pulseScale

        // Outer rotating ring
        drawCircle(
            color = accentColor.copy(alpha = 0.15f),
            radius = 1.8f * baseRadius,
            center = Offset(centerX, centerY),
            style = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), energyRotation * 2f)
            )
        )

        // Inner rotating ring
        drawCircle(
            color = suiteColor.copy(alpha = 0.25f),
            radius = 1.4f * baseRadius,
            center = Offset(centerX, centerY),
            style = Stroke(
                width = 1.5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), -energyRotation * 1.5f)
            )
        )

        val pixelSize = 6.dp.toPx()

        fun drawPixelBlock(color: Color, rx: Int, ry: Int, widthBlocks: Int, heightBlocks: Int) {
            val ox = centerX + (rx * pixelSize)
            val oy = centerY + (ry * pixelSize)
            drawRect(
                color = color,
                topLeft = Offset(ox, oy),
                size = Size(widthBlocks * pixelSize, heightBlocks * pixelSize)
            )
        }

        // Draw character body / head
        drawPixelBlock(suiteColor, -3, 4, 2, 2)
        drawPixelBlock(suiteColor, 1, 4, 2, 2)
        drawPixelBlock(Color.Black, -3, 6, 2, 1)
        drawPixelBlock(Color.Black, 1, 6, 2, 1)

        // Main Torso / Coat
        drawPixelBlock(suiteColor, -4, -1, 8, 5)
        drawPixelBlock(accentColor, -1, 1, 2, 2)
        drawPixelBlock(suiteColor, -6, 0, 2, 3)
        drawPixelBlock(suiteColor, 4, 0, 2, 3)

        // Face Visor / Helmet
        drawPixelBlock(Color(0xFFE2E8F0), -3, -6, 6, 5)

        // Hair / Headgear customized by outfit
        when (outfit) {
            Outfit.DEFAULT -> {
                drawPixelBlock(suiteColor, -4, -7, 8, 1)
                drawPixelBlock(suiteColor, -4, -6, 1, 4)
                drawPixelBlock(suiteColor, 3, -6, 1, 4)
            }
            Outfit.EXPLORER -> {
                drawPixelBlock(Color(0xFFFBBF24), -3, -4, 6, 2)
                drawPixelBlock(Color(0xFF4B5563), -4, -4, 1, 2)
                drawPixelBlock(Color(0xFF4B5563), 3, -4, 1, 2)
            }
            Outfit.STEALTH -> {
                drawPixelBlock(suiteColor, -4, -7, 8, 2)
                drawPixelBlock(suiteColor, -4, -5, 2, 4)
                drawPixelBlock(suiteColor, 2, -5, 2, 4)
            }
            Outfit.ACADEMIC -> {
                drawPixelBlock(suiteColor, -4, -8, 8, 2)
                drawPixelBlock(accentColor, -2, -9, 4, 1)
            }
            Outfit.VOID_TOUCH -> {
                drawPixelBlock(suiteColor, -4, -7, 8, 1)
                for (i in 0 until 5) {
                    val sparkX = (i * 3 - 6).coerceIn(-6, 6)
                    val sparkY = (i * 2 - 9).coerceIn(-9, 7)
                    drawPixelBlock(QuantumNeonPurple.copy(alpha = 0.7f), sparkX, sparkY, 1, 1)
                }
            }
        }

        // Helmet glowing eyes / visor highlights
        drawPixelBlock(accentColor, -2, -3, 1, 1)
        drawPixelBlock(accentColor, 1, -3, 1, 1)
        drawPixelBlock(Color(0xFF67E8F9), -1, -1, 2, 1)
    }
}

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
        colors = CardDefaults.cardColors(
            containerColor = QuantumCardBg.copy(alpha = 0.5f)
        ),
        border = BorderStroke(1.dp, QuantumBorder),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "🎵 AUDIO EMISSION: $currentLocation",
                color = QuantumNeonPurple,
                style = MaterialTheme.typography.labelMedium,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = soundTheme,
                color = QuantumGrayText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
