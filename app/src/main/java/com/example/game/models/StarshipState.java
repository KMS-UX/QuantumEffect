package com.example.game.models;

import java.util.List;
import java.util.Map;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: StarshipModels.kt */

/* loaded from: classes4.dex */
public final /* data */ class StarshipState {
    public static final int $stable = 8;
    private final String activeEndingChoice;
    private final Map<String, Integer> cargo;
    private final List<CrewMember> crew;
    private final String currentSystemId;
    private final float fuel;
    private final float hull;
    private final float maxFuel;
    private final float maxHull;
    private final float maxShield;
    private final String name;
    private final int quantumBabiesDiscovered;
    private final float shield;
    private final List<StarSystem> starSystems;
    private final Map<ShipSystem, Integer> systemPowerAllocation;
    private final int universeAlignment;
    private final List<ShipUpgrade> upgrades;

    public StarshipState() {
        this(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, null, null, null, null, null, 0, 0, null, 65535, null);
    }

    public static /* synthetic */ StarshipState copy$default(StarshipState starshipState, String str, float f, float f2, float f3, float f4, float f5, float f6, Map map, Map map2, List list, List list2, List list3, String str2, int i, int i2, String str3, int i3, Object obj) {
        String str4 = (i3 & 1) != 0 ? starshipState.name : str;
        return starshipState.copy(str4, (i3 & 2) != 0 ? starshipState.hull : f, (i3 & 4) != 0 ? starshipState.maxHull : f2, (i3 & 8) != 0 ? starshipState.shield : f3, (i3 & 16) != 0 ? starshipState.maxShield : f4, (i3 & 32) != 0 ? starshipState.fuel : f5, (i3 & 64) != 0 ? starshipState.maxFuel : f6, (i3 & 128) != 0 ? starshipState.systemPowerAllocation : map, (i3 & 256) != 0 ? starshipState.cargo : map2, (i3 & 512) != 0 ? starshipState.upgrades : list, (i3 & 1024) != 0 ? starshipState.crew : list2, (i3 & 2048) != 0 ? starshipState.starSystems : list3, (i3 & 4096) != 0 ? starshipState.currentSystemId : str2, (i3 & 8192) != 0 ? starshipState.universeAlignment : i, (i3 & 16384) != 0 ? starshipState.quantumBabiesDiscovered : i2, (i3 & 32768) != 0 ? starshipState.activeEndingChoice : str3);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.name;
    }

    public final List<ShipUpgrade> component10() {
        return this.upgrades;
    }

    public final List<CrewMember> component11() {
        return this.crew;
    }

    public final List<StarSystem> component12() {
        return this.starSystems;
    }

    /* renamed from: component13, reason: from getter */
    public final String component13() {
        return this.currentSystemId;
    }

    /* renamed from: component14, reason: from getter */
    public final int component14() {
        return this.universeAlignment;
    }

    /* renamed from: component15, reason: from getter */
    public final int component15() {
        return this.quantumBabiesDiscovered;
    }

    /* renamed from: component16, reason: from getter */
    public final String component16() {
        return this.activeEndingChoice;
    }

    /* renamed from: component2, reason: from getter */
    public final float component2() {
        return this.hull;
    }

    /* renamed from: component3, reason: from getter */
    public final float component3() {
        return this.maxHull;
    }

    /* renamed from: component4, reason: from getter */
    public final float component4() {
        return this.shield;
    }

    /* renamed from: component5, reason: from getter */
    public final float component5() {
        return this.maxShield;
    }

    /* renamed from: component6, reason: from getter */
    public final float component6() {
        return this.fuel;
    }

    /* renamed from: component7, reason: from getter */
    public final float component7() {
        return this.maxFuel;
    }

    public final Map<ShipSystem, Integer> component8() {
        return this.systemPowerAllocation;
    }

    public final Map<String, Integer> component9() {
        return this.cargo;
    }

    public final StarshipState copy(String name, float hull, float maxHull, float shield, float maxShield, float fuel, float maxFuel, Map<ShipSystem, Integer> systemPowerAllocation, Map<String, Integer> cargo, List<ShipUpgrade> upgrades, List<CrewMember> crew, List<StarSystem> starSystems, String currentSystemId, int universeAlignment, int quantumBabiesDiscovered, String activeEndingChoice) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(systemPowerAllocation, "systemPowerAllocation");
        Intrinsics.checkNotNullParameter(cargo, "cargo");
        Intrinsics.checkNotNullParameter(upgrades, "upgrades");
        Intrinsics.checkNotNullParameter(crew, "crew");
        Intrinsics.checkNotNullParameter(starSystems, "starSystems");
        Intrinsics.checkNotNullParameter(currentSystemId, "currentSystemId");
        return new StarshipState(name, hull, maxHull, shield, maxShield, fuel, maxFuel, systemPowerAllocation, cargo, upgrades, crew, starSystems, currentSystemId, universeAlignment, quantumBabiesDiscovered, activeEndingChoice);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StarshipState)) {
            return false;
        }
        StarshipState starshipState = (StarshipState) other;
        return Intrinsics.areEqual(this.name, starshipState.name) && Float.compare(this.hull, starshipState.hull) == 0 && Float.compare(this.maxHull, starshipState.maxHull) == 0 && Float.compare(this.shield, starshipState.shield) == 0 && Float.compare(this.maxShield, starshipState.maxShield) == 0 && Float.compare(this.fuel, starshipState.fuel) == 0 && Float.compare(this.maxFuel, starshipState.maxFuel) == 0 && Intrinsics.areEqual(this.systemPowerAllocation, starshipState.systemPowerAllocation) && Intrinsics.areEqual(this.cargo, starshipState.cargo) && Intrinsics.areEqual(this.upgrades, starshipState.upgrades) && Intrinsics.areEqual(this.crew, starshipState.crew) && Intrinsics.areEqual(this.starSystems, starshipState.starSystems) && Intrinsics.areEqual(this.currentSystemId, starshipState.currentSystemId) && this.universeAlignment == starshipState.universeAlignment && this.quantumBabiesDiscovered == starshipState.quantumBabiesDiscovered && Intrinsics.areEqual(this.activeEndingChoice, starshipState.activeEndingChoice);
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((this.name.hashCode() * 31) + Float.hashCode(this.hull)) * 31) + Float.hashCode(this.maxHull)) * 31) + Float.hashCode(this.shield)) * 31) + Float.hashCode(this.maxShield)) * 31) + Float.hashCode(this.fuel)) * 31) + Float.hashCode(this.maxFuel)) * 31) + this.systemPowerAllocation.hashCode()) * 31) + this.cargo.hashCode()) * 31) + this.upgrades.hashCode()) * 31) + this.crew.hashCode()) * 31) + this.starSystems.hashCode()) * 31) + this.currentSystemId.hashCode()) * 31) + Integer.hashCode(this.universeAlignment)) * 31) + Integer.hashCode(this.quantumBabiesDiscovered)) * 31) + (this.activeEndingChoice == null ? 0 : this.activeEndingChoice.hashCode());
    }

    public String toString() {
        return "StarshipState(name=" + this.name + ", hull=" + this.hull + ", maxHull=" + this.maxHull + ", shield=" + this.shield + ", maxShield=" + this.maxShield + ", fuel=" + this.fuel + ", maxFuel=" + this.maxFuel + ", systemPowerAllocation=" + this.systemPowerAllocation + ", cargo=" + this.cargo + ", upgrades=" + this.upgrades + ", crew=" + this.crew + ", starSystems=" + this.starSystems + ", currentSystemId=" + this.currentSystemId + ", universeAlignment=" + this.universeAlignment + ", quantumBabiesDiscovered=" + this.quantumBabiesDiscovered + ", activeEndingChoice=" + this.activeEndingChoice + ")";
    }

    public StarshipState(String name, float hull, float maxHull, float shield, float maxShield, float fuel, float maxFuel, Map<ShipSystem, Integer> systemPowerAllocation, Map<String, Integer> cargo, List<ShipUpgrade> upgrades, List<CrewMember> crew, List<StarSystem> starSystems, String currentSystemId, int universeAlignment, int quantumBabiesDiscovered, String activeEndingChoice) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(systemPowerAllocation, "systemPowerAllocation");
        Intrinsics.checkNotNullParameter(cargo, "cargo");
        Intrinsics.checkNotNullParameter(upgrades, "upgrades");
        Intrinsics.checkNotNullParameter(crew, "crew");
        Intrinsics.checkNotNullParameter(starSystems, "starSystems");
        Intrinsics.checkNotNullParameter(currentSystemId, "currentSystemId");
        this.name = name;
        this.hull = hull;
        this.maxHull = maxHull;
        this.shield = shield;
        this.maxShield = maxShield;
        this.fuel = fuel;
        this.maxFuel = maxFuel;
        this.systemPowerAllocation = systemPowerAllocation;
        this.cargo = cargo;
        this.upgrades = upgrades;
        this.crew = crew;
        this.starSystems = starSystems;
        this.currentSystemId = currentSystemId;
        this.universeAlignment = universeAlignment;
        this.quantumBabiesDiscovered = quantumBabiesDiscovered;
        this.activeEndingChoice = activeEndingChoice;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ StarshipState(java.lang.String r18, float r19, float r20, float r21, float r22, float r23, float r24, java.util.Map r25, java.util.Map r26, java.util.List r27, java.util.List r28, java.util.List r29, java.lang.String r30, int r31, int r32, java.lang.String r33, int r34, kotlin.jvm.internal.DefaultConstructorMarker r35) {
        /*
            Method dump skipped, instructions count: 327
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.models.StarshipState.<init>(java.lang.String, float, float, float, float, float, float, java.util.Map, java.util.Map, java.util.List, java.util.List, java.util.List, java.lang.String, int, int, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final String getName() {
        return this.name;
    }

    public final float getHull() {
        return this.hull;
    }

    public final float getMaxHull() {
        return this.maxHull;
    }

    public final float getShield() {
        return this.shield;
    }

    public final float getMaxShield() {
        return this.maxShield;
    }

    public final float getFuel() {
        return this.fuel;
    }

    public final float getMaxFuel() {
        return this.maxFuel;
    }

    public final Map<ShipSystem, Integer> getSystemPowerAllocation() {
        return this.systemPowerAllocation;
    }

    public final Map<String, Integer> getCargo() {
        return this.cargo;
    }

    public final List<ShipUpgrade> getUpgrades() {
        return this.upgrades;
    }

    public final List<CrewMember> getCrew() {
        return this.crew;
    }

    public final List<StarSystem> getStarSystems() {
        return this.starSystems;
    }

    public final String getCurrentSystemId() {
        return this.currentSystemId;
    }

    public final int getUniverseAlignment() {
        return this.universeAlignment;
    }

    public final int getQuantumBabiesDiscovered() {
        return this.quantumBabiesDiscovered;
    }

    public final String getActiveEndingChoice() {
        return this.activeEndingChoice;
    }
}
