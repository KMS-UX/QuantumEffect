package com.example.game.api;

import com.squareup.moshi.JsonClass;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeminiApi.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes6.dex */
public final /* data */ class ThinkingConfig {
    public static final int $stable = 0;
    private final String thinkingLevel;

    public static /* synthetic */ ThinkingConfig copy$default(ThinkingConfig thinkingConfig, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = thinkingConfig.thinkingLevel;
        }
        return thinkingConfig.copy(str);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.thinkingLevel;
    }

    public final ThinkingConfig copy(String thinkingLevel) {
        Intrinsics.checkNotNullParameter(thinkingLevel, "thinkingLevel");
        return new ThinkingConfig(thinkingLevel);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ThinkingConfig) && Intrinsics.areEqual(this.thinkingLevel, ((ThinkingConfig) other).thinkingLevel);
    }

    public int hashCode() {
        return this.thinkingLevel.hashCode();
    }

    public String toString() {
        return "ThinkingConfig(thinkingLevel=" + this.thinkingLevel + ")";
    }

    public ThinkingConfig(String thinkingLevel) {
        Intrinsics.checkNotNullParameter(thinkingLevel, "thinkingLevel");
        this.thinkingLevel = thinkingLevel;
    }

    public final String getThinkingLevel() {
        return this.thinkingLevel;
    }
}
