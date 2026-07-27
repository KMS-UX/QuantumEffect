package com.example.game.api;

import com.squareup.moshi.JsonClass;
import java.util.List;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeminiApi.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes6.dex */
public final /* data */ class GroundingMetadata {
    public static final int $stable = 8;
    private final List<GroundingChunk> groundingChunks;
    private final List<String> webSearchQueries;

    /* JADX WARN: Multi-variable type inference failed */
    public GroundingMetadata() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ GroundingMetadata copy$default(GroundingMetadata groundingMetadata, List list, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = groundingMetadata.webSearchQueries;
        }
        if ((i & 2) != 0) {
            list2 = groundingMetadata.groundingChunks;
        }
        return groundingMetadata.copy(list, list2);
    }

    public final List<String> component1() {
        return this.webSearchQueries;
    }

    public final List<GroundingChunk> component2() {
        return this.groundingChunks;
    }

    public final GroundingMetadata copy(List<String> webSearchQueries, List<GroundingChunk> groundingChunks) {
        return new GroundingMetadata(webSearchQueries, groundingChunks);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GroundingMetadata)) {
            return false;
        }
        GroundingMetadata groundingMetadata = (GroundingMetadata) other;
        return Intrinsics.areEqual(this.webSearchQueries, groundingMetadata.webSearchQueries) && Intrinsics.areEqual(this.groundingChunks, groundingMetadata.groundingChunks);
    }

    public int hashCode() {
        return ((this.webSearchQueries == null ? 0 : this.webSearchQueries.hashCode()) * 31) + (this.groundingChunks != null ? this.groundingChunks.hashCode() : 0);
    }

    public String toString() {
        return "GroundingMetadata(webSearchQueries=" + this.webSearchQueries + ", groundingChunks=" + this.groundingChunks + ")";
    }

    public GroundingMetadata(List<String> list, List<GroundingChunk> list2) {
        this.webSearchQueries = list;
        this.groundingChunks = list2;
    }

    public /* synthetic */ GroundingMetadata(List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list, (i & 2) != 0 ? null : list2);
    }

    public final List<String> getWebSearchQueries() {
        return this.webSearchQueries;
    }

    public final List<GroundingChunk> getGroundingChunks() {
        return this.groundingChunks;
    }
}
