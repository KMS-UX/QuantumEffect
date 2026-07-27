package com.example.game.viewmodel;

import com.example.game.models.ModChip;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */

@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$toggleModChip$1", f = "GameViewModel.kt", i = {1, 1, 1}, l = {254, 264}, m = "invokeSuspend", n = {"current", "updatedChips", "updated"}, s = {"L$0", "L$1", "L$2"})
/* loaded from: classes4.dex */
public final class GameViewModel$toggleModChip$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ModChip $chip;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$toggleModChip$1(GameViewModel gameViewModel, ModChip modChip, Continuation<? super GameViewModel$toggleModChip$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$chip = modChip;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$toggleModChip$1(this.this$0, this.$chip, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$toggleModChip$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00b2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005f  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r26) {
        /*
            r25 = this;
            r0 = r25
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L2a;
                case 1: goto L24;
                case 2: goto L13;
                default: goto Lb;
            }
        Lb:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        L13:
            java.lang.Object r1 = r0.L$2
            com.example.game.db.GameState r1 = (com.example.game.db.GameState) r1
            java.lang.Object r2 = r0.L$1
            java.util.List r2 = (java.util.List) r2
            java.lang.Object r3 = r0.L$0
            com.example.game.db.GameState r3 = (com.example.game.db.GameState) r3
            kotlin.ResultKt.throwOnFailure(r26)
            goto Lb6
        L24:
            kotlin.ResultKt.throwOnFailure(r26)
            r2 = r26
            goto L40
        L2a:
            kotlin.ResultKt.throwOnFailure(r26)
            com.example.game.viewmodel.GameViewModel r2 = r0.this$0
            com.example.game.db.GameRepository r2 = com.example.game.viewmodel.GameViewModel.access$getRepository$p(r2)
            r3 = r0
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r4 = 1
            r0.label = r4
            java.lang.Object r2 = r2.getGameState(r3)
            if (r2 != r1) goto L40
            return r1
        L40:
            r3 = r2
            com.example.game.db.GameState r3 = (com.example.game.db.GameState) r3
            java.util.List r2 = r3.getInstalledModChips()
            java.util.Collection r2 = (java.util.Collection) r2
            java.util.List r2 = kotlin.collections.CollectionsKt.toMutableList(r2)
            com.example.game.models.ModChip r4 = r0.$chip
            boolean r4 = r2.contains(r4)
            if (r4 == 0) goto L5f
            com.example.game.models.ModChip r4 = r0.$chip
            boolean r4 = r2.remove(r4)
            kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r4)
            goto L6b
        L5f:
            int r4 = r2.size()
            r5 = 4
            if (r4 >= r5) goto L6b
            com.example.game.models.ModChip r4 = r0.$chip
            r2.add(r4)
        L6b:
            r23 = 520191(0x7efff, float:7.28943E-40)
            r24 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            r22 = 0
            r16 = r2
            com.example.game.db.GameState r2 = com.example.game.db.GameState.copy$default(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            com.example.game.viewmodel.GameViewModel r4 = r0.this$0
            com.example.game.db.GameRepository r4 = com.example.game.viewmodel.GameViewModel.access$getRepository$p(r4)
            r5 = r0
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            java.lang.Object r6 = kotlin.coroutines.jvm.internal.r3
            r0.L$0 = r6
            java.lang.Object r6 = kotlin.coroutines.jvm.internal.r16
            r0.L$1 = r6
            java.lang.Object r6 = kotlin.coroutines.jvm.internal.r2
            r0.L$2 = r6
            r6 = 2
            r0.label = r6
            java.lang.Object r4 = r4.saveGameState(r2, r5)
            if (r4 != r1) goto Lb3
            return r1
        Lb3:
            r1 = r2
            r2 = r16
        Lb6:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.viewmodel.GameViewModel$toggleModChip$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
