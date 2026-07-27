package com.example.game.db;


import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;

/* compiled from: GameDatabase.kt */

/* loaded from: classes5.dex */
public final /* data */ class GameProgress {
    public static final int $stable = 0;
    private final String companionsJson;
    private final int credits;
    private final String currentOutfit;
    private final String currentWeapon;
    private final String deployedStructuresJson;
    private final String factionReputationsJson;
    private final int health;
    private final int id;
    private final String installedAugmentsJson;
    private final String installedModChipsJson;
    private final String inventoryJson;
    private final int level;
    private final String mapStateJson;
    private final int maxHealth;
    private final int maxMp;
    private final int mp;
    private final int nanites;
    private final String playerName;
    private final String questsJson;
    private final int xp;

    public static /* synthetic */ GameProgress copy$default(GameProgress gameProgress, int i, String str, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, int i10, Object obj) {
        String str12;
        String str13;
        int i11 = (i10 & 1) != 0 ? gameProgress.id : i;
        String str14 = (i10 & 2) != 0 ? gameProgress.playerName : str;
        int i12 = (i10 & 4) != 0 ? gameProgress.level : i2;
        int i13 = (i10 & 8) != 0 ? gameProgress.xp : i3;
        int i14 = (i10 & 16) != 0 ? gameProgress.credits : i4;
        int i15 = (i10 & 32) != 0 ? gameProgress.nanites : i5;
        int i16 = (i10 & 64) != 0 ? gameProgress.health : i6;
        int i17 = (i10 & 128) != 0 ? gameProgress.maxHealth : i7;
        int i18 = (i10 & 256) != 0 ? gameProgress.mp : i8;
        int i19 = (i10 & 512) != 0 ? gameProgress.maxMp : i9;
        String str15 = (i10 & 1024) != 0 ? gameProgress.currentWeapon : str2;
        String str16 = (i10 & 2048) != 0 ? gameProgress.currentOutfit : str3;
        String str17 = (i10 & 4096) != 0 ? gameProgress.installedAugmentsJson : str4;
        String str18 = (i10 & 8192) != 0 ? gameProgress.installedModChipsJson : str5;
        int i20 = i11;
        String str19 = (i10 & 16384) != 0 ? gameProgress.factionReputationsJson : str6;
        String str20 = (i10 & 32768) != 0 ? gameProgress.companionsJson : str7;
        String str21 = (i10 & 65536) != 0 ? gameProgress.mapStateJson : str8;
        String str22 = (i10 & 131072) != 0 ? gameProgress.deployedStructuresJson : str9;
        String str23 = (i10 & 262144) != 0 ? gameProgress.inventoryJson : str10;
        if ((i10 & 524288) != 0) {
            str13 = str23;
            str12 = gameProgress.questsJson;
        } else {
            str12 = str11;
            str13 = str23;
        }
        return gameProgress.copy(i20, str14, i12, i13, i14, i15, i16, i17, i18, i19, str15, str16, str17, str18, str19, str20, str21, str22, str13, str12);
    }

    /* renamed from: component1, reason: from getter */
    public final int component1() {
        return this.id;
    }

    /* renamed from: component10, reason: from getter */
    public final int component10() {
        return this.maxMp;
    }

    /* renamed from: component11, reason: from getter */
    public final String component11() {
        return this.currentWeapon;
    }

    /* renamed from: component12, reason: from getter */
    public final String component12() {
        return this.currentOutfit;
    }

    /* renamed from: component13, reason: from getter */
    public final String component13() {
        return this.installedAugmentsJson;
    }

    /* renamed from: component14, reason: from getter */
    public final String component14() {
        return this.installedModChipsJson;
    }

    /* renamed from: component15, reason: from getter */
    public final String component15() {
        return this.factionReputationsJson;
    }

    /* renamed from: component16, reason: from getter */
    public final String component16() {
        return this.companionsJson;
    }

    /* renamed from: component17, reason: from getter */
    public final String component17() {
        return this.mapStateJson;
    }

    /* renamed from: component18, reason: from getter */
    public final String component18() {
        return this.deployedStructuresJson;
    }

    /* renamed from: component19, reason: from getter */
    public final String component19() {
        return this.inventoryJson;
    }

    /* renamed from: component2, reason: from getter */
    public final String component2() {
        return this.playerName;
    }

    /* renamed from: component20, reason: from getter */
    public final String component20() {
        return this.questsJson;
    }

    /* renamed from: component3, reason: from getter */
    public final int component3() {
        return this.level;
    }

    /* renamed from: component4, reason: from getter */
    public final int component4() {
        return this.xp;
    }

    /* renamed from: component5, reason: from getter */
    public final int component5() {
        return this.credits;
    }

    /* renamed from: component6, reason: from getter */
    public final int component6() {
        return this.nanites;
    }

    /* renamed from: component7, reason: from getter */
    public final int component7() {
        return this.health;
    }

    /* renamed from: component8, reason: from getter */
    public final int component8() {
        return this.maxHealth;
    }

    /* renamed from: component9, reason: from getter */
    public final int component9() {
        return this.mp;
    }

    public final GameProgress copy(int id, String playerName, int level, int xp, int credits, int nanites, int health, int maxHealth, int mp, int maxMp, String currentWeapon, String currentOutfit, String installedAugmentsJson, String installedModChipsJson, String factionReputationsJson, String companionsJson, String mapStateJson, String deployedStructuresJson, String inventoryJson, String questsJson) {
        Intrinsics.checkNotNullParameter(playerName, "playerName");
        Intrinsics.checkNotNullParameter(currentWeapon, "currentWeapon");
        Intrinsics.checkNotNullParameter(currentOutfit, "currentOutfit");
        Intrinsics.checkNotNullParameter(installedAugmentsJson, "installedAugmentsJson");
        Intrinsics.checkNotNullParameter(installedModChipsJson, "installedModChipsJson");
        Intrinsics.checkNotNullParameter(factionReputationsJson, "factionReputationsJson");
        Intrinsics.checkNotNullParameter(companionsJson, "companionsJson");
        Intrinsics.checkNotNullParameter(mapStateJson, "mapStateJson");
        Intrinsics.checkNotNullParameter(deployedStructuresJson, "deployedStructuresJson");
        Intrinsics.checkNotNullParameter(inventoryJson, "inventoryJson");
        Intrinsics.checkNotNullParameter(questsJson, "questsJson");
        return new GameProgress(id, playerName, level, xp, credits, nanites, health, maxHealth, mp, maxMp, currentWeapon, currentOutfit, installedAugmentsJson, installedModChipsJson, factionReputationsJson, companionsJson, mapStateJson, deployedStructuresJson, inventoryJson, questsJson);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GameProgress)) {
            return false;
        }
        GameProgress gameProgress = (GameProgress) other;
        return this.id == gameProgress.id && Intrinsics.areEqual(this.playerName, gameProgress.playerName) && this.level == gameProgress.level && this.xp == gameProgress.xp && this.credits == gameProgress.credits && this.nanites == gameProgress.nanites && this.health == gameProgress.health && this.maxHealth == gameProgress.maxHealth && this.mp == gameProgress.mp && this.maxMp == gameProgress.maxMp && Intrinsics.areEqual(this.currentWeapon, gameProgress.currentWeapon) && Intrinsics.areEqual(this.currentOutfit, gameProgress.currentOutfit) && Intrinsics.areEqual(this.installedAugmentsJson, gameProgress.installedAugmentsJson) && Intrinsics.areEqual(this.installedModChipsJson, gameProgress.installedModChipsJson) && Intrinsics.areEqual(this.factionReputationsJson, gameProgress.factionReputationsJson) && Intrinsics.areEqual(this.companionsJson, gameProgress.companionsJson) && Intrinsics.areEqual(this.mapStateJson, gameProgress.mapStateJson) && Intrinsics.areEqual(this.deployedStructuresJson, gameProgress.deployedStructuresJson) && Intrinsics.areEqual(this.inventoryJson, gameProgress.inventoryJson) && Intrinsics.areEqual(this.questsJson, gameProgress.questsJson);
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((((((((((Integer.hashCode(this.id) * 31) + this.playerName.hashCode()) * 31) + Integer.hashCode(this.level)) * 31) + Integer.hashCode(this.xp)) * 31) + Integer.hashCode(this.credits)) * 31) + Integer.hashCode(this.nanites)) * 31) + Integer.hashCode(this.health)) * 31) + Integer.hashCode(this.maxHealth)) * 31) + Integer.hashCode(this.mp)) * 31) + Integer.hashCode(this.maxMp)) * 31) + this.currentWeapon.hashCode()) * 31) + this.currentOutfit.hashCode()) * 31) + this.installedAugmentsJson.hashCode()) * 31) + this.installedModChipsJson.hashCode()) * 31) + this.factionReputationsJson.hashCode()) * 31) + this.companionsJson.hashCode()) * 31) + this.mapStateJson.hashCode()) * 31) + this.deployedStructuresJson.hashCode()) * 31) + this.inventoryJson.hashCode()) * 31) + this.questsJson.hashCode();
    }

    public String toString() {
        return "GameProgress(id=" + this.id + ", playerName=" + this.playerName + ", level=" + this.level + ", xp=" + this.xp + ", credits=" + this.credits + ", nanites=" + this.nanites + ", health=" + this.health + ", maxHealth=" + this.maxHealth + ", mp=" + this.mp + ", maxMp=" + this.maxMp + ", currentWeapon=" + this.currentWeapon + ", currentOutfit=" + this.currentOutfit + ", installedAugmentsJson=" + this.installedAugmentsJson + ", installedModChipsJson=" + this.installedModChipsJson + ", factionReputationsJson=" + this.factionReputationsJson + ", companionsJson=" + this.companionsJson + ", mapStateJson=" + this.mapStateJson + ", deployedStructuresJson=" + this.deployedStructuresJson + ", inventoryJson=" + this.inventoryJson + ", questsJson=" + this.questsJson + ")";
    }

    public GameProgress(int id, String playerName, int level, int xp, int credits, int nanites, int health, int maxHealth, int mp, int maxMp, String currentWeapon, String currentOutfit, String installedAugmentsJson, String installedModChipsJson, String factionReputationsJson, String companionsJson, String mapStateJson, String deployedStructuresJson, String inventoryJson, String questsJson) {
        Intrinsics.checkNotNullParameter(playerName, "playerName");
        Intrinsics.checkNotNullParameter(currentWeapon, "currentWeapon");
        Intrinsics.checkNotNullParameter(currentOutfit, "currentOutfit");
        Intrinsics.checkNotNullParameter(installedAugmentsJson, "installedAugmentsJson");
        Intrinsics.checkNotNullParameter(installedModChipsJson, "installedModChipsJson");
        Intrinsics.checkNotNullParameter(factionReputationsJson, "factionReputationsJson");
        Intrinsics.checkNotNullParameter(companionsJson, "companionsJson");
        Intrinsics.checkNotNullParameter(mapStateJson, "mapStateJson");
        Intrinsics.checkNotNullParameter(deployedStructuresJson, "deployedStructuresJson");
        Intrinsics.checkNotNullParameter(inventoryJson, "inventoryJson");
        Intrinsics.checkNotNullParameter(questsJson, "questsJson");
        this.id = id;
        this.playerName = playerName;
        this.level = level;
        this.xp = xp;
        this.credits = credits;
        this.nanites = nanites;
        this.health = health;
        this.maxHealth = maxHealth;
        this.mp = mp;
        this.maxMp = maxMp;
        this.currentWeapon = currentWeapon;
        this.currentOutfit = currentOutfit;
        this.installedAugmentsJson = installedAugmentsJson;
        this.installedModChipsJson = installedModChipsJson;
        this.factionReputationsJson = factionReputationsJson;
        this.companionsJson = companionsJson;
        this.mapStateJson = mapStateJson;
        this.deployedStructuresJson = deployedStructuresJson;
        this.inventoryJson = inventoryJson;
        this.questsJson = questsJson;
    }

    public /* synthetic */ GameProgress(int i, String str, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, int i10, DefaultConstructorMarker defaultConstructorMarker) {
        this((i10 & 1) != 0 ? 1 : i, (i10 & 2) != 0 ? "Quantum Baby" : str, (i10 & 4) != 0 ? 1 : i2, (i10 & 8) != 0 ? 0 : i3, (i10 & 16) != 0 ? 1200 : i4, (i10 & 32) != 0 ? 150 : i5, (i10 & 64) != 0 ? 520 : i6, (i10 & 128) != 0 ? 520 : i7, (i10 & 256) != 0 ? 80 : i8, (i10 & 512) != 0 ? 80 : i9, (i10 & 1024) != 0 ? "QUANTUM_BLADE" : str2, (i10 & 2048) != 0 ? "DEFAULT" : str3, str4, str5, str6, str7, str8, (131072 & i10) != 0 ? HttpUrl.PATH_SEGMENT_ENCODE_SET_URI : str9, (262144 & i10) != 0 ? HttpUrl.PATH_SEGMENT_ENCODE_SET_URI : str10, (i10 & 524288) != 0 ? HttpUrl.PATH_SEGMENT_ENCODE_SET_URI : str11);
    }

    public final int getId() {
        return this.id;
    }

    public final String getPlayerName() {
        return this.playerName;
    }

    public final int getLevel() {
        return this.level;
    }

    public final int getXp() {
        return this.xp;
    }

    public final int getCredits() {
        return this.credits;
    }

    public final int getNanites() {
        return this.nanites;
    }

    public final int getHealth() {
        return this.health;
    }

    public final int getMaxHealth() {
        return this.maxHealth;
    }

    public final int getMp() {
        return this.mp;
    }

    public final int getMaxMp() {
        return this.maxMp;
    }

    public final String getCurrentWeapon() {
        return this.currentWeapon;
    }

    public final String getCurrentOutfit() {
        return this.currentOutfit;
    }

    public final String getInstalledAugmentsJson() {
        return this.installedAugmentsJson;
    }

    public final String getInstalledModChipsJson() {
        return this.installedModChipsJson;
    }

    public final String getFactionReputationsJson() {
        return this.factionReputationsJson;
    }

    public final String getCompanionsJson() {
        return this.companionsJson;
    }

    public final String getMapStateJson() {
        return this.mapStateJson;
    }

    public final String getDeployedStructuresJson() {
        return this.deployedStructuresJson;
    }

    public final String getInventoryJson() {
        return this.inventoryJson;
    }

    public final String getQuestsJson() {
        return this.questsJson;
    }
}
