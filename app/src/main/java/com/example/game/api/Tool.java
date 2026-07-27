package com.example.game.api;

import com.squareup.moshi.JsonClass;
import java.util.Map;

import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeminiApi.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes6.dex */
public final /* data */ class Tool {
    public static final int $stable = 8;
    private final Map<String, Object> googleSearch;

    /* JADX WARN: Multi-variable type inference failed */
    public Tool() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Tool copy$default(Tool tool, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            map = tool.googleSearch;
        }
        return tool.copy(map);
    }

    public final Map<String, Object> component1() {
        return this.googleSearch;
    }

    public final Tool copy(Map<String, ? extends Object> googleSearch) {
        Intrinsics.checkNotNullParameter(googleSearch, "googleSearch");
        return new Tool(googleSearch);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof Tool) && Intrinsics.areEqual(this.googleSearch, ((Tool) other).googleSearch);
    }

    public int hashCode() {
        return this.googleSearch.hashCode();
    }

    public String toString() {
        return "Tool(googleSearch=" + this.googleSearch + ")";
    }

    public Tool(Map<String, ? extends Object> googleSearch) {
        Intrinsics.checkNotNullParameter(googleSearch, "googleSearch");
        this.googleSearch = googleSearch;
    }

    public /* synthetic */ Tool(Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? MapsKt.emptyMap() : map);
    }

    public final Map<String, Object> getGoogleSearch() {
        return this.googleSearch;
    }
}
