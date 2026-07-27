package com.example.game.models;


import kotlin.jvm.internal.Intrinsics;

/* compiled from: StarshipModels.kt */

/* loaded from: classes4.dex */
public final /* data */ class ShipUpgrade {
    public static final int $stable = 0;
    private final ShipSystem affectedSystem;
    private final float bonusMultiplier;
    private final int costCredits;
    private final int costNanites;
    private final String description;
    private final String id;
    private final boolean isOwned;
    private final String name;

    public static /* synthetic */ ShipUpgrade copy$default(ShipUpgrade shipUpgrade, String str, String str2, String str3, ShipSystem shipSystem, int i, int i2, float f, boolean z, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = shipUpgrade.id;
        }
        if ((i3 & 2) != 0) {
            str2 = shipUpgrade.name;
        }
        if ((i3 & 4) != 0) {
            str3 = shipUpgrade.description;
        }
        if ((i3 & 8) != 0) {
            shipSystem = shipUpgrade.affectedSystem;
        }
        if ((i3 & 16) != 0) {
            i = shipUpgrade.costCredits;
        }
        if ((i3 & 32) != 0) {
            i2 = shipUpgrade.costNanites;
        }
        if ((i3 & 64) != 0) {
            f = shipUpgrade.bonusMultiplier;
        }
        if ((i3 & 128) != 0) {
            z = shipUpgrade.isOwned;
        }
        float f2 = f;
        boolean z2 = z;
        int i4 = i;
        int i5 = i2;
        return shipUpgrade.copy(str, str2, str3, shipSystem, i4, i5, f2, z2);
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
        return this.description;
    }

    /* renamed from: component4, reason: from getter */
    public final ShipSystem component4() {
        return this.affectedSystem;
    }

    /* renamed from: component5, reason: from getter */
    public final int component5() {
        return this.costCredits;
    }

    /* renamed from: component6, reason: from getter */
    public final int component6() {
        return this.costNanites;
    }

    /* renamed from: component7, reason: from getter */
    public final float component7() {
        return this.bonusMultiplier;
    }

    /* renamed from: component8, reason: from getter */
    public final boolean component8() {
        return this.isOwned;
    }

    public final ShipUpgrade copy(String id, String name, String description, ShipSystem affectedSystem, int costCredits, int costNanites, float bonusMultiplier, boolean isOwned) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(affectedSystem, "affectedSystem");
        return new ShipUpgrade(id, name, description, affectedSystem, costCredits, costNanites, bonusMultiplier, isOwned);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ShipUpgrade)) {
            return false;
        }
        ShipUpgrade shipUpgrade = (ShipUpgrade) other;
        return Intrinsics.areEqual(this.id, shipUpgrade.id) && Intrinsics.areEqual(this.name, shipUpgrade.name) && Intrinsics.areEqual(this.description, shipUpgrade.description) && this.affectedSystem == shipUpgrade.affectedSystem && this.costCredits == shipUpgrade.costCredits && this.costNanites == shipUpgrade.costNanites && Float.compare(this.bonusMultiplier, shipUpgrade.bonusMultiplier) == 0 && this.isOwned == shipUpgrade.isOwned;
    }

    public int hashCode() {
        return (((((((((((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.description.hashCode()) * 31) + this.affectedSystem.hashCode()) * 31) + Integer.hashCode(this.costCredits)) * 31) + Integer.hashCode(this.costNanites)) * 31) + Float.hashCode(this.bonusMultiplier)) * 31) + Boolean.hashCode(this.isOwned);
    }

    public String toString() {
        return "ShipUpgrade(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", affectedSystem=" + this.affectedSystem + ", costCredits=" + this.costCredits + ", costNanites=" + this.costNanites + ", bonusMultiplier=" + this.bonusMultiplier + ", isOwned=" + this.isOwned + ")";
    }

    public ShipUpgrade(String id, String name, String description, ShipSystem affectedSystem, int costCredits, int costNanites, float bonusMultiplier, boolean isOwned) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(affectedSystem, "affectedSystem");
        this.id = id;
        this.name = name;
        this.description = description;
        this.affectedSystem = affectedSystem;
        this.costCredits = costCredits;
        this.costNanites = costNanites;
        this.bonusMultiplier = bonusMultiplier;
        this.isOwned = isOwned;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ ShipUpgrade(java.lang.String r11, java.lang.String r12, java.lang.String r13, com.example.game.models.ShipSystem r14, int r15, int r16, float r17, boolean r18, int r19, kotlin.jvm.internal.DefaultConstructorMarker r20) {
        /*
            r10 = this;
            r0 = r19
            r0 = r0 & 128(0x80, float:1.8E-43)
            if (r0 == 0) goto L9
            r0 = 0
            r9 = r0
            goto Lb
        L9:
            r9 = r18
        Lb:
            r1 = r10
            r2 = r11
            r3 = r12
            r4 = r13
            r5 = r14
            r6 = r15
            r7 = r16
            r8 = r17
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.models.ShipUpgrade.<init>(java.lang.String, java.lang.String, java.lang.String, com.example.game.models.ShipSystem, int, int, float, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getDescription() {
        return this.description;
    }

    public final ShipSystem getAffectedSystem() {
        return this.affectedSystem;
    }

    public final int getCostCredits() {
        return this.costCredits;
    }

    public final int getCostNanites() {
        return this.costNanites;
    }

    public final float getBonusMultiplier() {
        return this.bonusMultiplier;
    }

    public final boolean isOwned() {
        return this.isOwned;
    }
}
