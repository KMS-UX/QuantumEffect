package com.example.game.viewmodel;

import androidx.core.app.NotificationManagerCompat;
import com.example.game.db.GameState;
import com.example.game.models.DeployedStructure;
import com.example.game.models.Faction;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;

import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */

@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$deployArchitecture$1", f = "GameViewModel.kt", i = {1, 1, 1, 1, 1, 1, 1, 1}, l = {472, 497}, m = "invokeSuspend", n = {"current", "updatedReps", "updatedStructures", "updated", "finalXp", "finalLevel", "requiredXp", "currentRep"}, s = {"L$0", "L$1", "L$2", "L$3", "I$0", "I$1", "I$2", "I$3"})
/* loaded from: classes4.dex */
public final class GameViewModel$deployArchitecture$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ int $creditsCost;
    final /* synthetic */ Faction $faction;
    final /* synthetic */ int $nanitesCost;
    final /* synthetic */ int $repIncrease;
    final /* synthetic */ DeployedStructure $structure;
    final /* synthetic */ int $xpGain;
    int I$0;
    int I$1;
    int I$2;
    int I$3;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$deployArchitecture$1(GameViewModel gameViewModel, int i, int i2, int i3, Faction faction, int i4, DeployedStructure deployedStructure, Continuation<? super GameViewModel$deployArchitecture$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$creditsCost = i;
        this.$nanitesCost = i2;
        this.$xpGain = i3;
        this.$faction = faction;
        this.$repIncrease = i4;
        this.$structure = deployedStructure;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$deployArchitecture$1(this.this$0, this.$creditsCost, this.$nanitesCost, this.$xpGain, this.$faction, this.$repIncrease, this.$structure, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$deployArchitecture$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Object gameState;
        GameState current;
        int finalXp;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                gameState = this.this$0.repository.getGameState(this);
                if (gameState == coroutine_suspended) {
                    return coroutine_suspended;
                }
                current = (GameState) gameState;
                if (current.getCredits() >= this.$creditsCost || current.getNanites() < this.$nanitesCost) {
                    return Unit.INSTANCE;
                }
                int finalXp2 = current.getXp() + this.$xpGain;
                int finalLevel = current.getLevel();
                int requiredXp = finalLevel * 500;
                if (finalXp2 < requiredXp) {
                    finalXp = finalXp2;
                } else {
                    finalLevel++;
                    finalXp = finalXp2 - requiredXp;
                }
                Map updatedReps = MapsKt.toMutableMap(current.getFactionReputations());
                Integer num = (Integer) updatedReps.get(this.$faction);
                int currentRep = num != null ? num.intValue() : 0;
                updatedReps.put(this.$faction, Boxing.boxInt(RangesKt.coerceIn(this.$repIncrease + currentRep, NotificationManagerCompat.IMPORTANCE_UNSPECIFIED, 1000)));
                List updatedStructures = CollectionsKt.plus((Collection<? extends DeployedStructure>) current.getDeployedStructures(), this.$structure);
                int requiredXp2 = finalLevel;
                GameState updated = GameState.copy$default(current, null, requiredXp2, finalXp, current.getCredits() - this.$creditsCost, current.getNanites() - this.$nanitesCost, 0, 0, 0, 0, null, null, null, null, updatedReps, null, null, updatedStructures, null, null, 450529, null);
                this.L$0 = current;
                this.L$1 = updatedReps;
                this.L$2 = updatedStructures;
                this.L$3 = updated;
                this.I$0 = finalXp;
                this.I$1 = requiredXp2;
                this.I$2 = requiredXp;
                this.I$3 = currentRep;
                this.label = 2;
                return this.this$0.repository.saveGameState(updated, this) == coroutine_suspended ? coroutine_suspended : Unit.INSTANCE;
            case 1:
                ResultKt.throwOnFailure($result);
                gameState = $result;
                current = (GameState) gameState;
                if (current.getCredits() >= this.$creditsCost) {
                    break;
                }
                return Unit.INSTANCE;
            case 2:
                int i = this.I$3;
                int i2 = this.I$2;
                int i3 = this.I$1;
                int i4 = this.I$0;
                ResultKt.throwOnFailure($result);
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
