package com.example.game.engine

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.hypot
import kotlin.math.sin

/**
 * Isometric projection and 8-way movement, per section 3A of the Summer Engine
 * blueprint. Pure math with no Android or Compose dependencies so it can be unit
 * tested on the JVM.
 *
 * Screen.X = (World.X - World.Y) * cos(30°)
 * Screen.Y = (World.X + World.Y) * sin(30°)
 */
object IsometricMath {

    const val PROJECTION_ANGLE_DEGREES: Float = 30f

    private const val DEG_TO_RAD = (Math.PI / 180.0).toFloat()

    val cosProjection: Float = cos(PROJECTION_ANGLE_DEGREES * DEG_TO_RAD)
    val sinProjection: Float = sin(PROJECTION_ANGLE_DEGREES * DEG_TO_RAD)

    /** Projects a world-space point onto the isometric screen plane. */
    fun worldToScreen(worldX: Float, worldY: Float): Vec2 =
        Vec2(
            x = (worldX - worldY) * cosProjection,
            y = (worldX + worldY) * sinProjection
        )

    /**
     * Inverse of [worldToScreen]. Used to turn a tap on the isometric floor back
     * into a tile coordinate.
     */
    fun screenToWorld(screenX: Float, screenY: Float): Vec2 {
        val a = screenX / cosProjection
        val b = screenY / sinProjection
        return Vec2(x = (b + a) / 2f, y = (b - a) / 2f)
    }

    /**
     * Snaps an arbitrary input vector (WASD, arrows or a virtual joystick) to one
     * of the eight isometric facings. Returns `null` for input inside [deadZone],
     * which is what lets a released stick fall through to [applyFriction] instead
     * of locking the sprite to a stale facing.
     */
    fun snapToOctant(inputX: Float, inputY: Float, deadZone: Float = 0.15f): IsoDirection? {
        if (hypot(inputX, inputY) < deadZone) return null
        // Rotate raw input into iso space, then bucket into 45° sectors.
        var degrees = Math.toDegrees(kotlin.math.atan2(inputY.toDouble(), inputX.toDouble())).toFloat()
        if (degrees < 0f) degrees += 360f
        val octant = (((degrees + 22.5f) % 360f) / 45f).toInt()
        return IsoDirection.entries[octant]
    }

    /**
     * Exponential velocity decay applied when the player releases input, so a
     * sprite coasts to a stop instead of snapping to zero on a glass touchscreen.
     *
     * @param dampening per-second decay constant; larger stops faster.
     */
    fun applyFriction(velocity: Vec2, deltaSeconds: Float, dampening: Float = 8f): Vec2 {
        if (deltaSeconds <= 0f) return velocity
        val factor = exp(-dampening * deltaSeconds)
        val next = Vec2(velocity.x * factor, velocity.y * factor)
        // Below this the sprite is visually stationary; zero it so idle animations latch.
        return if (abs(next.x) < 0.01f && abs(next.y) < 0.01f) Vec2.ZERO else next
    }
}

data class Vec2(val x: Float, val y: Float) {
    companion object {
        val ZERO = Vec2(0f, 0f)
    }
}

/**
 * The eight sprite facings, ordered counter-clockwise from screen-right so that
 * the ordinal doubles as the 45° octant index used by [IsometricMath.snapToOctant].
 */
enum class IsoDirection(val label: String) {
    RIGHT("Right"),
    DOWN_RIGHT("Down-Right"),
    DOWN("Down"),
    DOWN_LEFT("Down-Left"),
    LEFT("Left"),
    UP_LEFT("Up-Left"),
    UP("Up"),
    UP_RIGHT("Up-Right")
}
