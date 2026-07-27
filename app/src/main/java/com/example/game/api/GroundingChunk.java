package com.example.game.api;

import com.squareup.moshi.JsonClass;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeminiApi.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes6.dex */
public final /* data */ class GroundingChunk {
    public static final int $stable = 0;
    private final WebSource web;

    /* JADX WARN: Multi-variable type inference failed */
    public GroundingChunk() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ GroundingChunk copy$default(GroundingChunk groundingChunk, WebSource webSource, int i, Object obj) {
        if ((i & 1) != 0) {
            webSource = groundingChunk.web;
        }
        return groundingChunk.copy(webSource);
    }

    /* renamed from: component1, reason: from getter */
    public final WebSource component1() {
        return this.web;
    }

    public final GroundingChunk copy(WebSource web) {
        return new GroundingChunk(web);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof GroundingChunk) && Intrinsics.areEqual(this.web, ((GroundingChunk) other).web);
    }

    public int hashCode() {
        if (this.web == null) {
            return 0;
        }
        return this.web.hashCode();
    }

    public String toString() {
        return "GroundingChunk(web=" + this.web + ")";
    }

    public GroundingChunk(WebSource web) {
        this.web = web;
    }

    public /* synthetic */ GroundingChunk(WebSource webSource, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : webSource);
    }

    public final WebSource getWeb() {
        return this.web;
    }
}
