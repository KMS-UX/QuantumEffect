package com.example.game.assets

import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Guards against a failure mode this project has already hit once: binary assets
 * being run through a lossy text decode, which replaces every byte >= 0x80 with
 * U+FFFD (`EF BF BD`). The file keeps its name and size, `aapt` copies it into
 * the APK without complaint, and the app only dies at runtime when something
 * tries to decode it.
 *
 * The corrupted `img_combat_effects.jpg` crashed the isometric explore screen —
 * the only place that references it — while the rest of the app ran fine.
 */
class BinaryAssetIntegrityTest {

    /** Leading bytes that identify each format we ship. */
    private val signatures: Map<String, List<ByteArray>> = mapOf(
        "jpg" to listOf(byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte())),
        "jpeg" to listOf(byteArrayOf(0xFF.toByte(), 0xD8.toByte(), 0xFF.toByte())),
        "png" to listOf(byteArrayOf(0x89.toByte(), 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A)),
        "webp" to listOf("RIFF".toByteArray(Charsets.US_ASCII)),
        "gif" to listOf("GIF8".toByteArray(Charsets.US_ASCII)),
        "wav" to listOf("RIFF".toByteArray(Charsets.US_ASCII)),
        "ogg" to listOf("OggS".toByteArray(Charsets.US_ASCII)),
        "ttf" to listOf(byteArrayOf(0x00, 0x01, 0x00, 0x00), "true".toByteArray(Charsets.US_ASCII)),
        "otf" to listOf("OTTO".toByteArray(Charsets.US_ASCII))
    )

    private val replacementChar = byteArrayOf(0xEF.toByte(), 0xBF.toByte(), 0xBD.toByte())

    /** Unit tests run with the module directory as the working directory. */
    private val resDir = File("src/main/res")

    @Test
    fun `res directory is where the test expects it`() {
        assertTrue(
            resDir.isDirectory,
            "expected ${resDir.absolutePath} to exist; this test cannot validate anything otherwise"
        )
    }

    @Test
    fun `every binary resource starts with its format signature`() {
        val failures = mutableListOf<String>()

        for (file in resDir.walkTopDown().filter { it.isFile }) {
            val expected = signatures[file.extension.lowercase()] ?: continue
            val header = file.readBytes().take(16).toByteArray()
            val matches = expected.any { sig ->
                header.size >= sig.size && header.copyOfRange(0, sig.size).contentEquals(sig)
            }
            if (!matches) {
                failures += "${file.path}: header ${header.take(8).joinToString(" ") { "%02x".format(it) }}"
            }
        }

        if (failures.isNotEmpty()) {
            fail("Binary assets with an invalid signature:\n" + failures.joinToString("\n"))
        }
    }

    @Test
    fun `no binary resource contains UTF-8 replacement characters`() {
        val failures = mutableListOf<String>()

        for (file in resDir.walkTopDown().filter { it.isFile }) {
            if (file.extension.lowercase() !in signatures) continue
            val bytes = file.readBytes()
            val count = bytes.countOccurrences(replacementChar)
            // A handful can occur naturally in compressed data; a mangled file has thousands.
            if (count > MOJIBAKE_THRESHOLD) {
                failures += "${file.path}: $count occurrences of U+FFFD — decoded as text at some point"
            }
        }

        if (failures.isNotEmpty()) {
            fail("Binary assets corrupted by a lossy text round-trip:\n" + failures.joinToString("\n"))
        }
    }

    private fun ByteArray.countOccurrences(needle: ByteArray): Int {
        if (needle.isEmpty() || size < needle.size) return 0
        var count = 0
        var i = 0
        while (i <= size - needle.size) {
            var j = 0
            while (j < needle.size && this[i + j] == needle[j]) j++
            if (j == needle.size) {
                count++
                i += needle.size
            } else {
                i++
            }
        }
        return count
    }

    private companion object {
        const val MOJIBAKE_THRESHOLD = 32
    }
}
