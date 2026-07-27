package com.example.game.viewmodel;

import androidx.compose.runtime.ComposerKt;
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
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$levelUpCompanion$1", f = "GameViewModel.kt", i = {1, 1, 1}, l = {188, ComposerKt.invocationKey}, m = "invokeSuspend", n = {"current", "updatedCompanions", "updated"}, s = {"L$0", "L$1", "L$2"})
/* loaded from: classes4.dex */
public final class GameViewModel$levelUpCompanion$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $companionName;
    final /* synthetic */ int $creditsCost;
    final /* synthetic */ int $nanitesCost;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$levelUpCompanion$1(GameViewModel gameViewModel, int i, int i2, String str, Continuation<? super GameViewModel$levelUpCompanion$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$creditsCost = i;
        this.$nanitesCost = i2;
        this.$companionName = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$levelUpCompanion$1(this.this$0, this.$creditsCost, this.$nanitesCost, this.$companionName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$levelUpCompanion$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0009. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0112 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0113  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r30) {
        /*
            Method dump skipped, instructions count: 294
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.viewmodel.GameViewModel$levelUpCompanion$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
