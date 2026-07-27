package com.example.game.models;

import com.squareup.moshi.JsonClass;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameModels.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes4.dex */
public final /* data */ class PointOfInterest {
    public static final int $stable = 0;
    private final Biome biome;
    private final String bossName;
    private final String dangerLevel;
    private final String description;
    private final Faction factionPresent;
    private final boolean hasBoss;
    private final boolean isCleared;
    private final String name;
    private final String resource;

    public static /* synthetic */ PointOfInterest copy$default(PointOfInterest pointOfInterest, String str, Biome biome, String str2, String str3, String str4, boolean z, String str5, Faction faction, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = pointOfInterest.name;
        }
        if ((i & 2) != 0) {
            biome = pointOfInterest.biome;
        }
        if ((i & 4) != 0) {
            str2 = pointOfInterest.description;
        }
        if ((i & 8) != 0) {
            str3 = pointOfInterest.dangerLevel;
        }
        if ((i & 16) != 0) {
            str4 = pointOfInterest.resource;
        }
        if ((i & 32) != 0) {
            z = pointOfInterest.hasBoss;
        }
        if ((i & 64) != 0) {
            str5 = pointOfInterest.bossName;
        }
        if ((i & 128) != 0) {
            faction = pointOfInterest.factionPresent;
        }
        if ((i & 256) != 0) {
            z2 = pointOfInterest.isCleared;
        }
        Faction faction2 = faction;
        boolean z3 = z2;
        boolean z4 = z;
        String str6 = str5;
        String str7 = str4;
        String str8 = str2;
        return pointOfInterest.copy(str, biome, str8, str3, str7, z4, str6, faction2, z3);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.name;
    }

    /* renamed from: component2, reason: from getter */
    public final Biome component2() {
        return this.biome;
    }

    /* renamed from: component3, reason: from getter */
    public final String component3() {
        return this.description;
    }

    /* renamed from: component4, reason: from getter */
    public final String component4() {
        return this.dangerLevel;
    }

    /* renamed from: component5, reason: from getter */
    public final String component5() {
        return this.resource;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean component6() {
        return this.hasBoss;
    }

    /* renamed from: component7, reason: from getter */
    public final String component7() {
        return this.bossName;
    }

    /* renamed from: component8, reason: from getter */
    public final Faction component8() {
        return this.factionPresent;
    }

    /* renamed from: component9, reason: from getter */
    public final boolean component9() {
        return this.isCleared;
    }

    public final PointOfInterest copy(String name, Biome biome, String description, String dangerLevel, String resource, boolean hasBoss, String bossName, Faction factionPresent, boolean isCleared) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(biome, "biome");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(dangerLevel, "dangerLevel");
        Intrinsics.checkNotNullParameter(resource, "resource");
        Intrinsics.checkNotNullParameter(factionPresent, "factionPresent");
        return new PointOfInterest(name, biome, description, dangerLevel, resource, hasBoss, bossName, factionPresent, isCleared);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PointOfInterest)) {
            return false;
        }
        PointOfInterest pointOfInterest = (PointOfInterest) other;
        return Intrinsics.areEqual(this.name, pointOfInterest.name) && this.biome == pointOfInterest.biome && Intrinsics.areEqual(this.description, pointOfInterest.description) && Intrinsics.areEqual(this.dangerLevel, pointOfInterest.dangerLevel) && Intrinsics.areEqual(this.resource, pointOfInterest.resource) && this.hasBoss == pointOfInterest.hasBoss && Intrinsics.areEqual(this.bossName, pointOfInterest.bossName) && this.factionPresent == pointOfInterest.factionPresent && this.isCleared == pointOfInterest.isCleared;
    }

    public int hashCode() {
        return (((((((((((((((this.name.hashCode() * 31) + this.biome.hashCode()) * 31) + this.description.hashCode()) * 31) + this.dangerLevel.hashCode()) * 31) + this.resource.hashCode()) * 31) + Boolean.hashCode(this.hasBoss)) * 31) + (this.bossName == null ? 0 : this.bossName.hashCode())) * 31) + this.factionPresent.hashCode()) * 31) + Boolean.hashCode(this.isCleared);
    }

    public String toString() {
        return "PointOfInterest(name=" + this.name + ", biome=" + this.biome + ", description=" + this.description + ", dangerLevel=" + this.dangerLevel + ", resource=" + this.resource + ", hasBoss=" + this.hasBoss + ", bossName=" + this.bossName + ", factionPresent=" + this.factionPresent + ", isCleared=" + this.isCleared + ")";
    }

    public PointOfInterest(String name, Biome biome, String description, String dangerLevel, String resource, boolean hasBoss, String bossName, Faction factionPresent, boolean isCleared) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(biome, "biome");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(dangerLevel, "dangerLevel");
        Intrinsics.checkNotNullParameter(resource, "resource");
        Intrinsics.checkNotNullParameter(factionPresent, "factionPresent");
        this.name = name;
        this.biome = biome;
        this.description = description;
        this.dangerLevel = dangerLevel;
        this.resource = resource;
        this.hasBoss = hasBoss;
        this.bossName = bossName;
        this.factionPresent = factionPresent;
        this.isCleared = isCleared;
    }

    public /* synthetic */ PointOfInterest(String str, Biome biome, String str2, String str3, String str4, boolean z, String str5, Faction faction, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, biome, str2, str3, str4, (i & 32) != 0 ? false : z, (i & 64) != 0 ? null : str5, faction, (i & 256) != 0 ? false : z2);
    }

    public final String getName() {
        return this.name;
    }

    public final Biome getBiome() {
        return this.biome;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getDangerLevel() {
        return this.dangerLevel;
    }

    public final String getResource() {
        return this.resource;
    }

    public final boolean getHasBoss() {
        return this.hasBoss;
    }

    public final String getBossName() {
        return this.bossName;
    }

    public final Faction getFactionPresent() {
        return this.factionPresent;
    }

    public final boolean isCleared() {
        return this.isCleared;
    }
}
