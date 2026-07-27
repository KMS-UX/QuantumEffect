package com.example.game.viewmodel;

import com.example.game.models.ActiveSkill;
import com.example.game.models.BattleState;
import com.example.game.models.Enemy;
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
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$executePlayerSkill$1", f = "GameViewModel.kt", i = {1, 1, 1, 1, 1}, l = {908, 962}, m = "invokeSuspend", n = {"currentSave", "newLogs", "updatedEnemy", "finalPlayerHp", "finalPlayerMp"}, s = {"L$0", "L$1", "L$2", "I$0", "I$1"})
/* loaded from: classes4.dex */
public final class GameViewModel$executePlayerSkill$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Enemy $enemy;
    final /* synthetic */ ActiveSkill $skill;
    final /* synthetic */ BattleState $state;
    int I$0;
    int I$1;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* compiled from: GameViewModel.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    /* loaded from: classes4.dex */
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ActiveSkill.values().length];
            try {
                iArr[ActiveSkill.QUANTUM_BURST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[ActiveSkill.TIME_STEP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[ActiveSkill.PHASE_SHIELD.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[ActiveSkill.RESONANCE_WAVE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[ActiveSkill.GRAVITY_WELL.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[ActiveSkill.HEALING_PULSE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[ActiveSkill.VOID_TOUCH.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[ActiveSkill.TEMPORAL_LOOP.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[ActiveSkill.ENERGY_OVERLOAD.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$executePlayerSkill$1(GameViewModel gameViewModel, BattleState battleState, ActiveSkill activeSkill, Enemy enemy, Continuation<? super GameViewModel$executePlayerSkill$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$state = battleState;
        this.$skill = activeSkill;
        this.$enemy = enemy;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$executePlayerSkill$1(this.this$0, this.$state, this.$skill, this.$enemy, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$executePlayerSkill$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:11:0x00b7. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x02b7  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r35) {
        /*
            Method dump skipped, instructions count: 980
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.viewmodel.GameViewModel$executePlayerSkill$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
