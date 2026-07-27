package com.example.game.models;


import kotlin.jvm.internal.Intrinsics;

/* compiled from: ParallelEarthModels.kt */

/* loaded from: classes4.dex */
public final /* data */ class ParallelNpc {
    public static final int $stable = 0;
    private final String dialogueText;
    private final String id;
    private final String loreUnlock;
    private final String name;
    private final ParallelEarth parallelEarth;
    private final String portraitSymbol;
    private final String role;

    public static /* synthetic */ ParallelNpc copy$default(ParallelNpc parallelNpc, String str, String str2, String str3, ParallelEarth parallelEarth, String str4, String str5, String str6, int i, Object obj) {
        if ((i & 1) != 0) {
            str = parallelNpc.id;
        }
        if ((i & 2) != 0) {
            str2 = parallelNpc.name;
        }
        if ((i & 4) != 0) {
            str3 = parallelNpc.role;
        }
        if ((i & 8) != 0) {
            parallelEarth = parallelNpc.parallelEarth;
        }
        if ((i & 16) != 0) {
            str4 = parallelNpc.dialogueText;
        }
        if ((i & 32) != 0) {
            str5 = parallelNpc.loreUnlock;
        }
        if ((i & 64) != 0) {
            str6 = parallelNpc.portraitSymbol;
        }
        String str7 = str5;
        String str8 = str6;
        String str9 = str4;
        String str10 = str3;
        return parallelNpc.copy(str, str2, str10, parallelEarth, str9, str7, str8);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String component2() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final String component3() {
        return this.role;
    }

    /* renamed from: component4, reason: from getter */
    public final ParallelEarth component4() {
        return this.parallelEarth;
    }

    /* renamed from: component5, reason: from getter */
    public final String component5() {
        return this.dialogueText;
    }

    /* renamed from: component6, reason: from getter */
    public final String component6() {
        return this.loreUnlock;
    }

    /* renamed from: component7, reason: from getter */
    public final String component7() {
        return this.portraitSymbol;
    }

    public final ParallelNpc copy(String id, String name, String role, ParallelEarth parallelEarth, String dialogueText, String loreUnlock, String portraitSymbol) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(role, "role");
        Intrinsics.checkNotNullParameter(parallelEarth, "parallelEarth");
        Intrinsics.checkNotNullParameter(dialogueText, "dialogueText");
        Intrinsics.checkNotNullParameter(loreUnlock, "loreUnlock");
        Intrinsics.checkNotNullParameter(portraitSymbol, "portraitSymbol");
        return new ParallelNpc(id, name, role, parallelEarth, dialogueText, loreUnlock, portraitSymbol);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParallelNpc)) {
            return false;
        }
        ParallelNpc parallelNpc = (ParallelNpc) other;
        return Intrinsics.areEqual(this.id, parallelNpc.id) && Intrinsics.areEqual(this.name, parallelNpc.name) && Intrinsics.areEqual(this.role, parallelNpc.role) && this.parallelEarth == parallelNpc.parallelEarth && Intrinsics.areEqual(this.dialogueText, parallelNpc.dialogueText) && Intrinsics.areEqual(this.loreUnlock, parallelNpc.loreUnlock) && Intrinsics.areEqual(this.portraitSymbol, parallelNpc.portraitSymbol);
    }

    public int hashCode() {
        return (((((((((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.role.hashCode()) * 31) + this.parallelEarth.hashCode()) * 31) + this.dialogueText.hashCode()) * 31) + this.loreUnlock.hashCode()) * 31) + this.portraitSymbol.hashCode();
    }

    public String toString() {
        return "ParallelNpc(id=" + this.id + ", name=" + this.name + ", role=" + this.role + ", parallelEarth=" + this.parallelEarth + ", dialogueText=" + this.dialogueText + ", loreUnlock=" + this.loreUnlock + ", portraitSymbol=" + this.portraitSymbol + ")";
    }

    public ParallelNpc(String id, String name, String role, ParallelEarth parallelEarth, String dialogueText, String loreUnlock, String portraitSymbol) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(role, "role");
        Intrinsics.checkNotNullParameter(parallelEarth, "parallelEarth");
        Intrinsics.checkNotNullParameter(dialogueText, "dialogueText");
        Intrinsics.checkNotNullParameter(loreUnlock, "loreUnlock");
        Intrinsics.checkNotNullParameter(portraitSymbol, "portraitSymbol");
        this.id = id;
        this.name = name;
        this.role = role;
        this.parallelEarth = parallelEarth;
        this.dialogueText = dialogueText;
        this.loreUnlock = loreUnlock;
        this.portraitSymbol = portraitSymbol;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getRole() {
        return this.role;
    }

    public final ParallelEarth getParallelEarth() {
        return this.parallelEarth;
    }

    public final String getDialogueText() {
        return this.dialogueText;
    }

    public final String getLoreUnlock() {
        return this.loreUnlock;
    }

    public final String getPortraitSymbol() {
        return this.portraitSymbol;
    }
}
