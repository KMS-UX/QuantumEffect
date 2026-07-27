package com.example.game.viewmodel;

import androidx.core.app.FrameMetricsAggregator;
import com.example.game.db.GameState;
import com.example.game.models.DeployedStructure;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;

import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */

@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$trainPet$1", f = "GameViewModel.kt", i = {1, 1, 1}, l = {589, 603}, m = "invokeSuspend", n = {"current", "updatedStructures", "updated"}, s = {"L$0", "L$1", "L$2"})
/* loaded from: classes4.dex */
public final class GameViewModel$trainPet$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ int $creditsCost;
    final /* synthetic */ int $nanitesCost;
    final /* synthetic */ long $petId;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$trainPet$1(GameViewModel gameViewModel, int i, int i2, long j, Continuation<? super GameViewModel$trainPet$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$creditsCost = i;
        this.$nanitesCost = i2;
        this.$petId = j;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$trainPet$1(this.this$0, this.$creditsCost, this.$nanitesCost, this.$petId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$trainPet$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0009. Please report as an issue. */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Object gameState;
        GameState current;
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
                Iterable<DeployedStructure> deployedStructures = current.getDeployedStructures();
                long j = this.$petId;
                Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(deployedStructures, 10));
                for (DeployedStructure deployedStructure : deployedStructures) {
                    if (deployedStructure.getId() == j) {
                        deployedStructure = DeployedStructure.copy$default(deployedStructure, 0L, null, null, null, null, 0.0f, 0.0f, 0L, false, deployedStructure.getLevel() + 1, FrameMetricsAggregator.EVERY_DURATION, null);
                    }
                    arrayList.add(deployedStructure);
                }
                List updatedStructures = (List) arrayList;
                GameState updated = GameState.copy$default(current, null, 0, 0, current.getCredits() - this.$creditsCost, current.getNanites() - this.$nanitesCost, 0, 0, 0, 0, null, null, null, null, null, null, null, updatedStructures, null, null, 458727, null);
                this.L$0 = current;
                this.L$1 = updatedStructures;
                this.L$2 = updated;
                this.label = 2;
                if (this.this$0.repository.saveGameState(updated, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                return Unit.INSTANCE;
            case 1:
                ResultKt.throwOnFailure($result);
                gameState = $result;
                current = (GameState) gameState;
                if (current.getCredits() >= this.$creditsCost) {
                    break;
                }
                return Unit.INSTANCE;
            case 2:
                ResultKt.throwOnFailure($result);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
