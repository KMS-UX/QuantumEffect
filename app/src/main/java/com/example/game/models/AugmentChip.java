package com.example.game.models;

import com.squareup.moshi.JsonClass;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameModels.kt */
@JsonClass(generateAdapter = true)
/* loaded from: classes4.dex */
public final /* data */ class AugmentChip {
    public static final int $stable = 0;
    private final int atkBonus;
    private final int defBonus;
    private final int hpBonus;
    private final int lckBonus;
    private final int magBonus;
    private final String modifierDesc;
    private final int mpBonus;
    private final String name;
    private final AugmentSlot slot;
    private final int spdBonus;

    public static /* synthetic */ AugmentChip copy$default(AugmentChip augmentChip, String str, AugmentSlot augmentSlot, String str2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, Object obj) {
        if ((i8 & 1) != 0) {
            str = augmentChip.name;
        }
        if ((i8 & 2) != 0) {
            augmentSlot = augmentChip.slot;
        }
        if ((i8 & 4) != 0) {
            str2 = augmentChip.modifierDesc;
        }
        if ((i8 & 8) != 0) {
            i = augmentChip.hpBonus;
        }
        if ((i8 & 16) != 0) {
            i2 = augmentChip.mpBonus;
        }
        if ((i8 & 32) != 0) {
            i3 = augmentChip.atkBonus;
        }
        if ((i8 & 64) != 0) {
            i4 = augmentChip.defBonus;
        }
        if ((i8 & 128) != 0) {
            i5 = augmentChip.magBonus;
        }
        if ((i8 & 256) != 0) {
            i6 = augmentChip.spdBonus;
        }
        if ((i8 & 512) != 0) {
            i7 = augmentChip.lckBonus;
        }
        int i9 = i6;
        int i10 = i7;
        int i11 = i4;
        int i12 = i5;
        int i13 = i2;
        int i14 = i3;
        return augmentChip.copy(str, augmentSlot, str2, i, i13, i14, i11, i12, i9, i10);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.name;
    }

    /* renamed from: component10, reason: from getter */
    public final int component10() {
        return this.lckBonus;
    }

    /* renamed from: component2, reason: from getter */
    public final AugmentSlot component2() {
        return this.slot;
    }

    /* renamed from: component3, reason: from getter */
    public final String component3() {
        return this.modifierDesc;
    }

    /* renamed from: component4, reason: from getter */
    public final int component4() {
        return this.hpBonus;
    }

    /* renamed from: component5, reason: from getter */
    public final int component5() {
        return this.mpBonus;
    }

    /* renamed from: component6, reason: from getter */
    public final int component6() {
        return this.atkBonus;
    }

    /* renamed from: component7, reason: from getter */
    public final int component7() {
        return this.defBonus;
    }

    /* renamed from: component8, reason: from getter */
    public final int component8() {
        return this.magBonus;
    }

    /* renamed from: component9, reason: from getter */
    public final int component9() {
        return this.spdBonus;
    }

    public final AugmentChip copy(String name, AugmentSlot slot, String modifierDesc, int hpBonus, int mpBonus, int atkBonus, int defBonus, int magBonus, int spdBonus, int lckBonus) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(slot, "slot");
        Intrinsics.checkNotNullParameter(modifierDesc, "modifierDesc");
        return new AugmentChip(name, slot, modifierDesc, hpBonus, mpBonus, atkBonus, defBonus, magBonus, spdBonus, lckBonus);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AugmentChip)) {
            return false;
        }
        AugmentChip augmentChip = (AugmentChip) other;
        return Intrinsics.areEqual(this.name, augmentChip.name) && this.slot == augmentChip.slot && Intrinsics.areEqual(this.modifierDesc, augmentChip.modifierDesc) && this.hpBonus == augmentChip.hpBonus && this.mpBonus == augmentChip.mpBonus && this.atkBonus == augmentChip.atkBonus && this.defBonus == augmentChip.defBonus && this.magBonus == augmentChip.magBonus && this.spdBonus == augmentChip.spdBonus && this.lckBonus == augmentChip.lckBonus;
    }

    public int hashCode() {
        return (((((((((((((((((this.name.hashCode() * 31) + this.slot.hashCode()) * 31) + this.modifierDesc.hashCode()) * 31) + Integer.hashCode(this.hpBonus)) * 31) + Integer.hashCode(this.mpBonus)) * 31) + Integer.hashCode(this.atkBonus)) * 31) + Integer.hashCode(this.defBonus)) * 31) + Integer.hashCode(this.magBonus)) * 31) + Integer.hashCode(this.spdBonus)) * 31) + Integer.hashCode(this.lckBonus);
    }

    public String toString() {
        return "AugmentChip(name=" + this.name + ", slot=" + this.slot + ", modifierDesc=" + this.modifierDesc + ", hpBonus=" + this.hpBonus + ", mpBonus=" + this.mpBonus + ", atkBonus=" + this.atkBonus + ", defBonus=" + this.defBonus + ", magBonus=" + this.magBonus + ", spdBonus=" + this.spdBonus + ", lckBonus=" + this.lckBonus + ")";
    }

    public AugmentChip(String name, AugmentSlot slot, String modifierDesc, int hpBonus, int mpBonus, int atkBonus, int defBonus, int magBonus, int spdBonus, int lckBonus) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(slot, "slot");
        Intrinsics.checkNotNullParameter(modifierDesc, "modifierDesc");
        this.name = name;
        this.slot = slot;
        this.modifierDesc = modifierDesc;
        this.hpBonus = hpBonus;
        this.mpBonus = mpBonus;
        this.atkBonus = atkBonus;
        this.defBonus = defBonus;
        this.magBonus = magBonus;
        this.spdBonus = spdBonus;
        this.lckBonus = lckBonus;
    }

    public /* synthetic */ AugmentChip(String str, AugmentSlot augmentSlot, String str2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, augmentSlot, str2, (i8 & 8) != 0 ? 0 : i, (i8 & 16) != 0 ? 0 : i2, (i8 & 32) != 0 ? 0 : i3, (i8 & 64) != 0 ? 0 : i4, (i8 & 128) != 0 ? 0 : i5, (i8 & 256) != 0 ? 0 : i6, (i8 & 512) != 0 ? 0 : i7);
    }

    public final String getName() {
        return this.name;
    }

    public final AugmentSlot getSlot() {
        return this.slot;
    }

    public final String getModifierDesc() {
        return this.modifierDesc;
    }

    public final int getHpBonus() {
        return this.hpBonus;
    }

    public final int getMpBonus() {
        return this.mpBonus;
    }

    public final int getAtkBonus() {
        return this.atkBonus;
    }

    public final int getDefBonus() {
        return this.defBonus;
    }

    public final int getMagBonus() {
        return this.magBonus;
    }

    public final int getSpdBonus() {
        return this.spdBonus;
    }

    public final int getLckBonus() {
        return this.lckBonus;
    }
}
