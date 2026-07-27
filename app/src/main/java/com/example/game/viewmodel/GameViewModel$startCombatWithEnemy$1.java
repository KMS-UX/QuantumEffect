package com.example.game.viewmodel;

import androidx.compose.ui.graphics.Color;
import com.example.game.db.GameState;
import com.example.game.models.BattleLog;
import com.example.game.models.BattleState;
import com.example.game.models.Enemy;
import com.example.ui.theme.ColorKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$startCombatWithEnemy$1", f = "GameViewModel.kt", i = {}, l = {773}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes4.dex */
public final class GameViewModel$startCombatWithEnemy$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $dangerLevel;
    final /* synthetic */ String $enemyName;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$startCombatWithEnemy$1(GameViewModel gameViewModel, String str, String str2, Continuation<? super GameViewModel$startCombatWithEnemy$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$dangerLevel = str;
        this.$enemyName = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$startCombatWithEnemy$1(this.this$0, this.$dangerLevel, this.$enemyName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$startCombatWithEnemy$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Object gameState;
        int hp;
        int atk;
        int def;
        MutableStateFlow mutableStateFlow;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                gameState = this.this$0.repository.getGameState(this);
                if (gameState == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                ResultKt.throwOnFailure($result);
                gameState = $result;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        GameState current = (GameState) gameState;
        int maxHp = current.getMaxHealth();
        int maxMp = current.getMaxMp();
        String str = this.$dangerLevel;
        switch (str.hashCode()) {
            case -1821856108:
                if (str.equals("Severe")) {
                    hp = 400;
                    break;
                }
                hp = 900;
                break;
            case -554213085:
                if (str.equals("Moderate")) {
                    hp = 250;
                    break;
                }
                hp = 900;
                break;
            case 74348437:
                if (str.equals("Minor")) {
                    hp = 150;
                    break;
                }
                hp = 900;
                break;
            case 2016795583:
                if (str.equals("Critical")) {
                    hp = 600;
                    break;
                }
                hp = 900;
                break;
            default:
                hp = 900;
                break;
        }
        String str2 = this.$dangerLevel;
        switch (str2.hashCode()) {
            case -1821856108:
                if (str2.equals("Severe")) {
                    atk = 50;
                    break;
                }
                atk = 90;
                break;
            case -554213085:
                if (str2.equals("Moderate")) {
                    atk = 35;
                    break;
                }
                atk = 90;
                break;
            case 74348437:
                if (str2.equals("Minor")) {
                    atk = 20;
                    break;
                }
                atk = 90;
                break;
            case 2016795583:
                if (str2.equals("Critical")) {
                    atk = 70;
                    break;
                }
                atk = 90;
                break;
            default:
                atk = 90;
                break;
        }
        String str3 = this.$dangerLevel;
        switch (str3.hashCode()) {
            case -1821856108:
                if (str3.equals("Severe")) {
                    def = 40;
                    break;
                }
                def = 75;
                break;
            case -554213085:
                if (str3.equals("Moderate")) {
                    def = 25;
                    break;
                }
                def = 75;
                break;
            case 74348437:
                if (str3.equals("Minor")) {
                    def = 10;
                    break;
                }
                def = 75;
                break;
            case 2016795583:
                if (str3.equals("Critical")) {
                    def = 55;
                    break;
                }
                def = 75;
                break;
            default:
                def = 75;
                break;
        }
        Enemy enemy = new Enemy(this.$enemyName, hp, hp, atk, def, 40, "Corrupted high-threat hazard warping physical matter with localized quantum radiation.", Intrinsics.areEqual(this.$dangerLevel, "Apocalyptic") ? "Void" : "Kinetic", "Quantum Energy & Critical Strikes", "Overcharged Resonator", null, 1024, null);
        mutableStateFlow = this.this$0._battleState;
        mutableStateFlow.setValue(new BattleState(true, maxHp, maxHp, maxMp, maxMp, enemy, CollectionsKt.listOf((Object[]) new BattleLog[]{new BattleLog("ALERT: Spatial tear detected! Encounted hostile threat: " + this.$enemyName + " (" + this.$dangerLevel + ")!", false, ColorKt.getQuantumNeonRed(), null), new BattleLog("Tactical advisory: Analyze vulnerabilities and cycle active augments.", false, Color.INSTANCE.m4191getLightGray0d7_KjU(), null)}), 1, true, hp / 2, hp, false, false));
        return Unit.INSTANCE;
    }
}
