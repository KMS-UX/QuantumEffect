package com.example.game.models;

import com.squareup.moshi.JsonClass;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameModels.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes4.dex */
public final /* data */ class CompanionRecord {
    public static final int $stable = 0;
    private final String accessoryEquipped;
    private final String activeSkill;
    private final String armorEquipped;
    private final int bondPoints;
    private final String faction;
    private final boolean isRecruited;
    private final int level;
    private final String moduleEquipped;
    private final String name;
    private final String portraitSymbol;
    private final String role;
    private final String skillDesc;
    private final String weaponEquipped;

    public static /* synthetic */ CompanionRecord copy$default(CompanionRecord companionRecord, String str, String str2, String str3, int i, boolean z, String str4, String str5, String str6, int i2, String str7, String str8, String str9, String str10, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = companionRecord.name;
        }
        return companionRecord.copy(str, (i3 & 2) != 0 ? companionRecord.role : str2, (i3 & 4) != 0 ? companionRecord.faction : str3, (i3 & 8) != 0 ? companionRecord.bondPoints : i, (i3 & 16) != 0 ? companionRecord.isRecruited : z, (i3 & 32) != 0 ? companionRecord.activeSkill : str4, (i3 & 64) != 0 ? companionRecord.skillDesc : str5, (i3 & 128) != 0 ? companionRecord.portraitSymbol : str6, (i3 & 256) != 0 ? companionRecord.level : i2, (i3 & 512) != 0 ? companionRecord.weaponEquipped : str7, (i3 & 1024) != 0 ? companionRecord.armorEquipped : str8, (i3 & 2048) != 0 ? companionRecord.accessoryEquipped : str9, (i3 & 4096) != 0 ? companionRecord.moduleEquipped : str10);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.name;
    }

    /* renamed from: component10, reason: from getter */
    public final String component10() {
        return this.weaponEquipped;
    }

    /* renamed from: component11, reason: from getter */
    public final String component11() {
        return this.armorEquipped;
    }

    /* renamed from: component12, reason: from getter */
    public final String component12() {
        return this.accessoryEquipped;
    }

    /* renamed from: component13, reason: from getter */
    public final String component13() {
        return this.moduleEquipped;
    }

    /* renamed from: component2, reason: from getter */
    public final String component2() {
        return this.role;
    }

    /* renamed from: component3, reason: from getter */
    public final String component3() {
        return this.faction;
    }

    /* renamed from: component4, reason: from getter */
    public final int component4() {
        return this.bondPoints;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean component5() {
        return this.isRecruited;
    }

    /* renamed from: component6, reason: from getter */
    public final String component6() {
        return this.activeSkill;
    }

    /* renamed from: component7, reason: from getter */
    public final String component7() {
        return this.skillDesc;
    }

    /* renamed from: component8, reason: from getter */
    public final String component8() {
        return this.portraitSymbol;
    }

    /* renamed from: component9, reason: from getter */
    public final int component9() {
        return this.level;
    }

    public final CompanionRecord copy(String name, String role, String faction, int bondPoints, boolean isRecruited, String activeSkill, String skillDesc, String portraitSymbol, int level, String weaponEquipped, String armorEquipped, String accessoryEquipped, String moduleEquipped) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(role, "role");
        Intrinsics.checkNotNullParameter(faction, "faction");
        Intrinsics.checkNotNullParameter(activeSkill, "activeSkill");
        Intrinsics.checkNotNullParameter(skillDesc, "skillDesc");
        Intrinsics.checkNotNullParameter(portraitSymbol, "portraitSymbol");
        return new CompanionRecord(name, role, faction, bondPoints, isRecruited, activeSkill, skillDesc, portraitSymbol, level, weaponEquipped, armorEquipped, accessoryEquipped, moduleEquipped);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CompanionRecord)) {
            return false;
        }
        CompanionRecord companionRecord = (CompanionRecord) other;
        return Intrinsics.areEqual(this.name, companionRecord.name) && Intrinsics.areEqual(this.role, companionRecord.role) && Intrinsics.areEqual(this.faction, companionRecord.faction) && this.bondPoints == companionRecord.bondPoints && this.isRecruited == companionRecord.isRecruited && Intrinsics.areEqual(this.activeSkill, companionRecord.activeSkill) && Intrinsics.areEqual(this.skillDesc, companionRecord.skillDesc) && Intrinsics.areEqual(this.portraitSymbol, companionRecord.portraitSymbol) && this.level == companionRecord.level && Intrinsics.areEqual(this.weaponEquipped, companionRecord.weaponEquipped) && Intrinsics.areEqual(this.armorEquipped, companionRecord.armorEquipped) && Intrinsics.areEqual(this.accessoryEquipped, companionRecord.accessoryEquipped) && Intrinsics.areEqual(this.moduleEquipped, companionRecord.moduleEquipped);
    }

    public int hashCode() {
        return (((((((((((((((((((((((this.name.hashCode() * 31) + this.role.hashCode()) * 31) + this.faction.hashCode()) * 31) + Integer.hashCode(this.bondPoints)) * 31) + Boolean.hashCode(this.isRecruited)) * 31) + this.activeSkill.hashCode()) * 31) + this.skillDesc.hashCode()) * 31) + this.portraitSymbol.hashCode()) * 31) + Integer.hashCode(this.level)) * 31) + (this.weaponEquipped == null ? 0 : this.weaponEquipped.hashCode())) * 31) + (this.armorEquipped == null ? 0 : this.armorEquipped.hashCode())) * 31) + (this.accessoryEquipped == null ? 0 : this.accessoryEquipped.hashCode())) * 31) + (this.moduleEquipped != null ? this.moduleEquipped.hashCode() : 0);
    }

    public String toString() {
        return "CompanionRecord(name=" + this.name + ", role=" + this.role + ", faction=" + this.faction + ", bondPoints=" + this.bondPoints + ", isRecruited=" + this.isRecruited + ", activeSkill=" + this.activeSkill + ", skillDesc=" + this.skillDesc + ", portraitSymbol=" + this.portraitSymbol + ", level=" + this.level + ", weaponEquipped=" + this.weaponEquipped + ", armorEquipped=" + this.armorEquipped + ", accessoryEquipped=" + this.accessoryEquipped + ", moduleEquipped=" + this.moduleEquipped + ")";
    }

    public CompanionRecord(String name, String role, String faction, int bondPoints, boolean isRecruited, String activeSkill, String skillDesc, String portraitSymbol, int level, String weaponEquipped, String armorEquipped, String accessoryEquipped, String moduleEquipped) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(role, "role");
        Intrinsics.checkNotNullParameter(faction, "faction");
        Intrinsics.checkNotNullParameter(activeSkill, "activeSkill");
        Intrinsics.checkNotNullParameter(skillDesc, "skillDesc");
        Intrinsics.checkNotNullParameter(portraitSymbol, "portraitSymbol");
        this.name = name;
        this.role = role;
        this.faction = faction;
        this.bondPoints = bondPoints;
        this.isRecruited = isRecruited;
        this.activeSkill = activeSkill;
        this.skillDesc = skillDesc;
        this.portraitSymbol = portraitSymbol;
        this.level = level;
        this.weaponEquipped = weaponEquipped;
        this.armorEquipped = armorEquipped;
        this.accessoryEquipped = accessoryEquipped;
        this.moduleEquipped = moduleEquipped;
    }

    public /* synthetic */ CompanionRecord(String str, String str2, String str3, int i, boolean z, String str4, String str5, String str6, int i2, String str7, String str8, String str9, String str10, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, (i3 & 8) != 0 ? 0 : i, (i3 & 16) != 0 ? false : z, str4, str5, str6, (i3 & 256) != 0 ? 1 : i2, (i3 & 512) != 0 ? "None" : str7, (i3 & 1024) != 0 ? "None" : str8, (i3 & 2048) != 0 ? "None" : str9, (i3 & 4096) != 0 ? "None" : str10);
    }

    public final String getName() {
        return this.name;
    }

    public final String getRole() {
        return this.role;
    }

    public final String getFaction() {
        return this.faction;
    }

    public final int getBondPoints() {
        return this.bondPoints;
    }

    public final boolean isRecruited() {
        return this.isRecruited;
    }

    public final String getActiveSkill() {
        return this.activeSkill;
    }

    public final String getSkillDesc() {
        return this.skillDesc;
    }

    public final String getPortraitSymbol() {
        return this.portraitSymbol;
    }

    public final int getLevel() {
        return this.level;
    }

    public final String getWeaponEquipped() {
        return this.weaponEquipped;
    }

    public final String getArmorEquipped() {
        return this.armorEquipped;
    }

    public final String getAccessoryEquipped() {
        return this.accessoryEquipped;
    }

    public final String getModuleEquipped() {
        return this.moduleEquipped;
    }
}
