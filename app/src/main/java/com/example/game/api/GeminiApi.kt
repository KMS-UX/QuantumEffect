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

// --- Gemini Request / Response Models for Moshi ---

@JsonClass(generateAdapter = true)
data class GenerateContentRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig? = null,
    val tools: List<Tool>? = null,
    val systemInstruction: Content? = null
)

@JsonClass(generateAdapter = true)
data class Content(
    val parts: List<Part>
)

@JsonClass(generateAdapter = true)
data class Part(
    val text: String? = null
)

@JsonClass(generateAdapter = true)
data class GenerationConfig(
    val temperature: Float? = null,
    val topP: Float? = null,
    val topK: Int? = null,
    val thinkingConfig: ThinkingConfig? = null
)

enum class ThinkingLevel {
    LOW, MEDIUM, HIGH
}

@JsonClass(generateAdapter = true)
data class ThinkingConfig(
    val thinkingLevel: String
)

@JsonClass(generateAdapter = true)
data class Tool(
    val googleSearch: Map<String, Any?> = emptyMap()
)

@JsonClass(generateAdapter = true)
data class GenerateContentResponse(
    val candidates: List<Candidate>?,
    val promptFeedback: PromptFeedback? = null
)

@JsonClass(generateAdapter = true)
data class Candidate(
    val content: Content?,
    val finishReason: String? = null,
    val groundMetadata: GroundingMetadata? = null
)

@JsonClass(generateAdapter = true)
data class GroundingMetadata(
    val webSearchQueries: List<String>? = null,
    val groundingChunks: List<GroundingChunk>? = null
)

@JsonClass(generateAdapter = true)
data class GroundingChunk(
    val web: WebSource? = null
)

@JsonClass(generateAdapter = true)
data class WebSource(
    val uri: String?,
    val title: String?
)

@JsonClass(generateAdapter = true)
data class PromptFeedback(
    val blockReason: String? = null
)

// --- Retrofit Interface ---

interface GeminiApiService {
    @POST("v1beta/models/{model}:generateContent")
    suspend fun generateContent(
        @Path("model") model: String,
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

// --- Client Singleton ---

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
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

// --- Service Helper Functions ---

object GeminiServiceHelper {

    /**
     * Call models/gemini-3.1-pro-preview with HIGH Thinking level.
     * No maxOutputTokens is configured, as instructed.
     */
    suspend fun generateHighThinkingContent(prompt: String, systemPrompt: String? = null): String {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return "ERROR: Gemini API Key is missing! Please configure it in the Secrets panel."
        }

        val request = GenerateContentRequest(
            contents = listOf(Content(parts = listOf(Part(text = prompt)))),
            generationConfig = GenerationConfig(
                temperature = 0.7f,
                thinkingConfig = ThinkingConfig(thinkingLevel = ThinkingLevel.HIGH.name)
            ),
            systemInstruction = systemPrompt?.let { Content(parts = listOf(Part(text = it))) }
        )

        return try {
            val response = RetrofitClient.service.generateContent(
                model = "gemini-3.1-pro-preview",
                apiKey = apiKey,
                request = request
            )
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: "No response from AI."
        } catch (e: Exception) {
            "API Failure: ${e.localizedMessage ?: "Unknown Error"}"
        }
    }

    /**
     * Call models/gemini-3.5-flash with Google Search Grounding.
     */
    suspend fun generateSearchGroundedContent(prompt: String, systemPrompt: String? = null): Pair<String, List<WebSource>> {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return Pair("ERROR: Gemini API Key is missing! Please configure it in the Secrets panel.", emptyList())
        }

        val request = GenerateContentRequest(
            contents = listOf(Content(parts = listOf(Part(text = prompt)))),
            tools = listOf(Tool(googleSearch = emptyMap())),
            systemInstruction = systemPrompt?.let { Content(parts = listOf(Part(text = it))) }
        )

        return try {
            val response = RetrofitClient.service.generateContent(
                model = "gemini-3.5-flash",
                apiKey = apiKey,
                request = request
            )
            val candidate = response.candidates?.firstOrNull()
            val text = candidate?.content?.parts?.firstOrNull()?.text ?: "No response from AI."
            val sources = candidate?.groundMetadata?.groundingChunks?.mapNotNull { it.web } ?: emptyList()
            Pair(text, sources)
        } catch (e: Exception) {
            Pair("API Failure: ${e.localizedMessage ?: "Unknown Error"}", emptyList())
        }
    }
}
