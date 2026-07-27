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
@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$addItemToInventory$1", f = "GameViewModel.kt", i = {1, 1, 1, 1}, l = {392, 417}, m = "invokeSuspend", n = {"current", "existing", "updatedInventory", "updated"}, s = {"L$0", "L$1", "L$2", "L$3"})
/* loaded from: classes4.dex */
public final class GameViewModel$addItemToInventory$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ int $amount;
    final /* synthetic */ int $atkBonus;
    final /* synthetic */ String $category;
    final /* synthetic */ int $defBonus;
    final /* synthetic */ String $description;
    final /* synthetic */ int $hpBonus;
    final /* synthetic */ boolean $isConsumable;
    final /* synthetic */ boolean $isEquippable;
    final /* synthetic */ int $mpBonus;
    final /* synthetic */ String $name;
    final /* synthetic */ String $symbol;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$addItemToInventory$1(GameViewModel gameViewModel, String str, String str2, String str3, int i, String str4, int i2, int i3, int i4, int i5, boolean z, boolean z2, Continuation<? super GameViewModel$addItemToInventory$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$name = str;
        this.$category = str2;
        this.$symbol = str3;
        this.$amount = i;
        this.$description = str4;
        this.$hpBonus = i2;
        this.$mpBonus = i3;
        this.$atkBonus = i4;
        this.$defBonus = i5;
        this.$isConsumable = z;
        this.$isEquippable = z2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$addItemToInventory$1(this.this$0, this.$name, this.$category, this.$symbol, this.$amount, this.$description, this.$hpBonus, this.$mpBonus, this.$atkBonus, this.$defBonus, this.$isConsumable, this.$isEquippable, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$addItemToInventory$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0009. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x01aa A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x006c A[SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r32) {
        /*
            Method dump skipped, instructions count: 446
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.viewmodel.GameViewModel$addItemToInventory$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
