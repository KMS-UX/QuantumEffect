package com.example.game.viewmodel;

import com.example.game.models.BattleState;
import com.example.game.models.Enemy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$executePlayerAttack$1", f = "GameViewModel.kt", i = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, l = {839, 879}, m = "invokeSuspend", n = {"currentSave", "updatedEnemy", "newLogs", "hasCritMod", "hasLifeDrain", "critChance", "isCrit", "baseAtk", "damage", "updatedEnemyHp", "healAmount", "finalPlayerHp"}, s = {"L$0", "L$1", "L$2", "Z$0", "Z$1", "I$0", "I$1", "I$2", "I$3", "I$4", "I$5", "I$6"})
/* loaded from: classes4.dex */
public final class GameViewModel$executePlayerAttack$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Enemy $enemy;
    final /* synthetic */ BattleState $state;
    int I$0;
    int I$1;
    int I$2;
    int I$3;
    int I$4;
    int I$5;
    int I$6;
    Object L$0;
    Object L$1;
    Object L$2;
    boolean Z$0;
    boolean Z$1;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$executePlayerAttack$1(GameViewModel gameViewModel, Enemy enemy, BattleState battleState, Continuation<? super GameViewModel$executePlayerAttack$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$enemy = enemy;
        this.$state = battleState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$executePlayerAttack$1(this.this$0, this.$enemy, this.$state, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$executePlayerAttack$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0009. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:12:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006e  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r44) {
        /*
            Method dump skipped, instructions count: 620
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.viewmodel.GameViewModel$executePlayerAttack$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
