package com.example.game.viewmodel;

import com.example.game.models.Faction;
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
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$purchaseFactionItem$1", f = "GameViewModel.kt", i = {1, 1, 1, 1, 1, 1, 1}, l = {1369, 1399}, m = "invokeSuspend", n = {"current", "faction", "itemId", "newItem", "updatedInventory", "updatedState", "currentRep"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "I$0"})
/* loaded from: classes4.dex */
public final class GameViewModel$purchaseFactionItem$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $factionName;
    final /* synthetic */ String $itemName;
    final /* synthetic */ int $price;
    final /* synthetic */ int $requiredRep;
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* compiled from: GameViewModel.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    /* loaded from: classes4.dex */
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Faction.values().length];
            try {
                iArr[Faction.AURELIAN_ORDER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Faction.EMBERPACT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Faction.SILENT_VEIL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$purchaseFactionItem$1(GameViewModel gameViewModel, String str, int i, int i2, String str2, Continuation<? super GameViewModel$purchaseFactionItem$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$factionName = str;
        this.$price = i;
        this.$requiredRep = i2;
        this.$itemName = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$purchaseFactionItem$1(this.this$0, this.$factionName, this.$price, this.$requiredRep, this.$itemName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$purchaseFactionItem$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0008. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0159 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0069  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r30) {
        /*
            Method dump skipped, instructions count: 514
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.viewmodel.GameViewModel$purchaseFactionItem$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
