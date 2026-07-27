package com.example.game.viewmodel;

import androidx.core.app.FrameMetricsAggregator;
import com.example.game.models.BattleLog;
import com.example.game.models.BattleState;
import com.example.game.models.ParallelEarth;
import com.example.game.models.StarSystem;
import com.example.game.models.StarshipState;
import java.util.ArrayList;
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
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$jumpToSystem$1", f = "GameViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes4.dex */
public final class GameViewModel$jumpToSystem$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ StarshipState $currentShip;
    final /* synthetic */ int $fuelNeeded;
    final /* synthetic */ String $systemId;
    final /* synthetic */ StarSystem $targetSystem;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$jumpToSystem$1(GameViewModel gameViewModel, StarshipState starshipState, int i, String str, StarSystem starSystem, Continuation<? super GameViewModel$jumpToSystem$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$currentShip = starshipState;
        this.$fuelNeeded = i;
        this.$systemId = str;
        this.$targetSystem = starSystem;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$jumpToSystem$1(this.this$0, this.$currentShip, this.$fuelNeeded, this.$systemId, this.$targetSystem, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$jumpToSystem$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        MutableStateFlow mutableStateFlow;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                mutableStateFlow = this.this$0._starshipState;
                mutableStateFlow.setValue(StarshipState.copy$default(this.$currentShip, null, 0.0f, 0.0f, 0.0f, 0.0f, this.$currentShip.getFuel() - this.$fuelNeeded, 0.0f, null, null, null, null, null, this.$systemId, 0, 0, null, 61407, null));
                GameViewModel gameViewModel = this.this$0;
                ParallelEarth associatedReality = this.$targetSystem.getAssociatedReality();
                final StarshipState starshipState = this.$currentShip;
                final GameViewModel gameViewModel2 = this.this$0;
                final StarSystem starSystem = this.$targetSystem;
                final String str = this.$systemId;
                gameViewModel.startWarpShift(associatedReality, new Function0() { // from class: com.example.game.viewmodel.GameViewModel$jumpToSystem$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return GameViewModel$jumpToSystem$1.invokeSuspend$lambda$1(StarshipState.this, gameViewModel2, starSystem, str);
                    }
                });
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final Unit invokeSuspend$lambda$1(StarshipState $currentShip, GameViewModel this$0, StarSystem $targetSystem, String $systemId) {
        MutableStateFlow mutableStateFlow;
        MutableStateFlow mutableStateFlow2;
        MutableStateFlow mutableStateFlow3;
        MutableStateFlow mutableStateFlow4;
        MutableStateFlow mutableStateFlow5;
        String str;
        Iterable<StarSystem> starSystems = $currentShip.getStarSystems();
        Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(starSystems, 10));
        for (StarSystem starSystem : starSystems) {
            if (Intrinsics.areEqual(starSystem.getId(), $systemId)) {
                starSystem = StarSystem.copy$default(starSystem, null, null, 0.0f, 0.0f, null, null, null, 0.0f, null, true, FrameMetricsAggregator.EVERY_DURATION, null);
            }
            arrayList.add(starSystem);
        }
        List updatedSystems = (List) arrayList;
        mutableStateFlow = this$0._starshipState;
        mutableStateFlow2 = this$0._starshipState;
        mutableStateFlow.setValue(StarshipState.copy$default((StarshipState) mutableStateFlow2.getValue(), null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, null, null, null, updatedSystems, null, 0, 0, null, 63487, null));
        mutableStateFlow3 = this$0._battleState;
        mutableStateFlow4 = this$0._battleState;
        BattleState battleState = (BattleState) mutableStateFlow4.getValue();
        mutableStateFlow5 = this$0._battleState;
        mutableStateFlow3.setValue(BattleState.copy$default(battleState, false, 0, 0, 0, 0, null, CollectionsKt.plus((Collection<? extends BattleLog>) ((BattleState) mutableStateFlow5.getValue()).getLogs(), new BattleLog("INTERSTELLAR JUMP: Successfully traveled to " + $targetSystem.getName() + "! System affiliation: " + $targetSystem.getFactionAffiliation() + ".", false, $targetSystem.getAssociatedReality().getPrimaryColor(), null)), 0, false, 0, 0, false, false, 8127, null));
        if (Random.INSTANCE.nextInt(100) < 40) {
            if ($targetSystem.getAssociatedReality() == ParallelEarth.VOID_CORE) {
                str = "Dimensional Rift Leviathan";
            } else {
                str = "Technopunk Syndicate Dreadnought";
            }
            this$0.startSpaceCombat(str);
        }
        return Unit.INSTANCE;
    }
}
