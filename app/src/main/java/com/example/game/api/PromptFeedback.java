package com.example.game.api;

import com.squareup.moshi.JsonClass;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeminiApi.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes6.dex */
public final /* data */ class PromptFeedback {
    public static final int $stable = 0;
    private final String blockReason;

    /* JADX WARN: Multi-variable type inference failed */
    public PromptFeedback() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ PromptFeedback copy$default(PromptFeedback promptFeedback, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = promptFeedback.blockReason;
        }
        return promptFeedback.copy(str);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.blockReason;
    }

    public final PromptFeedback copy(String blockReason) {
        return new PromptFeedback(blockReason);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof PromptFeedback) && Intrinsics.areEqual(this.blockReason, ((PromptFeedback) other).blockReason);
    }

    public int hashCode() {
        if (this.blockReason == null) {
            return 0;
        }
        return this.blockReason.hashCode();
    }

    public String toString() {
        return "PromptFeedback(blockReason=" + this.blockReason + ")";
    }

    public PromptFeedback(String blockReason) {
        this.blockReason = blockReason;
    }

    public /* synthetic */ PromptFeedback(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }

    public final String getBlockReason() {
        return this.blockReason;
    }
}
