package com.example.game.viewmodel;

import com.example.game.api.GeminiServiceHelper;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;

import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */

@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$transmitRadioMessage$1", f = "GameViewModel.kt", i = {0, 0, 0}, l = {1154}, m = "invokeSuspend", n = {"userMsg", "systemInstruction", "respondentName"}, s = {"L$0", "L$1", "L$2"})
/* loaded from: classes4.dex */
public final class GameViewModel$transmitRadioMessage$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ float $frequency;
    final /* synthetic */ String $userMessage;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$transmitRadioMessage$1(String str, float f, GameViewModel gameViewModel, Continuation<? super GameViewModel$transmitRadioMessage$1> continuation) {
        super(2, continuation);
        this.$userMessage = str;
        this.$frequency = f;
        this.this$0 = gameViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$transmitRadioMessage$1(this.$userMessage, this.$frequency, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$transmitRadioMessage$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        MutableStateFlow mutableStateFlow;
        MutableStateFlow mutableStateFlow2;
        MutableStateFlow mutableStateFlow3;
        String systemInstruction;
        String respondentName;
        Object generateHighThinkingContent;
        String respondentName2;
        MutableStateFlow mutableStateFlow4;
        MutableStateFlow mutableStateFlow5;
        MutableStateFlow mutableStateFlow6;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                String str = this.$userMessage;
                String format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                RadioMessage userMsg = new RadioMessage("DIRECTOR (YOU)", str, format, this.$frequency);
                mutableStateFlow = this.this$0._radioTransmissionLog;
                mutableStateFlow2 = this.this$0._radioTransmissionLog;
                mutableStateFlow.setValue(CollectionsKt.plus((Collection<? extends RadioMessage>) mutableStateFlow2.getValue(), userMsg));
                mutableStateFlow3 = this.this$0._isTransmitting;
                mutableStateFlow3.setValue(Boxing.boxBoolean(true));
                float f = this.$frequency;
                if (f == 144.8f) {
                    systemInstruction = "You are the high-ranking command strategist of the Enlighteners Faction.\nYou respond with precise, elegant, high-tech transhumanist philosophy, strategic geometric military formations, dry mechanical logic, and chemical efficiency.\nFormat your response in bullet points or numbered logs. Do not use generic commercial or marketing talk. Keep it cold, smart, and militaristic.";
                } else {
                    if (f == 98.2f) {
                        systemInstruction = "You are a scrappy, hyperactive, fast-talking street hacker of the Technopunks faction.\nYou respond with street tech slang, chaotic code injections, overclocking instructions, field jury-rigging solutions, and active rebellion tactics.\nUse capitalized expressions, sound effects like [STATIC], and colorful energetic speech. Keep it street-smart and highly reactive.";
                    } else {
                        if (f == 404.0f) {
                            systemInstruction = "You are 'Spectre', a mysterious, ancient, deep-grid rogue AI entity that drifts across forbidden military frequencies.\nYou respond in highly cryptic, poetic, fragmented machine diagnostics, quantum probability matrices, cosmic-cybernetic formulas, and eerie warnings.\nFormat with broken terminal prompts, binary snippets, or eerie metaphors. Speak as an omnipresent entity.";
                        } else {
                            systemInstruction = "You are an unknown encrypted tactical frequency. Provide basic military intelligence.";
                        }
                    }
                }
                float f2 = this.$frequency;
                if (f2 == 144.8f) {
                    respondentName = "ENLIGHTENERS HQ [144.8 MHz]";
                } else {
                    if (f2 == 98.2f) {
                        respondentName = "TECHNOPUNK HACKER REBELS [98.2 MHz]";
                    } else {
                        respondentName = (f2 > 404.0f ? 1 : (f2 == 404.0f ? 0 : -1)) == 0 ? "SPECTRE CORRUPT-AI [404.0 MHz]" : "UNKNOWN BROADCASTER";
                    }
                }
                this.L$0 = userMsg;
                this.L$1 = systemInstruction;
                this.L$2 = respondentName;
                this.label = 1;
                generateHighThinkingContent = GeminiServiceHelper.INSTANCE.generateHighThinkingContent(this.$userMessage, systemInstruction, this);
                if (generateHighThinkingContent == coroutine_suspended) {
                    return coroutine_suspended;
                }
                respondentName2 = respondentName;
                break;
            case 1:
                respondentName2 = (String) this.L$2;
                ResultKt.throwOnFailure($result);
                generateHighThinkingContent = $result;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        String aiResponse = (String) generateHighThinkingContent;
        String format2 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
        RadioMessage aiMsg = new RadioMessage(respondentName2, aiResponse, format2, this.$frequency);
        mutableStateFlow4 = this.this$0._radioTransmissionLog;
        mutableStateFlow5 = this.this$0._radioTransmissionLog;
        mutableStateFlow4.setValue(CollectionsKt.plus((Collection<? extends RadioMessage>) mutableStateFlow5.getValue(), aiMsg));
        mutableStateFlow6 = this.this$0._isTransmitting;
        mutableStateFlow6.setValue(Boxing.boxBoolean(false));
        return Unit.INSTANCE;
    }
}
