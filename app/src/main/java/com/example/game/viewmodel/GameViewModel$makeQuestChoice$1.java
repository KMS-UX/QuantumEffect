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
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$makeQuestChoice$1", f = "GameViewModel.kt", i = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, l = {1185, 1354}, m = "invokeSuspend", n = {"current", "quest", "lyraBondChange", "nixBondChange", "elsiBondChange", "droxBondChange", "grumBondChange", "vaynBondChange", "notificationText", "recruitDrox", "updatedQuests", "updatedCompanions", "updatedReps", "updated", "creditReward", "naniteReward", "xpReward", "repReward", "aurelianRepChange", "emberpactRepChange", "ironwardRepChange", "silentVeilRepChange", "finalXp", "finalLevel", "requiredXp"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$6", "L$7", "L$8", "L$9", "L$10", "L$11", "L$12", "L$13", "I$0", "I$1", "I$2", "I$3", "I$4", "I$5", "I$6", "I$7", "I$8", "I$9", "I$10"})
/* loaded from: classes4.dex */
public final class GameViewModel$makeQuestChoice$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ int $choiceIndex;
    final /* synthetic */ String $questId;
    int I$0;
    int I$1;
    int I$10;
    int I$2;
    int I$3;
    int I$4;
    int I$5;
    int I$6;
    int I$7;
    int I$8;
    int I$9;
    Object L$0;
    Object L$1;
    Object L$10;
    Object L$11;
    Object L$12;
    Object L$13;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    Object L$6;
    Object L$7;
    Object L$8;
    Object L$9;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$makeQuestChoice$1(GameViewModel gameViewModel, String str, int i, Continuation<? super GameViewModel$makeQuestChoice$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$questId = str;
        this.$choiceIndex = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$makeQuestChoice$1(this.this$0, this.$questId, this.$choiceIndex, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$makeQuestChoice$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:123:0x020a. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:21:0x0125. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0009. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x00ce A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0330  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x039c  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x047c A[SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r69) {
        /*
            Method dump skipped, instructions count: 1798
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.viewmodel.GameViewModel$makeQuestChoice$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
