package com.example.game.api;

import com.squareup.moshi.JsonClass;


import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeminiApi.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes6.dex */
public final /* data */ class WebSource {
    public static final int $stable = 0;
    private final String title;
    private final String uri;

    public static /* synthetic */ WebSource copy$default(WebSource webSource, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = webSource.uri;
        }
        if ((i & 2) != 0) {
            str2 = webSource.title;
        }
        return webSource.copy(str, str2);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.uri;
    }

    /* renamed from: component2, reason: from getter */
    public final String component2() {
        return this.title;
    }

    public final WebSource copy(String uri, String title) {
        return new WebSource(uri, title);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WebSource)) {
            return false;
        }
        WebSource webSource = (WebSource) other;
        return Intrinsics.areEqual(this.uri, webSource.uri) && Intrinsics.areEqual(this.title, webSource.title);
    }

    public int hashCode() {
        return ((this.uri == null ? 0 : this.uri.hashCode()) * 31) + (this.title != null ? this.title.hashCode() : 0);
    }

    public String toString() {
        return "WebSource(uri=" + this.uri + ", title=" + this.title + ")";
    }

    public WebSource(String uri, String title) {
        this.uri = uri;
        this.title = title;
    }

    public final String getUri() {
        return this.uri;
    }

    public final String getTitle() {
        return this.title;
    }
}
