package com.example.game.viewmodel;

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
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$addResources$1", f = "GameViewModel.kt", i = {1, 1, 1, 1, 1, 1, 1, 1, 1}, l = {345, 374}, m = "invokeSuspend", n = {"current", "updated", "finalXp", "finalLevel", "requiredXp", "finalMaxHealth", "finalMaxMp", "finalHealth", "finalMp"}, s = {"L$0", "L$1", "I$0", "I$1", "I$2", "I$3", "I$4", "I$5", "I$6"})
/* loaded from: classes4.dex */
public final class GameViewModel$addResources$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ int $credits;
    final /* synthetic */ int $nanites;
    final /* synthetic */ int $xp;
    int I$0;
    int I$1;
    int I$2;
    int I$3;
    int I$4;
    int I$5;
    int I$6;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$addResources$1(GameViewModel gameViewModel, int i, int i2, int i3, Continuation<? super GameViewModel$addResources$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$xp = i;
        this.$credits = i2;
        this.$nanites = i3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$addResources$1(this.this$0, this.$xp, this.$credits, this.$nanites, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$addResources$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0073 A[LOOP:0: B:11:0x0071->B:12:0x0073, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00e3 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00e4  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r26) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.viewmodel.GameViewModel$addResources$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
