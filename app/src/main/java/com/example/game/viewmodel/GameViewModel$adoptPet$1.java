package com.example.game.viewmodel;

import com.example.game.db.GameState;
import com.example.game.models.DeployedStructure;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;

import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */

@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$adoptPet$1", f = "GameViewModel.kt", i = {1, 1, 1, 1, 1}, l = {557, 583}, m = "invokeSuspend", n = {"current", "newPet", "updatedStructures", "updated", "alreadyAdopted"}, s = {"L$0", "L$1", "L$2", "L$3", "I$0"})
/* loaded from: classes4.dex */
public final class GameViewModel$adoptPet$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ int $creditsCost;
    final /* synthetic */ String $emoji;
    final /* synthetic */ String $name;
    final /* synthetic */ int $nanitesCost;
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$adoptPet$1(GameViewModel gameViewModel, int i, int i2, String str, String str2, Continuation<? super GameViewModel$adoptPet$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$creditsCost = i;
        this.$nanitesCost = i2;
        this.$name = str;
        this.$emoji = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$adoptPet$1(this.this$0, this.$creditsCost, this.$nanitesCost, this.$name, this.$emoji, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$adoptPet$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0009. Please report as an issue. */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Object gameState;
        GameState current;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = 1;
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
                Iterable deployedStructures = current.getDeployedStructures();
                String str = this.$name;
                if ((deployedStructures instanceof Collection) && ((Collection) deployedStructures).isEmpty()) {
                    i = 0;
                } else {
                    Iterator it = deployedStructures.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            DeployedStructure deployedStructure = (DeployedStructure) it.next();
                            if (((Intrinsics.areEqual(deployedStructure.getType(), "PET_ADOPTED") && Intrinsics.areEqual(deployedStructure.getName(), str)) ? 1 : null) != null) {
                            }
                        } else {
                            i = 0;
                        }
                    }
                }
                if (i != 0) {
                    return Unit.INSTANCE;
                }
                DeployedStructure newPet = new DeployedStructure(System.nanoTime(), "PET_ADOPTED", this.$name, this.$emoji, "Colony Base", 0.0f, 0.0f, 4283611135L, false, 1);
                List updatedStructures = CollectionsKt.plus((Collection<? extends DeployedStructure>) current.getDeployedStructures(), newPet);
                GameState updated = GameState.copy$default(current, null, 0, 0, current.getCredits() - this.$creditsCost, current.getNanites() - this.$nanitesCost, 0, 0, 0, 0, null, null, null, null, null, null, null, updatedStructures, null, null, 458727, null);
                this.L$0 = current;
                this.L$1 = newPet;
                this.L$2 = updatedStructures;
                this.L$3 = updated;
                this.I$0 = i;
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
                int i2 = this.I$0;
                ResultKt.throwOnFailure($result);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
