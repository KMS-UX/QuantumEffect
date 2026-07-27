package com.example.game.models;


import kotlin.jvm.internal.Intrinsics;

/* compiled from: StarshipModels.kt */

/* loaded from: classes4.dex */
public final /* data */ class StarSystem {
    public static final int $stable = 0;
    private final ParallelEarth associatedReality;
    private final String description;
    private final String factionAffiliation;
    private final String id;
    private final boolean isDiscovered;
    private final String name;
    private final float resourceMultiplier;
    private final float sectorX;
    private final float sectorY;
    private final String specialNodeName;

    public static /* synthetic */ StarSystem copy$default(StarSystem starSystem, String str, String str2, float f, float f2, String str3, String str4, ParallelEarth parallelEarth, float f3, String str5, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = starSystem.id;
        }
        if ((i & 2) != 0) {
            str2 = starSystem.name;
        }
        if ((i & 4) != 0) {
            f = starSystem.sectorX;
        }
        if ((i & 8) != 0) {
            f2 = starSystem.sectorY;
        }
        if ((i & 16) != 0) {
            str3 = starSystem.factionAffiliation;
        }
        if ((i & 32) != 0) {
            str4 = starSystem.description;
        }
        if ((i & 64) != 0) {
            parallelEarth = starSystem.associatedReality;
        }
        if ((i & 128) != 0) {
            f3 = starSystem.resourceMultiplier;
        }
        if ((i & 256) != 0) {
            str5 = starSystem.specialNodeName;
        }
        if ((i & 512) != 0) {
            z = starSystem.isDiscovered;
        }
        String str6 = str5;
        boolean z2 = z;
        ParallelEarth parallelEarth2 = parallelEarth;
        float f4 = f3;
        String str7 = str3;
        String str8 = str4;
        return starSystem.copy(str, str2, f, f2, str7, str8, parallelEarth2, f4, str6, z2);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.id;
    }

    /* renamed from: component10, reason: from getter */
    public final boolean component10() {
        return this.isDiscovered;
    }

    /* renamed from: component2, reason: from getter */
    public final String component2() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final float component3() {
        return this.sectorX;
    }

    /* renamed from: component4, reason: from getter */
    public final float component4() {
        return this.sectorY;
    }

    /* renamed from: component5, reason: from getter */
    public final String component5() {
        return this.factionAffiliation;
    }

    /* renamed from: component6, reason: from getter */
    public final String component6() {
        return this.description;
    }

    /* renamed from: component7, reason: from getter */
    public final ParallelEarth component7() {
        return this.associatedReality;
    }

    /* renamed from: component8, reason: from getter */
    public final float component8() {
        return this.resourceMultiplier;
    }

    /* renamed from: component9, reason: from getter */
    public final String component9() {
        return this.specialNodeName;
    }

    public final StarSystem copy(String id, String name, float sectorX, float sectorY, String factionAffiliation, String description, ParallelEarth associatedReality, float resourceMultiplier, String specialNodeName, boolean isDiscovered) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(factionAffiliation, "factionAffiliation");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(associatedReality, "associatedReality");
        Intrinsics.checkNotNullParameter(specialNodeName, "specialNodeName");
        return new StarSystem(id, name, sectorX, sectorY, factionAffiliation, description, associatedReality, resourceMultiplier, specialNodeName, isDiscovered);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StarSystem)) {
            return false;
        }
        StarSystem starSystem = (StarSystem) other;
        return Intrinsics.areEqual(this.id, starSystem.id) && Intrinsics.areEqual(this.name, starSystem.name) && Float.compare(this.sectorX, starSystem.sectorX) == 0 && Float.compare(this.sectorY, starSystem.sectorY) == 0 && Intrinsics.areEqual(this.factionAffiliation, starSystem.factionAffiliation) && Intrinsics.areEqual(this.description, starSystem.description) && this.associatedReality == starSystem.associatedReality && Float.compare(this.resourceMultiplier, starSystem.resourceMultiplier) == 0 && Intrinsics.areEqual(this.specialNodeName, starSystem.specialNodeName) && this.isDiscovered == starSystem.isDiscovered;
    }

    public int hashCode() {
        return (((((((((((((((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + Float.hashCode(this.sectorX)) * 31) + Float.hashCode(this.sectorY)) * 31) + this.factionAffiliation.hashCode()) * 31) + this.description.hashCode()) * 31) + this.associatedReality.hashCode()) * 31) + Float.hashCode(this.resourceMultiplier)) * 31) + this.specialNodeName.hashCode()) * 31) + Boolean.hashCode(this.isDiscovered);
    }

    public String toString() {
        return "StarSystem(id=" + this.id + ", name=" + this.name + ", sectorX=" + this.sectorX + ", sectorY=" + this.sectorY + ", factionAffiliation=" + this.factionAffiliation + ", description=" + this.description + ", associatedReality=" + this.associatedReality + ", resourceMultiplier=" + this.resourceMultiplier + ", specialNodeName=" + this.specialNodeName + ", isDiscovered=" + this.isDiscovered + ")";
    }

    public StarSystem(String id, String name, float sectorX, float sectorY, String factionAffiliation, String description, ParallelEarth associatedReality, float resourceMultiplier, String specialNodeName, boolean isDiscovered) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(factionAffiliation, "factionAffiliation");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(associatedReality, "associatedReality");
        Intrinsics.checkNotNullParameter(specialNodeName, "specialNodeName");
        this.id = id;
        this.name = name;
        this.sectorX = sectorX;
        this.sectorY = sectorY;
        this.factionAffiliation = factionAffiliation;
        this.description = description;
        this.associatedReality = associatedReality;
        this.resourceMultiplier = resourceMultiplier;
        this.specialNodeName = specialNodeName;
        this.isDiscovered = isDiscovered;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ StarSystem(java.lang.String r13, java.lang.String r14, float r15, float r16, java.lang.String r17, java.lang.String r18, com.example.game.models.ParallelEarth r19, float r20, java.lang.String r21, boolean r22, int r23, kotlin.jvm.internal.DefaultConstructorMarker r24) {
        /*
            r12 = this;
            r0 = r23
            r0 = r0 & 512(0x200, float:7.17E-43)
            if (r0 == 0) goto L9
            r0 = 0
            r11 = r0
            goto Lb
        L9:
            r11 = r22
        Lb:
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r15
            r5 = r16
            r6 = r17
            r7 = r18
            r8 = r19
            r9 = r20
            r10 = r21
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.models.StarSystem.<init>(java.lang.String, java.lang.String, float, float, java.lang.String, java.lang.String, com.example.game.models.ParallelEarth, float, java.lang.String, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final float getSectorX() {
        return this.sectorX;
    }

    public final float getSectorY() {
        return this.sectorY;
    }

    public final String getFactionAffiliation() {
        return this.factionAffiliation;
    }

    public final String getDescription() {
        return this.description;
    }

    public final ParallelEarth getAssociatedReality() {
        return this.associatedReality;
    }

    public final float getResourceMultiplier() {
        return this.resourceMultiplier;
    }

    public final String getSpecialNodeName() {
        return this.specialNodeName;
    }

    public final boolean isDiscovered() {
        return this.isDiscovered;
    }
}
