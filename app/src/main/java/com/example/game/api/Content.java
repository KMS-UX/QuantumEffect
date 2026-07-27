package com.example.game.api;

import com.squareup.moshi.JsonClass;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeminiApi.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes6.dex */
public final /* data */ class Content {
    public static final int $stable = 8;
    private final List<Part> parts;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Content copy$default(Content content, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = content.parts;
        }
        return content.copy(list);
    }

    public final List<Part> component1() {
        return this.parts;
    }

    public final Content copy(List<Part> parts) {
        Intrinsics.checkNotNullParameter(parts, "parts");
        return new Content(parts);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof Content) && Intrinsics.areEqual(this.parts, ((Content) other).parts);
    }

    public int hashCode() {
        return this.parts.hashCode();
    }

    public String toString() {
        return "Content(parts=" + this.parts + ")";
    }

    public Content(List<Part> parts) {
        Intrinsics.checkNotNullParameter(parts, "parts");
        this.parts = parts;
    }

    public final List<Part> getParts() {
        return this.parts;
    }
}
