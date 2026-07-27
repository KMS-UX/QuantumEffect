package com.example.game.models;

import com.squareup.moshi.JsonClass;
import java.util.List;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameModels.kt */
@JsonClass(generateAdapter = true)
/* loaded from: classes4.dex */
public final /* data */ class BattleState {
    public static final int $stable = 8;
    private final Enemy activeEnemy;
    private final int battleRewardCredits;
    private final int battleRewardXp;
    private final boolean defeat;
    private final boolean isActive;
    private final boolean isPlayerTurn;
    private final List<BattleLog> logs;
    private final int playerHp;
    private final int playerMaxHp;
    private final int playerMaxMp;
    private final int playerMp;
    private final int turnNumber;
    private final boolean victory;

    public BattleState() {
        this(false, 0, 0, 0, 0, null, null, 0, false, 0, 0, false, false, 8191, null);
    }

    public static /* synthetic */ BattleState copy$default(BattleState battleState, boolean z, int i, int i2, int i3, int i4, Enemy enemy, List list, int i5, boolean z2, int i6, int i7, boolean z3, boolean z4, int i8, Object obj) {
        if ((i8 & 1) != 0) {
            z = battleState.isActive;
        }
        return battleState.copy(z, (i8 & 2) != 0 ? battleState.playerHp : i, (i8 & 4) != 0 ? battleState.playerMaxHp : i2, (i8 & 8) != 0 ? battleState.playerMp : i3, (i8 & 16) != 0 ? battleState.playerMaxMp : i4, (i8 & 32) != 0 ? battleState.activeEnemy : enemy, (i8 & 64) != 0 ? battleState.logs : list, (i8 & 128) != 0 ? battleState.turnNumber : i5, (i8 & 256) != 0 ? battleState.isPlayerTurn : z2, (i8 & 512) != 0 ? battleState.battleRewardXp : i6, (i8 & 1024) != 0 ? battleState.battleRewardCredits : i7, (i8 & 2048) != 0 ? battleState.victory : z3, (i8 & 4096) != 0 ? battleState.defeat : z4);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean component1() {
        return this.isActive;
    }

    /* renamed from: component10, reason: from getter */
    public final int component10() {
        return this.battleRewardXp;
    }

    /* renamed from: component11, reason: from getter */
    public final int component11() {
        return this.battleRewardCredits;
    }

    /* renamed from: component12, reason: from getter */
    public final boolean component12() {
        return this.victory;
    }

    /* renamed from: component13, reason: from getter */
    public final boolean component13() {
        return this.defeat;
    }

    /* renamed from: component2, reason: from getter */
    public final int component2() {
        return this.playerHp;
    }

    /* renamed from: component3, reason: from getter */
    public final int component3() {
        return this.playerMaxHp;
    }

    /* renamed from: component4, reason: from getter */
    public final int component4() {
        return this.playerMp;
    }

    /* renamed from: component5, reason: from getter */
    public final int component5() {
        return this.playerMaxMp;
    }

    /* renamed from: component6, reason: from getter */
    public final Enemy component6() {
        return this.activeEnemy;
    }

    public final List<BattleLog> component7() {
        return this.logs;
    }

    /* renamed from: component8, reason: from getter */
    public final int component8() {
        return this.turnNumber;
    }

    /* renamed from: component9, reason: from getter */
    public final boolean component9() {
        return this.isPlayerTurn;
    }

    public final BattleState copy(boolean isActive, int playerHp, int playerMaxHp, int playerMp, int playerMaxMp, Enemy activeEnemy, List<BattleLog> logs, int turnNumber, boolean isPlayerTurn, int battleRewardXp, int battleRewardCredits, boolean victory, boolean defeat) {
        Intrinsics.checkNotNullParameter(logs, "logs");
        return new BattleState(isActive, playerHp, playerMaxHp, playerMp, playerMaxMp, activeEnemy, logs, turnNumber, isPlayerTurn, battleRewardXp, battleRewardCredits, victory, defeat);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BattleState)) {
            return false;
        }
        BattleState battleState = (BattleState) other;
        return this.isActive == battleState.isActive && this.playerHp == battleState.playerHp && this.playerMaxHp == battleState.playerMaxHp && this.playerMp == battleState.playerMp && this.playerMaxMp == battleState.playerMaxMp && Intrinsics.areEqual(this.activeEnemy, battleState.activeEnemy) && Intrinsics.areEqual(this.logs, battleState.logs) && this.turnNumber == battleState.turnNumber && this.isPlayerTurn == battleState.isPlayerTurn && this.battleRewardXp == battleState.battleRewardXp && this.battleRewardCredits == battleState.battleRewardCredits && this.victory == battleState.victory && this.defeat == battleState.defeat;
    }

    public int hashCode() {
        return (((((((((((((((((((((((Boolean.hashCode(this.isActive) * 31) + Integer.hashCode(this.playerHp)) * 31) + Integer.hashCode(this.playerMaxHp)) * 31) + Integer.hashCode(this.playerMp)) * 31) + Integer.hashCode(this.playerMaxMp)) * 31) + (this.activeEnemy == null ? 0 : this.activeEnemy.hashCode())) * 31) + this.logs.hashCode()) * 31) + Integer.hashCode(this.turnNumber)) * 31) + Boolean.hashCode(this.isPlayerTurn)) * 31) + Integer.hashCode(this.battleRewardXp)) * 31) + Integer.hashCode(this.battleRewardCredits)) * 31) + Boolean.hashCode(this.victory)) * 31) + Boolean.hashCode(this.defeat);
    }

    public String toString() {
        return "BattleState(isActive=" + this.isActive + ", playerHp=" + this.playerHp + ", playerMaxHp=" + this.playerMaxHp + ", playerMp=" + this.playerMp + ", playerMaxMp=" + this.playerMaxMp + ", activeEnemy=" + this.activeEnemy + ", logs=" + this.logs + ", turnNumber=" + this.turnNumber + ", isPlayerTurn=" + this.isPlayerTurn + ", battleRewardXp=" + this.battleRewardXp + ", battleRewardCredits=" + this.battleRewardCredits + ", victory=" + this.victory + ", defeat=" + this.defeat + ")";
    }

    public BattleState(boolean isActive, int playerHp, int playerMaxHp, int playerMp, int playerMaxMp, Enemy activeEnemy, List<BattleLog> logs, int turnNumber, boolean isPlayerTurn, int battleRewardXp, int battleRewardCredits, boolean victory, boolean defeat) {
        Intrinsics.checkNotNullParameter(logs, "logs");
        this.isActive = isActive;
        this.playerHp = playerHp;
        this.playerMaxHp = playerMaxHp;
        this.playerMp = playerMp;
        this.playerMaxMp = playerMaxMp;
        this.activeEnemy = activeEnemy;
        this.logs = logs;
        this.turnNumber = turnNumber;
        this.isPlayerTurn = isPlayerTurn;
        this.battleRewardXp = battleRewardXp;
        this.battleRewardCredits = battleRewardCredits;
        this.victory = victory;
        this.defeat = defeat;
    }

    public /* synthetic */ BattleState(boolean z, int i, int i2, int i3, int i4, Enemy enemy, List list, int i5, boolean z2, int i6, int i7, boolean z3, boolean z4, int i8, DefaultConstructorMarker defaultConstructorMarker) {
        this((i8 & 1) != 0 ? false : z, (i8 & 2) != 0 ? 520 : i, (i8 & 4) == 0 ? i2 : 520, (i8 & 8) != 0 ? 80 : i3, (i8 & 16) == 0 ? i4 : 80, (i8 & 32) != 0 ? null : enemy, (i8 & 64) != 0 ? CollectionsKt.emptyList() : list, (i8 & 128) != 0 ? 1 : i5, (i8 & 256) == 0 ? z2 : true, (i8 & 512) != 0 ? 0 : i6, (i8 & 1024) != 0 ? 0 : i7, (i8 & 2048) != 0 ? false : z3, (i8 & 4096) == 0 ? z4 : false);
    }

    public final boolean isActive() {
        return this.isActive;
    }

    public final int getPlayerHp() {
        return this.playerHp;
    }

    public final int getPlayerMaxHp() {
        return this.playerMaxHp;
    }

    public final int getPlayerMp() {
        return this.playerMp;
    }

    public final int getPlayerMaxMp() {
        return this.playerMaxMp;
    }

    public final Enemy getActiveEnemy() {
        return this.activeEnemy;
    }

    public final List<BattleLog> getLogs() {
        return this.logs;
    }

    public final int getTurnNumber() {
        return this.turnNumber;
    }

    public final boolean isPlayerTurn() {
        return this.isPlayerTurn;
    }

    public final int getBattleRewardXp() {
        return this.battleRewardXp;
    }

    public final int getBattleRewardCredits() {
        return this.battleRewardCredits;
    }

    public final boolean getVictory() {
        return this.victory;
    }

    public final boolean getDefeat() {
        return this.defeat;
    }
}
