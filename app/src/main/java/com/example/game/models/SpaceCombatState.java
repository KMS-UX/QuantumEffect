package com.example.game.models;

import java.util.List;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StarshipModels.kt */
/* loaded from: classes4.dex */
public final /* data */ class SpaceCombatState {
    public static final int $stable = 8;
    private final boolean battleOver;
    private final List<String> combatLogs;
    private final float enemyHull;
    private final float enemyMaxHull;
    private final float enemyMaxShield;
    private final String enemyName;
    private final float enemyShield;
    private final float enemyWeaponPower;
    private final boolean isCombatActive;
    private final boolean playerTurn;
    private final boolean playerWon;
    private final int rewardCredits;
    private final int rewardNanites;

    public SpaceCombatState() {
        this(false, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, null, 0, 0, false, false, 8191, null);
    }

    public static /* synthetic */ SpaceCombatState copy$default(SpaceCombatState spaceCombatState, boolean z, String str, float f, float f2, float f3, float f4, float f5, boolean z2, List list, int i, int i2, boolean z3, boolean z4, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            z = spaceCombatState.isCombatActive;
        }
        return spaceCombatState.copy(z, (i3 & 2) != 0 ? spaceCombatState.enemyName : str, (i3 & 4) != 0 ? spaceCombatState.enemyHull : f, (i3 & 8) != 0 ? spaceCombatState.enemyMaxHull : f2, (i3 & 16) != 0 ? spaceCombatState.enemyShield : f3, (i3 & 32) != 0 ? spaceCombatState.enemyMaxShield : f4, (i3 & 64) != 0 ? spaceCombatState.enemyWeaponPower : f5, (i3 & 128) != 0 ? spaceCombatState.playerTurn : z2, (i3 & 256) != 0 ? spaceCombatState.combatLogs : list, (i3 & 512) != 0 ? spaceCombatState.rewardCredits : i, (i3 & 1024) != 0 ? spaceCombatState.rewardNanites : i2, (i3 & 2048) != 0 ? spaceCombatState.battleOver : z3, (i3 & 4096) != 0 ? spaceCombatState.playerWon : z4);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean component1() {
        return this.isCombatActive;
    }

    /* renamed from: component10, reason: from getter */
    public final int component10() {
        return this.rewardCredits;
    }

    /* renamed from: component11, reason: from getter */
    public final int component11() {
        return this.rewardNanites;
    }

    /* renamed from: component12, reason: from getter */
    public final boolean component12() {
        return this.battleOver;
    }

    /* renamed from: component13, reason: from getter */
    public final boolean component13() {
        return this.playerWon;
    }

    /* renamed from: component2, reason: from getter */
    public final String component2() {
        return this.enemyName;
    }

    /* renamed from: component3, reason: from getter */
    public final float component3() {
        return this.enemyHull;
    }

    /* renamed from: component4, reason: from getter */
    public final float component4() {
        return this.enemyMaxHull;
    }

    /* renamed from: component5, reason: from getter */
    public final float component5() {
        return this.enemyShield;
    }

    /* renamed from: component6, reason: from getter */
    public final float component6() {
        return this.enemyMaxShield;
    }

    /* renamed from: component7, reason: from getter */
    public final float component7() {
        return this.enemyWeaponPower;
    }

    /* renamed from: component8, reason: from getter */
    public final boolean component8() {
        return this.playerTurn;
    }

    public final List<String> component9() {
        return this.combatLogs;
    }

    public final SpaceCombatState copy(boolean isCombatActive, String enemyName, float enemyHull, float enemyMaxHull, float enemyShield, float enemyMaxShield, float enemyWeaponPower, boolean playerTurn, List<String> combatLogs, int rewardCredits, int rewardNanites, boolean battleOver, boolean playerWon) {
        Intrinsics.checkNotNullParameter(enemyName, "enemyName");
        Intrinsics.checkNotNullParameter(combatLogs, "combatLogs");
        return new SpaceCombatState(isCombatActive, enemyName, enemyHull, enemyMaxHull, enemyShield, enemyMaxShield, enemyWeaponPower, playerTurn, combatLogs, rewardCredits, rewardNanites, battleOver, playerWon);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SpaceCombatState)) {
            return false;
        }
        SpaceCombatState spaceCombatState = (SpaceCombatState) other;
        return this.isCombatActive == spaceCombatState.isCombatActive && Intrinsics.areEqual(this.enemyName, spaceCombatState.enemyName) && Float.compare(this.enemyHull, spaceCombatState.enemyHull) == 0 && Float.compare(this.enemyMaxHull, spaceCombatState.enemyMaxHull) == 0 && Float.compare(this.enemyShield, spaceCombatState.enemyShield) == 0 && Float.compare(this.enemyMaxShield, spaceCombatState.enemyMaxShield) == 0 && Float.compare(this.enemyWeaponPower, spaceCombatState.enemyWeaponPower) == 0 && this.playerTurn == spaceCombatState.playerTurn && Intrinsics.areEqual(this.combatLogs, spaceCombatState.combatLogs) && this.rewardCredits == spaceCombatState.rewardCredits && this.rewardNanites == spaceCombatState.rewardNanites && this.battleOver == spaceCombatState.battleOver && this.playerWon == spaceCombatState.playerWon;
    }

    public int hashCode() {
        return (((((((((((((((((((((((Boolean.hashCode(this.isCombatActive) * 31) + this.enemyName.hashCode()) * 31) + Float.hashCode(this.enemyHull)) * 31) + Float.hashCode(this.enemyMaxHull)) * 31) + Float.hashCode(this.enemyShield)) * 31) + Float.hashCode(this.enemyMaxShield)) * 31) + Float.hashCode(this.enemyWeaponPower)) * 31) + Boolean.hashCode(this.playerTurn)) * 31) + this.combatLogs.hashCode()) * 31) + Integer.hashCode(this.rewardCredits)) * 31) + Integer.hashCode(this.rewardNanites)) * 31) + Boolean.hashCode(this.battleOver)) * 31) + Boolean.hashCode(this.playerWon);
    }

    public String toString() {
        return "SpaceCombatState(isCombatActive=" + this.isCombatActive + ", enemyName=" + this.enemyName + ", enemyHull=" + this.enemyHull + ", enemyMaxHull=" + this.enemyMaxHull + ", enemyShield=" + this.enemyShield + ", enemyMaxShield=" + this.enemyMaxShield + ", enemyWeaponPower=" + this.enemyWeaponPower + ", playerTurn=" + this.playerTurn + ", combatLogs=" + this.combatLogs + ", rewardCredits=" + this.rewardCredits + ", rewardNanites=" + this.rewardNanites + ", battleOver=" + this.battleOver + ", playerWon=" + this.playerWon + ")";
    }

    public SpaceCombatState(boolean isCombatActive, String enemyName, float enemyHull, float enemyMaxHull, float enemyShield, float enemyMaxShield, float enemyWeaponPower, boolean playerTurn, List<String> combatLogs, int rewardCredits, int rewardNanites, boolean battleOver, boolean playerWon) {
        Intrinsics.checkNotNullParameter(enemyName, "enemyName");
        Intrinsics.checkNotNullParameter(combatLogs, "combatLogs");
        this.isCombatActive = isCombatActive;
        this.enemyName = enemyName;
        this.enemyHull = enemyHull;
        this.enemyMaxHull = enemyMaxHull;
        this.enemyShield = enemyShield;
        this.enemyMaxShield = enemyMaxShield;
        this.enemyWeaponPower = enemyWeaponPower;
        this.playerTurn = playerTurn;
        this.combatLogs = combatLogs;
        this.rewardCredits = rewardCredits;
        this.rewardNanites = rewardNanites;
        this.battleOver = battleOver;
        this.playerWon = playerWon;
    }

    public /* synthetic */ SpaceCombatState(boolean z, String str, float f, float f2, float f3, float f4, float f5, boolean z2, List list, int i, int i2, boolean z3, boolean z4, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? false : z, (i3 & 2) != 0 ? "" : str, (i3 & 4) != 0 ? 0.0f : f, (i3 & 8) != 0 ? 0.0f : f2, (i3 & 16) != 0 ? 0.0f : f3, (i3 & 32) == 0 ? f4 : 0.0f, (i3 & 64) != 0 ? 15.0f : f5, (i3 & 128) != 0 ? true : z2, (i3 & 256) != 0 ? CollectionsKt.emptyList() : list, (i3 & 512) != 0 ? 0 : i, (i3 & 1024) != 0 ? 0 : i2, (i3 & 2048) != 0 ? false : z3, (i3 & 4096) == 0 ? z4 : false);
    }

    public final boolean isCombatActive() {
        return this.isCombatActive;
    }

    public final String getEnemyName() {
        return this.enemyName;
    }

    public final float getEnemyHull() {
        return this.enemyHull;
    }

    public final float getEnemyMaxHull() {
        return this.enemyMaxHull;
    }

    public final float getEnemyShield() {
        return this.enemyShield;
    }

    public final float getEnemyMaxShield() {
        return this.enemyMaxShield;
    }

    public final float getEnemyWeaponPower() {
        return this.enemyWeaponPower;
    }

    public final boolean getPlayerTurn() {
        return this.playerTurn;
    }

    public final List<String> getCombatLogs() {
        return this.combatLogs;
    }

    public final int getRewardCredits() {
        return this.rewardCredits;
    }

    public final int getRewardNanites() {
        return this.rewardNanites;
    }

    public final boolean getBattleOver() {
        return this.battleOver;
    }

    public final boolean getPlayerWon() {
        return this.playerWon;
    }
}
