package com.example.game.api;

import com.squareup.moshi.JsonClass;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeminiApi.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes6.dex */
public final /* data */ class Part {
    public static final int $stable = 0;
    private final String text;

    /* JADX WARN: Multi-variable type inference failed */
    public Part() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public static /* synthetic */ Part copy$default(Part part, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = part.text;
        }
        return part.copy(str);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.text;
    }

    public final Part copy(String text) {
        return new Part(text);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof Part) && Intrinsics.areEqual(this.text, ((Part) other).text);
    }

    public int hashCode() {
        if (this.text == null) {
            return 0;
        }
        return this.text.hashCode();
    }

    public String toString() {
        return "Part(text=" + this.text + ")";
    }

    public Part(String text) {
        this.text = text;
    }

    public /* synthetic */ Part(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }

    public final String getText() {
        return this.text;
    }
}
