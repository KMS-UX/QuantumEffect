package com.example.game.models;

import androidx.compose.ui.graphics.Color;
import com.squareup.moshi.JsonClass;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameModels.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes4.dex */
public final /* data */ class BattleLog {
    public static final int $stable = 0;
    private final long color;
    private final boolean isPlayerAction;
    private final String text;

    public /* synthetic */ BattleLog(String str, boolean z, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, z, j);
    }

    /* renamed from: copy-mxwnekA$default, reason: not valid java name */
    public static /* synthetic */ BattleLog m6989copymxwnekA$default(BattleLog battleLog, String str, boolean z, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            str = battleLog.text;
        }
        if ((i & 2) != 0) {
            z = battleLog.isPlayerAction;
        }
        if ((i & 4) != 0) {
            j = battleLog.color;
        }
        return battleLog.m6991copymxwnekA(str, z, j);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.text;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean component2() {
        return this.isPlayerAction;
    }

    /* renamed from: component3-0d7_KjU, reason: not valid java name and from getter */
    public final long getColor() {
        return this.color;
    }

    /* renamed from: copy-mxwnekA, reason: not valid java name */
    public final BattleLog m6991copymxwnekA(String text, boolean isPlayerAction, long color) {
        Intrinsics.checkNotNullParameter(text, "text");
        return new BattleLog(text, isPlayerAction, color, null);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BattleLog)) {
            return false;
        }
        BattleLog battleLog = (BattleLog) other;
        return Intrinsics.areEqual(this.text, battleLog.text) && this.isPlayerAction == battleLog.isPlayerAction && Color.m4160equalsimpl0(this.color, battleLog.color);
    }

    public int hashCode() {
        return (((this.text.hashCode() * 31) + Boolean.hashCode(this.isPlayerAction)) * 31) + Color.m4166hashCodeimpl(this.color);
    }

    public String toString() {
        return "BattleLog(text=" + this.text + ", isPlayerAction=" + this.isPlayerAction + ", color=" + Color.m4167toStringimpl(this.color) + ")";
    }

    private BattleLog(String text, boolean isPlayerAction, long color) {
        Intrinsics.checkNotNullParameter(text, "text");
        this.text = text;
        this.isPlayerAction = isPlayerAction;
        this.color = color;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ BattleLog(java.lang.String r7, boolean r8, long r9, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r6 = this;
            r12 = r11 & 2
            if (r12 == 0) goto L7
            r8 = 1
            r2 = r8
            goto L8
        L7:
            r2 = r8
        L8:
            r8 = r11 & 4
            if (r8 == 0) goto L14
            androidx.compose.ui.graphics.Color$Companion r8 = androidx.compose.ui.graphics.Color.INSTANCE
            long r9 = r8.m4196getWhite0d7_KjU()
            r3 = r9
            goto L15
        L14:
            r3 = r9
        L15:
            r5 = 0
            r0 = r6
            r1 = r7
            r0.<init>(r1, r2, r3, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.models.BattleLog.<init>(java.lang.String, boolean, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final String getText() {
        return this.text;
    }

    public final boolean isPlayerAction() {
        return this.isPlayerAction;
    }

    /* renamed from: getColor-0d7_KjU, reason: not valid java name */
    public final long m6992getColor0d7_KjU() {
        return this.color;
    }
}
