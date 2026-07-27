package com.example.game.api;

import com.squareup.moshi.JsonClass;
import java.util.List;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeminiApi.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes6.dex */
public final /* data */ class GenerateContentResponse {
    public static final int $stable = 8;
    private final List<Candidate> candidates;
    private final PromptFeedback promptFeedback;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ GenerateContentResponse copy$default(GenerateContentResponse generateContentResponse, List list, PromptFeedback promptFeedback, int i, Object obj) {
        if ((i & 1) != 0) {
            list = generateContentResponse.candidates;
        }
        if ((i & 2) != 0) {
            promptFeedback = generateContentResponse.promptFeedback;
        }
        return generateContentResponse.copy(list, promptFeedback);
    }

    public final List<Candidate> component1() {
        return this.candidates;
    }

    /* renamed from: component2, reason: from getter */
    public final PromptFeedback component2() {
        return this.promptFeedback;
    }

    public final GenerateContentResponse copy(List<Candidate> candidates, PromptFeedback promptFeedback) {
        return new GenerateContentResponse(candidates, promptFeedback);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GenerateContentResponse)) {
            return false;
        }
        GenerateContentResponse generateContentResponse = (GenerateContentResponse) other;
        return Intrinsics.areEqual(this.candidates, generateContentResponse.candidates) && Intrinsics.areEqual(this.promptFeedback, generateContentResponse.promptFeedback);
    }

    public int hashCode() {
        return ((this.candidates == null ? 0 : this.candidates.hashCode()) * 31) + (this.promptFeedback != null ? this.promptFeedback.hashCode() : 0);
    }

    public String toString() {
        return "GenerateContentResponse(candidates=" + this.candidates + ", promptFeedback=" + this.promptFeedback + ")";
    }

    public GenerateContentResponse(List<Candidate> list, PromptFeedback promptFeedback) {
        this.candidates = list;
        this.promptFeedback = promptFeedback;
    }

    public /* synthetic */ GenerateContentResponse(List list, PromptFeedback promptFeedback, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? null : promptFeedback);
    }

    public final List<Candidate> getCandidates() {
        return this.candidates;
    }

    public final PromptFeedback getPromptFeedback() {
        return this.promptFeedback;
    }
}
