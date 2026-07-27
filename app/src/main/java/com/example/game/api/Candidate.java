package com.example.game.api;

import com.squareup.moshi.JsonClass;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeminiApi.kt */
@JsonClass(generateAdapter = true)
/* loaded from: classes6.dex */
public final /* data */ class Candidate {
    public static final int $stable = 8;
    private final Content content;
    private final String finishReason;
    private final GroundingMetadata groundMetadata;

    public static /* synthetic */ Candidate copy$default(Candidate candidate, Content content, String str, GroundingMetadata groundingMetadata, int i, Object obj) {
        if ((i & 1) != 0) {
            content = candidate.content;
        }
        if ((i & 2) != 0) {
            str = candidate.finishReason;
        }
        if ((i & 4) != 0) {
            groundingMetadata = candidate.groundMetadata;
        }
        return candidate.copy(content, str, groundingMetadata);
    }

    /* renamed from: component1, reason: from getter */
    public final Content component1() {
        return this.content;
    }

    /* renamed from: component2, reason: from getter */
    public final String component2() {
        return this.finishReason;
    }

    /* renamed from: component3, reason: from getter */
    public final GroundingMetadata component3() {
        return this.groundMetadata;
    }

    public final Candidate copy(Content content, String finishReason, GroundingMetadata groundMetadata) {
        return new Candidate(content, finishReason, groundMetadata);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Candidate)) {
            return false;
        }
        Candidate candidate = (Candidate) other;
        return Intrinsics.areEqual(this.content, candidate.content) && Intrinsics.areEqual(this.finishReason, candidate.finishReason) && Intrinsics.areEqual(this.groundMetadata, candidate.groundMetadata);
    }

    public int hashCode() {
        return ((((this.content == null ? 0 : this.content.hashCode()) * 31) + (this.finishReason == null ? 0 : this.finishReason.hashCode())) * 31) + (this.groundMetadata != null ? this.groundMetadata.hashCode() : 0);
    }

    public String toString() {
        return "Candidate(content=" + this.content + ", finishReason=" + this.finishReason + ", groundMetadata=" + this.groundMetadata + ")";
    }

    public Candidate(Content content, String finishReason, GroundingMetadata groundMetadata) {
        this.content = content;
        this.finishReason = finishReason;
        this.groundMetadata = groundMetadata;
    }

    public /* synthetic */ Candidate(Content content, String str, GroundingMetadata groundingMetadata, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(content, (i & 2) != 0 ? null : str, (i & 4) != 0 ? null : groundingMetadata);
    }

    public final Content getContent() {
        return this.content;
    }

    public final String getFinishReason() {
        return this.finishReason;
    }

    public final GroundingMetadata getGroundMetadata() {
        return this.groundMetadata;
    }
}
