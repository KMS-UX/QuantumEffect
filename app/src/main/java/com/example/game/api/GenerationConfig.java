package com.example.game.api;

import com.squareup.moshi.JsonClass;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeminiApi.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes6.dex */
public final /* data */ class GenerationConfig {
    public static final int $stable = 0;
    private final Float temperature;
    private final ThinkingConfig thinkingConfig;
    private final Integer topK;
    private final Float topP;

    public GenerationConfig() {
        this(null, null, null, null, 15, null);
    }

    public static /* synthetic */ GenerationConfig copy$default(GenerationConfig generationConfig, Float f, Float f2, Integer num, ThinkingConfig thinkingConfig, int i, Object obj) {
        if ((i & 1) != 0) {
            f = generationConfig.temperature;
        }
        if ((i & 2) != 0) {
            f2 = generationConfig.topP;
        }
        if ((i & 4) != 0) {
            num = generationConfig.topK;
        }
        if ((i & 8) != 0) {
            thinkingConfig = generationConfig.thinkingConfig;
        }
        return generationConfig.copy(f, f2, num, thinkingConfig);
    }

    /* renamed from: component1, reason: from getter */
    public final Float component1() {
        return this.temperature;
    }

    /* renamed from: component2, reason: from getter */
    public final Float component2() {
        return this.topP;
    }

    /* renamed from: component3, reason: from getter */
    public final Integer component3() {
        return this.topK;
    }

    /* renamed from: component4, reason: from getter */
    public final ThinkingConfig component4() {
        return this.thinkingConfig;
    }

    public final GenerationConfig copy(Float temperature, Float topP, Integer topK, ThinkingConfig thinkingConfig) {
        return new GenerationConfig(temperature, topP, topK, thinkingConfig);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GenerationConfig)) {
            return false;
        }
        GenerationConfig generationConfig = (GenerationConfig) other;
        return Intrinsics.areEqual((Object) this.temperature, (Object) generationConfig.temperature) && Intrinsics.areEqual((Object) this.topP, (Object) generationConfig.topP) && Intrinsics.areEqual(this.topK, generationConfig.topK) && Intrinsics.areEqual(this.thinkingConfig, generationConfig.thinkingConfig);
    }

    public int hashCode() {
        return ((((((this.temperature == null ? 0 : this.temperature.hashCode()) * 31) + (this.topP == null ? 0 : this.topP.hashCode())) * 31) + (this.topK == null ? 0 : this.topK.hashCode())) * 31) + (this.thinkingConfig != null ? this.thinkingConfig.hashCode() : 0);
    }

    public String toString() {
        return "GenerationConfig(temperature=" + this.temperature + ", topP=" + this.topP + ", topK=" + this.topK + ", thinkingConfig=" + this.thinkingConfig + ")";
    }

    public GenerationConfig(Float temperature, Float topP, Integer topK, ThinkingConfig thinkingConfig) {
        this.temperature = temperature;
        this.topP = topP;
        this.topK = topK;
        this.thinkingConfig = thinkingConfig;
    }

    public /* synthetic */ GenerationConfig(Float f, Float f2, Integer num, ThinkingConfig thinkingConfig, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : f, (i & 2) != 0 ? null : f2, (i & 4) != 0 ? null : num, (i & 8) != 0 ? null : thinkingConfig);
    }

    public final Float getTemperature() {
        return this.temperature;
    }

    public final Float getTopP() {
        return this.topP;
    }

    public final Integer getTopK() {
        return this.topK;
    }

    public final ThinkingConfig getThinkingConfig() {
        return this.thinkingConfig;
    }
}
