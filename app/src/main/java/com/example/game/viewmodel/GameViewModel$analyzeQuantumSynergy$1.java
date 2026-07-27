package com.example.game.viewmodel;

import com.example.game.api.GeminiServiceHelper;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;

import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */

@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$analyzeQuantumSynergy$1", f = "GameViewModel.kt", i = {0}, l = {1079}, m = "invokeSuspend", n = {"systemInstruction"}, s = {"L$0"})
/* loaded from: classes4.dex */
public final class GameViewModel$analyzeQuantumSynergy$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $prompt;
    Object L$0;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$analyzeQuantumSynergy$1(GameViewModel gameViewModel, String str, Continuation<? super GameViewModel$analyzeQuantumSynergy$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$prompt = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$analyzeQuantumSynergy$1(this.this$0, this.$prompt, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$analyzeQuantumSynergy$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        MutableStateFlow mutableStateFlow;
        MutableStateFlow mutableStateFlow2;
        Object generateHighThinkingContent;
        MutableStateFlow mutableStateFlow3;
        MutableStateFlow mutableStateFlow4;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                mutableStateFlow = this.this$0._isThinking;
                mutableStateFlow.setValue(Boxing.boxBoolean(true));
                mutableStateFlow2 = this.this$0._thinkingText;
                mutableStateFlow2.setValue("");
                this.L$0 = "You are the 'Quantum Effect' supercomputer 'Deus Ex Machina' analyzing parallel universe coordinate builds.\nProvide a highly detailed, professional, jargon-rich cyberpunk build optimization analysis.\nBreak down the synergies of the requested parts using precise mathematical, physical, and tactical mechanics.\nRespond in a clean terminal computer log style. No self-praising or commercial hype. Format beautifully.";
                this.label = 1;
                generateHighThinkingContent = GeminiServiceHelper.INSTANCE.generateHighThinkingContent(this.$prompt, "You are the 'Quantum Effect' supercomputer 'Deus Ex Machina' analyzing parallel universe coordinate builds.\nProvide a highly detailed, professional, jargon-rich cyberpunk build optimization analysis.\nBreak down the synergies of the requested parts using precise mathematical, physical, and tactical mechanics.\nRespond in a clean terminal computer log style. No self-praising or commercial hype. Format beautifully.", this);
                if (generateHighThinkingContent != coroutine_suspended) {
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                generateHighThinkingContent = $result;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        String result = (String) generateHighThinkingContent;
        mutableStateFlow3 = this.this$0._thinkingText;
        mutableStateFlow3.setValue(result);
        mutableStateFlow4 = this.this$0._isThinking;
        mutableStateFlow4.setValue(Boxing.boxBoolean(false));
        return Unit.INSTANCE;
    }
}
