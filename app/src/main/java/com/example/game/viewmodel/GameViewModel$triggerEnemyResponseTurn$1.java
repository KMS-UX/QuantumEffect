package com.example.game.viewmodel;

import com.example.game.models.BattleLog;
import com.example.game.models.BattleState;
import com.example.game.models.Enemy;
import com.example.ui.theme.ColorKt;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.MutableStateFlow;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$triggerEnemyResponseTurn$1", f = "GameViewModel.kt", i = {}, l = {981}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes4.dex */
public final class GameViewModel$triggerEnemyResponseTurn$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$triggerEnemyResponseTurn$1(GameViewModel gameViewModel, Continuation<? super GameViewModel$triggerEnemyResponseTurn$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$triggerEnemyResponseTurn$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$triggerEnemyResponseTurn$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        MutableStateFlow mutableStateFlow;
        int finalPlayerHp;
        MutableStateFlow mutableStateFlow2;
        MutableStateFlow mutableStateFlow3;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                if (DelayKt.delay(1200L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        mutableStateFlow = this.this$0._battleState;
        BattleState state = (BattleState) mutableStateFlow.getValue();
        Enemy enemy = state.getActiveEnemy();
        if (enemy == null) {
            return Unit.INSTANCE;
        }
        if (state.getVictory() || state.getDefeat()) {
            return Unit.INSTANCE;
        }
        boolean isMiss = Random.INSTANCE.nextInt(100) < 15;
        int damage = RangesKt.coerceAtLeast(enemy.getAtk() - Random.INSTANCE.nextInt(0, 10), 10);
        List newLogs = CollectionsKt.toMutableList((Collection) state.getLogs());
        int finalPlayerHp2 = state.getPlayerHp();
        if (isMiss) {
            newLogs.add(new BattleLog("DODGED: " + enemy.getName() + " sweeps a heavy strike but misses your localized coordinate!", false, ColorKt.getQuantumNeonBlue(), null));
            finalPlayerHp = finalPlayerHp2;
        } else {
            int finalPlayerHp3 = RangesKt.coerceAtLeast(state.getPlayerHp() - damage, 0);
            newLogs.add(new BattleLog("ALERT: " + enemy.getName() + " releases " + enemy.getModName() + " blast, dealing " + damage + " damage to your cyber-chassis!", false, ColorKt.getQuantumNeonRed(), null));
            finalPlayerHp = finalPlayerHp3;
        }
        if (finalPlayerHp != 0) {
            mutableStateFlow2 = this.this$0._battleState;
            mutableStateFlow2.setValue(BattleState.copy$default(state, false, finalPlayerHp, 0, 0, 0, null, newLogs, state.getTurnNumber() + 1, true, 0, 0, false, false, 7741, null));
            return Unit.INSTANCE;
        }
        newLogs.add(new BattleLog("SYSTEM CRITICAL FAILURE: Cyber-core offline. Quantum Baby has collapsed ...", false, ColorKt.getQuantumNeonRed(), null));
        mutableStateFlow3 = this.this$0._battleState;
        mutableStateFlow3.setValue(BattleState.copy$default(state, false, finalPlayerHp, 0, 0, 0, null, newLogs, 0, false, 0, 0, false, true, 4028, null));
        return Unit.INSTANCE;
    }
}
