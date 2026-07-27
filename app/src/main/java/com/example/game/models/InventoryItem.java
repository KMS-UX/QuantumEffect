package com.example.game.models;

import com.squareup.moshi.JsonClass;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameModels.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes4.dex */
public final /* data */ class InventoryItem {
    public static final int $stable = 0;
    private final int atkBonus;
    private final String category;
    private final int defBonus;
    private final String description;
    private final int hpBonus;
    private final String iconSymbol;
    private final String id;
    private final boolean isConsumable;
    private final boolean isEquippable;
    private final boolean isEquipped;
    private final int mpBonus;
    private final String name;
    private final int quantity;
    private final String statModifierDesc;

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.id;
    }

    /* renamed from: component10, reason: from getter */
    public final int component10() {
        return this.atkBonus;
    }

    /* renamed from: component11, reason: from getter */
    public final int component11() {
        return this.defBonus;
    }

    /* renamed from: component12, reason: from getter */
    public final boolean component12() {
        return this.isConsumable;
    }

    /* renamed from: component13, reason: from getter */
    public final boolean component13() {
        return this.isEquippable;
    }

    /* renamed from: component14, reason: from getter */
    public final boolean component14() {
        return this.isEquipped;
    }

    /* renamed from: component2, reason: from getter */
    public final String component2() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final String component3() {
        return this.category;
    }

    /* renamed from: component4, reason: from getter */
    public final String component4() {
        return this.iconSymbol;
    }

    /* renamed from: component5, reason: from getter */
    public final int component5() {
        return this.quantity;
    }

    /* renamed from: component6, reason: from getter */
    public final String component6() {
        return this.description;
    }

    /* renamed from: component7, reason: from getter */
    public final String component7() {
        return this.statModifierDesc;
    }

    /* renamed from: component8, reason: from getter */
    public final int component8() {
        return this.hpBonus;
    }

    /* renamed from: component9, reason: from getter */
    public final int component9() {
        return this.mpBonus;
    }

    public final InventoryItem copy(String id, String name, String category, String iconSymbol, int quantity, String description, String statModifierDesc, int hpBonus, int mpBonus, int atkBonus, int defBonus, boolean isConsumable, boolean isEquippable, boolean isEquipped) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(iconSymbol, "iconSymbol");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(statModifierDesc, "statModifierDesc");
        return new InventoryItem(id, name, category, iconSymbol, quantity, description, statModifierDesc, hpBonus, mpBonus, atkBonus, defBonus, isConsumable, isEquippable, isEquipped);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InventoryItem)) {
            return false;
        }
        InventoryItem inventoryItem = (InventoryItem) other;
        return Intrinsics.areEqual(this.id, inventoryItem.id) && Intrinsics.areEqual(this.name, inventoryItem.name) && Intrinsics.areEqual(this.category, inventoryItem.category) && Intrinsics.areEqual(this.iconSymbol, inventoryItem.iconSymbol) && this.quantity == inventoryItem.quantity && Intrinsics.areEqual(this.description, inventoryItem.description) && Intrinsics.areEqual(this.statModifierDesc, inventoryItem.statModifierDesc) && this.hpBonus == inventoryItem.hpBonus && this.mpBonus == inventoryItem.mpBonus && this.atkBonus == inventoryItem.atkBonus && this.defBonus == inventoryItem.defBonus && this.isConsumable == inventoryItem.isConsumable && this.isEquippable == inventoryItem.isEquippable && this.isEquipped == inventoryItem.isEquipped;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.category.hashCode()) * 31) + this.iconSymbol.hashCode()) * 31) + Integer.hashCode(this.quantity)) * 31) + this.description.hashCode()) * 31) + this.statModifierDesc.hashCode()) * 31) + Integer.hashCode(this.hpBonus)) * 31) + Integer.hashCode(this.mpBonus)) * 31) + Integer.hashCode(this.atkBonus)) * 31) + Integer.hashCode(this.defBonus)) * 31) + Boolean.hashCode(this.isConsumable)) * 31) + Boolean.hashCode(this.isEquippable)) * 31) + Boolean.hashCode(this.isEquipped);
    }

    public String toString() {
        return "InventoryItem(id=" + this.id + ", name=" + this.name + ", category=" + this.category + ", iconSymbol=" + this.iconSymbol + ", quantity=" + this.quantity + ", description=" + this.description + ", statModifierDesc=" + this.statModifierDesc + ", hpBonus=" + this.hpBonus + ", mpBonus=" + this.mpBonus + ", atkBonus=" + this.atkBonus + ", defBonus=" + this.defBonus + ", isConsumable=" + this.isConsumable + ", isEquippable=" + this.isEquippable + ", isEquipped=" + this.isEquipped + ")";
    }

    public InventoryItem(String id, String name, String category, String iconSymbol, int quantity, String description, String statModifierDesc, int hpBonus, int mpBonus, int atkBonus, int defBonus, boolean isConsumable, boolean isEquippable, boolean isEquipped) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(iconSymbol, "iconSymbol");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(statModifierDesc, "statModifierDesc");
        this.id = id;
        this.name = name;
        this.category = category;
        this.iconSymbol = iconSymbol;
        this.quantity = quantity;
        this.description = description;
        this.statModifierDesc = statModifierDesc;
        this.hpBonus = hpBonus;
        this.mpBonus = mpBonus;
        this.atkBonus = atkBonus;
        this.defBonus = defBonus;
        this.isConsumable = isConsumable;
        this.isEquippable = isEquippable;
        this.isEquipped = isEquipped;
    }

    public /* synthetic */ InventoryItem(String str, String str2, String str3, String str4, int i, String str5, String str6, int i2, int i3, int i4, int i5, boolean z, boolean z2, boolean z3, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, i, str5, (i6 & 64) != 0 ? "" : str6, (i6 & 128) != 0 ? 0 : i2, (i6 & 256) != 0 ? 0 : i3, (i6 & 512) != 0 ? 0 : i4, (i6 & 1024) != 0 ? 0 : i5, (i6 & 2048) != 0 ? false : z, (i6 & 4096) != 0 ? false : z2, (i6 & 8192) != 0 ? false : z3);
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getCategory() {
        return this.category;
    }

    public final String getIconSymbol() {
        return this.iconSymbol;
    }

    public final int getQuantity() {
        return this.quantity;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getStatModifierDesc() {
        return this.statModifierDesc;
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

    public final boolean isConsumable() {
        return this.isConsumable;
    }

    public final boolean isEquippable() {
        return this.isEquippable;
    }

    public final boolean isEquipped() {
        return this.isEquipped;
    }
}
