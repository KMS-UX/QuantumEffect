package com.example.game.api;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.internal.Util;
import java.lang.reflect.Constructor;
import java.util.List;

import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GenerateContentRequestJsonAdapter.kt */

/* renamed from: com.example.game.api.GenerateContentRequestJsonAdapter, reason: from toString */
/* loaded from: classes6.dex */
public final class GeneratedJsonAdapter extends JsonAdapter<GenerateContentRequest> {
    public static final int $stable = 8;
    private volatile Constructor<GenerateContentRequest> constructorRef;
    private final JsonAdapter<List<Content>> listOfContentAdapter;
    private final JsonAdapter<Content> nullableContentAdapter;
    private final JsonAdapter<GenerationConfig> nullableGenerationConfigAdapter;
    private final JsonAdapter<List<Tool>> nullableListOfToolAdapter;
    private final JsonReader.Options options;

    public GeneratedJsonAdapter(Moshi moshi) {
        Intrinsics.checkNotNullParameter(moshi, "moshi");
        JsonReader.Options of = JsonReader.Options.of("contents", "generationConfig", "tools", "systemInstruction");
        Intrinsics.checkNotNullExpressionValue(of, "of(...)");
        this.options = of;
        JsonAdapter<List<Content>> adapter = moshi.adapter(Types.newParameterizedType(List.class, Content.class), SetsKt.emptySet(), "contents");
        Intrinsics.checkNotNullExpressionValue(adapter, "adapter(...)");
        this.listOfContentAdapter = adapter;
        JsonAdapter<GenerationConfig> adapter2 = moshi.adapter(GenerationConfig.class, SetsKt.emptySet(), "generationConfig");
        Intrinsics.checkNotNullExpressionValue(adapter2, "adapter(...)");
        this.nullableGenerationConfigAdapter = adapter2;
        JsonAdapter<List<Tool>> adapter3 = moshi.adapter(Types.newParameterizedType(List.class, Tool.class), SetsKt.emptySet(), "tools");
        Intrinsics.checkNotNullExpressionValue(adapter3, "adapter(...)");
        this.nullableListOfToolAdapter = adapter3;
        JsonAdapter<Content> adapter4 = moshi.adapter(Content.class, SetsKt.emptySet(), "systemInstruction");
        Intrinsics.checkNotNullExpressionValue(adapter4, "adapter(...)");
        this.nullableContentAdapter = adapter4;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(44);
        sb.append("GeneratedJsonAdapter(").append("GenerateContentRequest").append(')');
        return sb.toString();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.squareup.moshi.JsonAdapter
    public GenerateContentRequest fromJson(JsonReader reader) {
        Intrinsics.checkNotNullParameter(reader, "reader");
        int mask0 = -1;
        reader.beginObject();
        List contents = null;
        GenerationConfig generationConfig = null;
        List tools = null;
        Content systemInstruction = null;
        while (reader.hasNext()) {
            switch (reader.selectName(this.options)) {
                case -1:
                    reader.skipName();
                    reader.skipValue();
                    break;
                case 0:
                    List fromJson = this.listOfContentAdapter.fromJson(reader);
                    if (fromJson == null) {
                        throw Util.unexpectedNull("contents", "contents", reader);
                    }
                    contents = fromJson;
                    break;
                case 1:
                    GenerationConfig generationConfig2 = this.nullableGenerationConfigAdapter.fromJson(reader);
                    generationConfig = generationConfig2;
                    mask0 &= -3;
                    break;
                case 2:
                    List tools2 = this.nullableListOfToolAdapter.fromJson(reader);
                    tools = tools2;
                    mask0 &= -5;
                    break;
                case 3:
                    Content systemInstruction2 = this.nullableContentAdapter.fromJson(reader);
                    systemInstruction = systemInstruction2;
                    mask0 &= -9;
                    break;
            }
        }
        reader.endObject();
        if (mask0 == -15) {
            if (contents != null) {
                return new GenerateContentRequest(contents, generationConfig, tools, systemInstruction);
            }
            throw Util.missingProperty("contents", "contents", reader);
        }
        Constructor localConstructor = this.constructorRef;
        if (localConstructor == null) {
            localConstructor = GenerateContentRequest.class.getDeclaredConstructor(List.class, GenerationConfig.class, List.class, Content.class, Integer.TYPE, Util.DEFAULT_CONSTRUCTOR_MARKER);
            this.constructorRef = localConstructor;
            Intrinsics.checkNotNullExpressionValue(localConstructor, "also(...)");
        }
        if (contents == null) {
            throw Util.missingProperty("contents", "contents", reader);
        }
        GenerateContentRequest newInstance = localConstructor.newInstance(contents, generationConfig, tools, systemInstruction, Integer.valueOf(mask0), null);
        Intrinsics.checkNotNullExpressionValue(newInstance, "newInstance(...)");
        return newInstance;
    }

    @Override // com.squareup.moshi.JsonAdapter
    public void toJson(JsonWriter writer, GenerateContentRequest value_) {
        Intrinsics.checkNotNullParameter(writer, "writer");
        if (value_ == null) {
            throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
        }
        writer.beginObject();
        writer.name("contents");
        this.listOfContentAdapter.toJson(writer, (JsonWriter) value_.getContents());
        writer.name("generationConfig");
        this.nullableGenerationConfigAdapter.toJson(writer, (JsonWriter) value_.getGenerationConfig());
        writer.name("tools");
        this.nullableListOfToolAdapter.toJson(writer, (JsonWriter) value_.getTools());
        writer.name("systemInstruction");
        this.nullableContentAdapter.toJson(writer, (JsonWriter) value_.getSystemInstruction());
        writer.endObject();
    }
}
