package com.example.game.models;

import com.squareup.moshi.JsonClass;


import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameModels.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes4.dex */
public final /* data */ class DeployedStructure {
    public static final int $stable = 8;
    private final String biomeName;
    private final long colorVal;
    private final String factionName;
    private final long id;
    private boolean isUpgraded;
    private int level;
    private final String name;
    private final String type;
    private final float x;
    private final float y;

    public static /* synthetic */ DeployedStructure copy$default(DeployedStructure deployedStructure, long j, String str, String str2, String str3, String str4, float f, float f2, long j2, boolean z, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            j = deployedStructure.id;
        }
        return deployedStructure.copy(j, (i2 & 2) != 0 ? deployedStructure.type : str, (i2 & 4) != 0 ? deployedStructure.name : str2, (i2 & 8) != 0 ? deployedStructure.factionName : str3, (i2 & 16) != 0 ? deployedStructure.biomeName : str4, (i2 & 32) != 0 ? deployedStructure.x : f, (i2 & 64) != 0 ? deployedStructure.y : f2, (i2 & 128) != 0 ? deployedStructure.colorVal : j2, (i2 & 256) != 0 ? deployedStructure.isUpgraded : z, (i2 & 512) != 0 ? deployedStructure.level : i);
    }

    /* renamed from: component1, reason: from getter */
    public final long component1() {
        return this.id;
    }

    /* renamed from: component10, reason: from getter */
    public final int component10() {
        return this.level;
    }

    /* renamed from: component2, reason: from getter */
    public final String component2() {
        return this.type;
    }

    /* renamed from: component3, reason: from getter */
    public final String component3() {
        return this.name;
    }

    /* renamed from: component4, reason: from getter */
    public final String component4() {
        return this.factionName;
    }

    /* renamed from: component5, reason: from getter */
    public final String component5() {
        return this.biomeName;
    }

    /* renamed from: component6, reason: from getter */
    public final float component6() {
        return this.x;
    }

    /* renamed from: component7, reason: from getter */
    public final float component7() {
        return this.y;
    }

    /* renamed from: component8, reason: from getter */
    public final long component8() {
        return this.colorVal;
    }

    /* renamed from: component9, reason: from getter */
    public final boolean component9() {
        return this.isUpgraded;
    }

    public final DeployedStructure copy(long id, String type, String name, String factionName, String biomeName, float x, float y, long colorVal, boolean isUpgraded, int level) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(factionName, "factionName");
        Intrinsics.checkNotNullParameter(biomeName, "biomeName");
        return new DeployedStructure(id, type, name, factionName, biomeName, x, y, colorVal, isUpgraded, level);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeployedStructure)) {
            return false;
        }
        DeployedStructure deployedStructure = (DeployedStructure) other;
        return this.id == deployedStructure.id && Intrinsics.areEqual(this.type, deployedStructure.type) && Intrinsics.areEqual(this.name, deployedStructure.name) && Intrinsics.areEqual(this.factionName, deployedStructure.factionName) && Intrinsics.areEqual(this.biomeName, deployedStructure.biomeName) && Float.compare(this.x, deployedStructure.x) == 0 && Float.compare(this.y, deployedStructure.y) == 0 && this.colorVal == deployedStructure.colorVal && this.isUpgraded == deployedStructure.isUpgraded && this.level == deployedStructure.level;
    }

    public int hashCode() {
        return (((((((((((((((((Long.hashCode(this.id) * 31) + this.type.hashCode()) * 31) + this.name.hashCode()) * 31) + this.factionName.hashCode()) * 31) + this.biomeName.hashCode()) * 31) + Float.hashCode(this.x)) * 31) + Float.hashCode(this.y)) * 31) + Long.hashCode(this.colorVal)) * 31) + Boolean.hashCode(this.isUpgraded)) * 31) + Integer.hashCode(this.level);
    }

    public String toString() {
        return "DeployedStructure(id=" + this.id + ", type=" + this.type + ", name=" + this.name + ", factionName=" + this.factionName + ", biomeName=" + this.biomeName + ", x=" + this.x + ", y=" + this.y + ", colorVal=" + this.colorVal + ", isUpgraded=" + this.isUpgraded + ", level=" + this.level + ")";
    }

    public DeployedStructure(long id, String type, String name, String factionName, String biomeName, float x, float y, long colorVal, boolean isUpgraded, int level) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(factionName, "factionName");
        Intrinsics.checkNotNullParameter(biomeName, "biomeName");
        this.id = id;
        this.type = type;
        this.name = name;
        this.factionName = factionName;
        this.biomeName = biomeName;
        this.x = x;
        this.y = y;
        this.colorVal = colorVal;
        this.isUpgraded = isUpgraded;
        this.level = level;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ DeployedStructure(long r16, java.lang.String r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, float r22, float r23, long r24, boolean r26, int r27, int r28, kotlin.jvm.internal.DefaultConstructorMarker r29) {
        /*
            r15 = this;
            r0 = r28
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L9
            r1 = 0
            r13 = r1
            goto Lb
        L9:
            r13 = r26
        Lb:
            r0 = r0 & 512(0x200, float:7.17E-43)
            if (r0 == 0) goto L12
            r0 = 1
            r14 = r0
            goto L14
        L12:
            r14 = r27
        L14:
            r2 = r15
            r3 = r16
            r5 = r18
            r6 = r19
            r7 = r20
            r8 = r21
            r9 = r22
            r10 = r23
            r11 = r24
            r2.<init>(r3, r5, r6, r7, r8, r9, r10, r11, r13, r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.models.DeployedStructure.<init>(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, float, float, long, boolean, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final long getId() {
        return this.id;
    }

    public final String getType() {
        return this.type;
    }

    public final String getName() {
        return this.name;
    }

    public final String getFactionName() {
        return this.factionName;
    }

    public final String getBiomeName() {
        return this.biomeName;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public final long getColorVal() {
        return this.colorVal;
    }

    public final boolean isUpgraded() {
        return this.isUpgraded;
    }

    public final void setUpgraded(boolean z) {
        this.isUpgraded = z;
    }

    public final int getLevel() {
        return this.level;
    }

    public final void setLevel(int i) {
        this.level = i;
    }
}
