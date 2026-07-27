package com.example.game.viewmodel;

import com.example.game.models.Faction;
import java.util.Map;
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
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$modifyFactionReputations$1", f = "GameViewModel.kt", i = {1, 1, 1}, l = {1414, 1423}, m = "invokeSuspend", n = {"current", "updatedReps", "updated"}, s = {"L$0", "L$1", "L$2"})
/* loaded from: classes4.dex */
public final class GameViewModel$modifyFactionReputations$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $dialogueLogText;
    final /* synthetic */ Map<Faction, Integer> $repChanges;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$modifyFactionReputations$1(GameViewModel gameViewModel, Map<Faction, Integer> map, String str, Continuation<? super GameViewModel$modifyFactionReputations$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$repChanges = map;
        this.$dialogueLogText = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$modifyFactionReputations$1(this.this$0, this.$repChanges, this.$dialogueLogText, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$modifyFactionReputations$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:13:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00de A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00df  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r26) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.viewmodel.GameViewModel$modifyFactionReputations$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
