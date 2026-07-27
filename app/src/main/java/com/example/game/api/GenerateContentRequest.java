package com.example.game.api;

import com.squareup.moshi.JsonClass;
import java.util.List;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GeminiApi.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes6.dex */
public final /* data */ class GenerateContentRequest {
    public static final int $stable = 8;
    private final List<Content> contents;
    private final GenerationConfig generationConfig;
    private final Content systemInstruction;
    private final List<Tool> tools;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ GenerateContentRequest copy$default(GenerateContentRequest generateContentRequest, List list, GenerationConfig generationConfig, List list2, Content content, int i, Object obj) {
        if ((i & 1) != 0) {
            list = generateContentRequest.contents;
        }
        if ((i & 2) != 0) {
            generationConfig = generateContentRequest.generationConfig;
        }
        if ((i & 4) != 0) {
            list2 = generateContentRequest.tools;
        }
        if ((i & 8) != 0) {
            content = generateContentRequest.systemInstruction;
        }
        return generateContentRequest.copy(list, generationConfig, list2, content);
    }

    public final List<Content> component1() {
        return this.contents;
    }

    /* renamed from: component2, reason: from getter */
    public final GenerationConfig component2() {
        return this.generationConfig;
    }

    public final List<Tool> component3() {
        return this.tools;
    }

    /* renamed from: component4, reason: from getter */
    public final Content component4() {
        return this.systemInstruction;
    }

    public final GenerateContentRequest copy(List<Content> contents, GenerationConfig generationConfig, List<Tool> tools, Content systemInstruction) {
        Intrinsics.checkNotNullParameter(contents, "contents");
        return new GenerateContentRequest(contents, generationConfig, tools, systemInstruction);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GenerateContentRequest)) {
            return false;
        }
        GenerateContentRequest generateContentRequest = (GenerateContentRequest) other;
        return Intrinsics.areEqual(this.contents, generateContentRequest.contents) && Intrinsics.areEqual(this.generationConfig, generateContentRequest.generationConfig) && Intrinsics.areEqual(this.tools, generateContentRequest.tools) && Intrinsics.areEqual(this.systemInstruction, generateContentRequest.systemInstruction);
    }

    public int hashCode() {
        return (((((this.contents.hashCode() * 31) + (this.generationConfig == null ? 0 : this.generationConfig.hashCode())) * 31) + (this.tools == null ? 0 : this.tools.hashCode())) * 31) + (this.systemInstruction != null ? this.systemInstruction.hashCode() : 0);
    }

    public String toString() {
        return "GenerateContentRequest(contents=" + this.contents + ", generationConfig=" + this.generationConfig + ", tools=" + this.tools + ", systemInstruction=" + this.systemInstruction + ")";
    }

    public GenerateContentRequest(List<Content> contents, GenerationConfig generationConfig, List<Tool> list, Content systemInstruction) {
        Intrinsics.checkNotNullParameter(contents, "contents");
        this.contents = contents;
        this.generationConfig = generationConfig;
        this.tools = list;
        this.systemInstruction = systemInstruction;
    }

    public /* synthetic */ GenerateContentRequest(List list, GenerationConfig generationConfig, List list2, Content content, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? null : generationConfig, (i & 4) != 0 ? null : list2, (i & 8) != 0 ? null : content);
    }

    public final List<Content> getContents() {
        return this.contents;
    }

    public final GenerationConfig getGenerationConfig() {
        return this.generationConfig;
    }

    public final List<Tool> getTools() {
        return this.tools;
    }

    public final Content getSystemInstruction() {
        return this.systemInstruction;
    }
}
