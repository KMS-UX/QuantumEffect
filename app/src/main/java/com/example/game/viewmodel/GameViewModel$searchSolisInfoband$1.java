package com.example.game.viewmodel;

import com.example.game.api.GeminiServiceHelper;
import java.util.List;

import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
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

@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$searchSolisInfoband$1", f = "GameViewModel.kt", i = {0}, l = {1102}, m = "invokeSuspend", n = {"systemInstruction"}, s = {"L$0"})
/* loaded from: classes4.dex */
public final class GameViewModel$searchSolisInfoband$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $query;
    Object L$0;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$searchSolisInfoband$1(GameViewModel gameViewModel, String str, Continuation<? super GameViewModel$searchSolisInfoband$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$query = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$searchSolisInfoband$1(this.this$0, this.$query, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$searchSolisInfoband$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        MutableStateFlow mutableStateFlow;
        MutableStateFlow mutableStateFlow2;
        MutableStateFlow mutableStateFlow3;
        Object generateSearchGroundedContent;
        MutableStateFlow mutableStateFlow4;
        MutableStateFlow mutableStateFlow5;
        MutableStateFlow mutableStateFlow6;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                mutableStateFlow = this.this$0._isSearching;
                mutableStateFlow.setValue(Boxing.boxBoolean(true));
                mutableStateFlow2 = this.this$0._searchText;
                mutableStateFlow2.setValue("");
                mutableStateFlow3 = this.this$0._searchSources;
                mutableStateFlow3.setValue(CollectionsKt.emptyList());
                this.L$0 = "You are searching the 'Solis Infoband' global parallel-earth network.\nProvide factual, grounded intelligence about the queried sci-fi concepts, cyberpunk tech, or parallel Earth occurrences.\nSynthesize search grounding query metadata into clear, structural briefings.";
                this.label = 1;
                generateSearchGroundedContent = GeminiServiceHelper.INSTANCE.generateSearchGroundedContent(this.$query, "You are searching the 'Solis Infoband' global parallel-earth network.\nProvide factual, grounded intelligence about the queried sci-fi concepts, cyberpunk tech, or parallel Earth occurrences.\nSynthesize search grounding query metadata into clear, structural briefings.", this);
                if (generateSearchGroundedContent != coroutine_suspended) {
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                generateSearchGroundedContent = $result;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Pair pair = (Pair) generateSearchGroundedContent;
        String text = (String) pair.component1();
        List sources = (List) pair.component2();
        mutableStateFlow4 = this.this$0._searchText;
        mutableStateFlow4.setValue(text);
        mutableStateFlow5 = this.this$0._searchSources;
        mutableStateFlow5.setValue(sources);
        mutableStateFlow6 = this.this$0._isSearching;
        mutableStateFlow6.setValue(Boxing.boxBoolean(false));
        return Unit.INSTANCE;
    }
}
