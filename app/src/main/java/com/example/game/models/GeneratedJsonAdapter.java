package com.example.game.models;

import androidx.compose.ui.graphics.Color;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.internal.Util;
import java.lang.reflect.Constructor;

import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BattleLogJsonAdapter.kt */

/* renamed from: com.example.game.models.BattleLogJsonAdapter, reason: from toString */
/* loaded from: classes4.dex */
public final class GeneratedJsonAdapter extends JsonAdapter<BattleLog> {
    public static final int $stable = 8;
    private final JsonAdapter<Boolean> booleanAdapter;
    private final JsonAdapter<Color> colorAdapter;
    private volatile Constructor<BattleLog> constructorRef;
    private final JsonReader.Options options;
    private final JsonAdapter<String> stringAdapter;

    public GeneratedJsonAdapter(Moshi moshi) {
        Intrinsics.checkNotNullParameter(moshi, "moshi");
        JsonReader.Options of = JsonReader.Options.of("text", "isPlayerAction", "color");
        Intrinsics.checkNotNullExpressionValue(of, "of(...)");
        this.options = of;
        JsonAdapter<String> adapter = moshi.adapter(String.class, SetsKt.emptySet(), "text");
        Intrinsics.checkNotNullExpressionValue(adapter, "adapter(...)");
        this.stringAdapter = adapter;
        JsonAdapter<Boolean> adapter2 = moshi.adapter(Boolean.TYPE, SetsKt.emptySet(), "isPlayerAction");
        Intrinsics.checkNotNullExpressionValue(adapter2, "adapter(...)");
        this.booleanAdapter = adapter2;
        JsonAdapter<Color> adapter3 = moshi.adapter(Color.class, SetsKt.emptySet(), "color");
        Intrinsics.checkNotNullExpressionValue(adapter3, "adapter(...)");
        this.colorAdapter = adapter3;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(31);
        sb.append("GeneratedJsonAdapter(").append("BattleLog").append(')');
        return sb.toString();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.squareup.moshi.JsonAdapter
    public BattleLog fromJson(JsonReader reader) {
        Intrinsics.checkNotNullParameter(reader, "reader");
        Boolean isPlayerAction = false;
        Color color = null;
        int mask0 = -1;
        reader.beginObject();
        String text = null;
        while (reader.hasNext()) {
            switch (reader.selectName(this.options)) {
                case -1:
                    reader.skipName();
                    reader.skipValue();
                    break;
                case 0:
                    String fromJson = this.stringAdapter.fromJson(reader);
                    if (fromJson == null) {
                        throw Util.unexpectedNull("text", "text", reader);
                    }
                    text = fromJson;
                    break;
                case 1:
                    Boolean fromJson2 = this.booleanAdapter.fromJson(reader);
                    if (fromJson2 == null) {
                        throw Util.unexpectedNull("isPlayerAction", "isPlayerAction", reader);
                    }
                    isPlayerAction = fromJson2;
                    mask0 &= -3;
                    break;
                case 2:
                    Color fromJson3 = this.colorAdapter.fromJson(reader);
                    if (fromJson3 == null) {
                        throw Util.unexpectedNull("color", "color", reader);
                    }
                    color = fromJson3;
                    mask0 &= -5;
                    break;
            }
        }
        reader.endObject();
        if (mask0 == -7) {
            if (text != null) {
                return new BattleLog(text, isPlayerAction.booleanValue(), color.m4169unboximpl(), null);
            }
            throw Util.missingProperty("text", "text", reader);
        }
        Constructor localConstructor = this.constructorRef;
        if (localConstructor == null) {
            localConstructor = BattleLog.class.getDeclaredConstructor(String.class, Boolean.TYPE, Color.class, Integer.TYPE, Util.DEFAULT_CONSTRUCTOR_MARKER);
            this.constructorRef = localConstructor;
            Intrinsics.checkNotNullExpressionValue(localConstructor, "also(...)");
        }
        if (text != null) {
            BattleLog newInstance = localConstructor.newInstance(text, isPlayerAction, color, Integer.valueOf(mask0), null);
            Intrinsics.checkNotNullExpressionValue(newInstance, "newInstance(...)");
            return newInstance;
        }
        throw Util.missingProperty("text", "text", reader);
    }

    @Override // com.squareup.moshi.JsonAdapter
    public void toJson(JsonWriter writer, BattleLog value_) {
        Intrinsics.checkNotNullParameter(writer, "writer");
        if (value_ == null) {
            throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
        }
        writer.beginObject();
        writer.name("text");
        this.stringAdapter.toJson(writer, (JsonWriter) value_.getText());
        writer.name("isPlayerAction");
        this.booleanAdapter.toJson(writer, (JsonWriter) Boolean.valueOf(value_.isPlayerAction()));
        writer.name("color");
        this.colorAdapter.toJson(writer, (JsonWriter) Color.m4149boximpl(value_.m6992getColor0d7_KjU()));
        writer.endObject();
    }
}
