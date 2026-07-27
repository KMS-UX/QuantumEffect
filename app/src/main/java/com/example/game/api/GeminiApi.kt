package com.example.game.api

import com.example.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

// ---------------------------------------------------------------------------
// Request / response DTOs for generativelanguage.googleapis.com
// ---------------------------------------------------------------------------

enum class ThinkingLevel { LOW, MEDIUM, HIGH }

@JsonClass(generateAdapter = true)
data class ThinkingConfig(
    val thinkingLevel: String
)

@JsonClass(generateAdapter = true)
data class GenerationConfig(
    val temperature: Float? = null,
    val topP: Float? = null,
    val topK: Int? = null,
    val thinkingConfig: ThinkingConfig? = null
)

@JsonClass(generateAdapter = true)
data class Part(
    val text: String? = null
)

@JsonClass(generateAdapter = true)
data class Content(
    val parts: List<Part>
)

/** Declares the built-in Google Search grounding tool. An empty object enables it. */
@JsonClass(generateAdapter = true)
data class Tool(
    val googleSearch: Map<String, Any> = emptyMap()
)

@JsonClass(generateAdapter = true)
data class GenerateContentRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig? = null,
    val tools: List<Tool>? = null,
    val systemInstruction: Content? = null
)

@JsonClass(generateAdapter = true)
data class WebSource(
    val uri: String,
    val title: String
)

@JsonClass(generateAdapter = true)
data class GroundingChunk(
    val web: WebSource? = null
)

@JsonClass(generateAdapter = true)
data class GroundingMetadata(
    val webSearchQueries: List<String>? = null,
    val groundingChunks: List<GroundingChunk>? = null
)

@JsonClass(generateAdapter = true)
data class Candidate(
    val content: Content,
    val finishReason: String? = null,
    @Json(name = "groundingMetadata") val groundMetadata: GroundingMetadata? = null
)

@JsonClass(generateAdapter = true)
data class PromptFeedback(
    val blockReason: String? = null
)

@JsonClass(generateAdapter = true)
data class GenerateContentResponse(
    val candidates: List<Candidate>?,
    val promptFeedback: PromptFeedback? = null
)

// ---------------------------------------------------------------------------
// Transport
// ---------------------------------------------------------------------------

interface GeminiApiService {
    @POST("v1beta/models/{model}:generateContent")
    suspend fun generateContent(
        @Path("model") model: String,
        @Query("key") key: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    val service: GeminiApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GeminiApiService::class.java)
    }
}

// ---------------------------------------------------------------------------
// High level helpers used by the ViewModel
// ---------------------------------------------------------------------------

object GeminiServiceHelper {
    /** Deep-reasoning model used by the Quantum Synergy analyser. */
    private const val THINKING_MODEL = "gemini-3.1-pro-preview"

    /** Fast model used for search-grounded Solis Infoband lookups. */
    private const val SEARCH_MODEL = "gemini-3.5-flash"

    private val apiKey: String get() = BuildConfig.GEMINI_API_KEY

    private fun GenerateContentResponse.firstText(): String? =
        candidates
            ?.firstOrNull()
            ?.content
            ?.parts
            ?.mapNotNull { it.text }
            ?.joinToString("")
            ?.takeIf { it.isNotBlank() }

    private fun missingKeyMessage() =
        "SIGNAL LOST: no GEMINI_API_KEY configured. Add one to .env (see .env.example) and rebuild."

    /**
     * Runs [prompt] through the high-thinking model. Returns the generated text, or a
     * player-facing diagnostic string when the call fails — callers render the result
     * straight into the terminal, so this never throws.
     */
    suspend fun generateHighThinkingContent(
        prompt: String,
        systemInstruction: String? = null
    ): String {
        if (apiKey.isBlank()) return missingKeyMessage()
        return try {
            val response = RetrofitClient.service.generateContent(
                model = THINKING_MODEL,
                key = apiKey,
                request = GenerateContentRequest(
                    contents = listOf(Content(parts = listOf(Part(text = prompt)))),
                    generationConfig = GenerationConfig(
                        temperature = 0.8f,
                        thinkingConfig = ThinkingConfig(thinkingLevel = ThinkingLevel.HIGH.name)
                    ),
                    systemInstruction = systemInstruction?.let {
                        Content(parts = listOf(Part(text = it)))
                    }
                )
            )
            response.firstText()
                ?: response.promptFeedback?.blockReason?.let { "TRANSMISSION BLOCKED: $it" }
                ?: "NO RESPONSE: the reasoning core returned an empty candidate set."
        } catch (e: Exception) {
            "CONNECTION FAULT: ${e.message ?: e::class.java.simpleName}"
        }
    }

    /**
     * Runs [prompt] through the search-grounded model. Returns the generated text paired
     * with the web sources the model cited (empty when grounding produced none).
     */
    suspend fun generateSearchGroundedContent(
        prompt: String,
        systemInstruction: String? = null
    ): Pair<String, List<WebSource>> {
        if (apiKey.isBlank()) return missingKeyMessage() to emptyList()
        return try {
            val response = RetrofitClient.service.generateContent(
                model = SEARCH_MODEL,
                key = apiKey,
                request = GenerateContentRequest(
                    contents = listOf(Content(parts = listOf(Part(text = prompt)))),
                    tools = listOf(Tool()),
                    systemInstruction = systemInstruction?.let {
                        Content(parts = listOf(Part(text = it)))
                    }
                )
            )
            val text = response.firstText()
                ?: response.promptFeedback?.blockReason?.let { "TRANSMISSION BLOCKED: $it" }
                ?: "NO RESPONSE: the infoband returned an empty candidate set."
            val sources = response.candidates
                ?.firstOrNull()
                ?.groundMetadata
                ?.groundingChunks
                ?.mapNotNull { it.web }
                ?.distinctBy { it.uri }
                .orEmpty()
            text to sources
        } catch (e: Exception) {
            "CONNECTION FAULT: ${e.message ?: e::class.java.simpleName}" to emptyList()
        }
    }
}
