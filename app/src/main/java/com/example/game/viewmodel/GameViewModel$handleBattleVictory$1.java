package com.example.game.viewmodel;

import androidx.core.view.InputDeviceCompat;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */
@Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel", f = "GameViewModel.kt", i = {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1}, l = {InputDeviceCompat.SOURCE_GAMEPAD, 1040}, m = "handleBattleVictory", n = {"state", "enemy", "logs", "state", "enemy", "logs", "currentSave", "updated", "finalXp", "finalLevel", "requiredXp"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3", "L$4", "I$0", "I$1", "I$2"})
/* loaded from: classes4.dex */
public final class GameViewModel$handleBattleVictory$1 extends ContinuationImpl {
    int I$0;
    int I$1;
    int I$2;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$handleBattleVictory$1(GameViewModel gameViewModel, Continuation<? super GameViewModel$handleBattleVictory$1> continuation) {
        super(continuation);
        this.this$0 = gameViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object handleBattleVictory;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        handleBattleVictory = this.this$0.handleBattleVictory(null, null, null, this);
        return handleBattleVictory;
    }
}
