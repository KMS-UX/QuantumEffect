package com.example.game.viewmodel;

import com.example.game.models.ShipSystem;
import com.example.game.models.SpaceCombatState;
import com.example.game.models.StarshipState;
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
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.MutableStateFlow;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$triggerEnemyTurn$1", f = "GameViewModel.kt", i = {}, l = {1829}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes4.dex */
public final class GameViewModel$triggerEnemyTurn$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$triggerEnemyTurn$1(GameViewModel gameViewModel, Continuation<? super GameViewModel$triggerEnemyTurn$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$triggerEnemyTurn$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$triggerEnemyTurn$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        MutableStateFlow mutableStateFlow;
        MutableStateFlow mutableStateFlow2;
        MutableStateFlow mutableStateFlow3;
        MutableStateFlow mutableStateFlow4;
        MutableStateFlow mutableStateFlow5;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                if (DelayKt.delay(1000L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        mutableStateFlow = this.this$0._spaceCombat;
        SpaceCombatState combat = (SpaceCombatState) mutableStateFlow.getValue();
        if (combat.isCombatActive() && !combat.getBattleOver() && !combat.getPlayerTurn()) {
            mutableStateFlow2 = this.this$0._starshipState;
            StarshipState currentShip = (StarshipState) mutableStateFlow2.getValue();
            Integer num = currentShip.getSystemPowerAllocation().get(ShipSystem.SHIELDS);
            int shieldsAlloc = num != null ? num.intValue() : 2;
            float finalEnemyAtk = Math.max(5.0f, combat.getEnemyWeaponPower() - (shieldsAlloc * 2.0f));
            float remainingAtk = finalEnemyAtk;
            float playerShield = currentShip.getShield();
            float playerHull = currentShip.getHull();
            List logs = CollectionsKt.toMutableList((Collection) combat.getCombatLogs());
            if (playerShield > 0.0f) {
                float absorbed = Math.min(playerShield, remainingAtk);
                playerShield -= absorbed;
                remainingAtk -= absorbed;
                logs.add("⚠️ Enemy fired energy cannons! Absorbed " + absorbed + " by shield.");
            }
            if (remainingAtk > 0.0f) {
                playerHull = Math.max(0.0f, playerHull - remainingAtk);
                logs.add("⚠️ Enemy laser breached your armor! Dealt " + remainingAtk + " direct hull damage.");
            }
            mutableStateFlow3 = this.this$0._starshipState;
            float playerShield2 = playerShield;
            float playerShield3 = playerHull;
            mutableStateFlow3.setValue(StarshipState.copy$default(currentShip, null, playerShield3, 0.0f, playerShield2, 0.0f, 0.0f, 0.0f, null, null, null, null, null, null, 0, 0, null, 65525, null));
            GameViewModel gameViewModel = this.this$0;
            if (playerShield3 <= 0.0f) {
                mutableStateFlow5 = gameViewModel._spaceCombat;
                mutableStateFlow5.setValue(SpaceCombatState.copy$default(combat, false, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, true, CollectionsKt.plus((Collection<? extends String>) logs, "💀 DEFEAT: Ship systems critical! Autopilot has emergency-warped back to Solis Prime at the cost of credits."), 0, 0, true, false, 1663, null));
            } else {
                mutableStateFlow4 = gameViewModel._spaceCombat;
                mutableStateFlow4.setValue(SpaceCombatState.copy$default(combat, false, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, true, logs, 0, 0, false, false, 7807, null));
            }
            return Unit.INSTANCE;
        }
        return Unit.INSTANCE;
    }
}
