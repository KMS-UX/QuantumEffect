package com.example.game.api;


import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GeminiApi.kt */

@DebugMetadata(c = "com.example.game.api.GeminiServiceHelper", f = "GeminiApi.kt", i = {0, 0, 0, 0}, l = {189}, m = "generateSearchGroundedContent", n = {"prompt", "systemPrompt", "apiKey", "request"}, s = {"L$0", "L$1", "L$2", "L$3"})
/* loaded from: classes6.dex */
public final class GeminiServiceHelper$generateSearchGroundedContent$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ GeminiServiceHelper this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GeminiServiceHelper$generateSearchGroundedContent$1(GeminiServiceHelper geminiServiceHelper, Continuation<? super GeminiServiceHelper$generateSearchGroundedContent$1> continuation) {
        super(continuation);
        this.this$0 = geminiServiceHelper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.generateSearchGroundedContent(null, null, this);
    }
}
