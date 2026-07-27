package com.example.game.viewmodel;

import com.example.game.db.GameState;
import com.example.game.models.InventoryItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameViewModel.kt */

@DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$craftItem$1", f = "GameViewModel.kt", i = {1, 1, 1, 1, 1, 1, 1}, l = {639, 704}, m = "invokeSuspend", n = {"current", "updatedInventory", "updated", "existingIndex", "finalXp", "finalLevel", "requiredXp"}, s = {"L$0", "L$1", "L$2", "I$0", "I$1", "I$2", "I$3"})
/* loaded from: classes4.dex */
public final class GameViewModel$craftItem$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ int $atkBonus;
    final /* synthetic */ int $creditsCost;
    final /* synthetic */ int $defBonus;
    final /* synthetic */ int $hpBonus;
    final /* synthetic */ boolean $isConsumable;
    final /* synthetic */ boolean $isEquippable;
    final /* synthetic */ List<Pair<String, Integer>> $materialsNeeded;
    final /* synthetic */ int $mpBonus;
    final /* synthetic */ int $nanitesCost;
    final /* synthetic */ String $resultCategory;
    final /* synthetic */ String $resultDesc;
    final /* synthetic */ String $resultItemName;
    final /* synthetic */ String $resultSymbol;
    int I$0;
    int I$1;
    int I$2;
    int I$3;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ GameViewModel this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel$craftItem$1(GameViewModel gameViewModel, int i, int i2, List<Pair<String, Integer>> list, String str, String str2, String str3, String str4, int i3, int i4, int i5, int i6, boolean z, boolean z2, Continuation<? super GameViewModel$craftItem$1> continuation) {
        super(2, continuation);
        this.this$0 = gameViewModel;
        this.$creditsCost = i;
        this.$nanitesCost = i2;
        this.$materialsNeeded = list;
        this.$resultItemName = str;
        this.$resultCategory = str2;
        this.$resultSymbol = str3;
        this.$resultDesc = str4;
        this.$hpBonus = i3;
        this.$mpBonus = i4;
        this.$atkBonus = i5;
        this.$defBonus = i6;
        this.$isConsumable = z;
        this.$isEquippable = z2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new GameViewModel$craftItem$1(this.this$0, this.$creditsCost, this.$nanitesCost, this.$materialsNeeded, this.$resultItemName, this.$resultCategory, this.$resultSymbol, this.$resultDesc, this.$hpBonus, this.$mpBonus, this.$atkBonus, this.$defBonus, this.$isConsumable, this.$isEquippable, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((GameViewModel$craftItem$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0009. Please report as an issue. */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object $result) {
        Object gameState;
        GameState current;
        GameState current2;
        int finalXp;
        InventoryItem copy;
        boolean z;
        Object obj;
        InventoryItem copy2;
        Object obj2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        boolean z2 = true;
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                gameState = this.this$0.repository.getGameState(this);
                if (gameState == coroutine_suspended) {
                    return coroutine_suspended;
                }
                current = (GameState) gameState;
                if (current.getCredits() >= this.$creditsCost || current.getNanites() < this.$nanitesCost) {
                    return Unit.INSTANCE;
                }
                for (Pair material : this.$materialsNeeded) {
                    Iterator<T> it = current.getInventory().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Object next = it.next();
                            if (Intrinsics.areEqual(((InventoryItem) next).getName(), material.getFirst())) {
                                obj2 = next;
                            }
                        } else {
                            obj2 = null;
                        }
                    }
                    InventoryItem found = (InventoryItem) obj2;
                    if (found == null || found.getQuantity() < material.getSecond().intValue()) {
                        return Unit.INSTANCE;
                    }
                }
                Iterable<InventoryItem> inventory = current.getInventory();
                List<Pair<String, Integer>> list = this.$materialsNeeded;
                Collection arrayList = new ArrayList();
                for (InventoryItem inventoryItem : inventory) {
                    Iterator<T> it2 = list.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            obj = it2.next();
                            z = z2;
                            if (!Intrinsics.areEqual(((Pair) obj).getFirst(), inventoryItem.getName())) {
                                z2 = z;
                            }
                        } else {
                            z = z2;
                            obj = null;
                        }
                    }
                    Pair pair = (Pair) obj;
                    if (pair != null) {
                        int quantity = inventoryItem.getQuantity() - ((Number) pair.getSecond()).intValue();
                        if (quantity > 0) {
                            copy2 = inventoryItem.copy((r29 & 1) != 0 ? inventoryItem.id : null, (r29 & 2) != 0 ? inventoryItem.name : null, (r29 & 4) != 0 ? inventoryItem.category : null, (r29 & 8) != 0 ? inventoryItem.iconSymbol : null, (r29 & 16) != 0 ? inventoryItem.quantity : quantity, (r29 & 32) != 0 ? inventoryItem.description : null, (r29 & 64) != 0 ? inventoryItem.statModifierDesc : null, (r29 & 128) != 0 ? inventoryItem.hpBonus : 0, (r29 & 256) != 0 ? inventoryItem.mpBonus : 0, (r29 & 512) != 0 ? inventoryItem.atkBonus : 0, (r29 & 1024) != 0 ? inventoryItem.defBonus : 0, (r29 & 2048) != 0 ? inventoryItem.isConsumable : false, (r29 & 4096) != 0 ? inventoryItem.isEquippable : false, (r29 & 8192) != 0 ? inventoryItem.isEquipped : false);
                            inventoryItem = copy2;
                        } else {
                            inventoryItem = null;
                        }
                    }
                    if (inventoryItem != null) {
                        arrayList.add(inventoryItem);
                    }
                    z2 = z;
                }
                List updatedInventory = CollectionsKt.toMutableList(arrayList);
                String str = this.$resultItemName;
                int i = 0;
                Iterator it3 = updatedInventory.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        i = -1;
                    } else if (!Intrinsics.areEqual(((InventoryItem) it3.next()).getName(), str)) {
                        i++;
                    }
                }
                int existingIndex = i;
                if (existingIndex != -1) {
                    InventoryItem existing = (InventoryItem) updatedInventory.get(existingIndex);
                    copy = existing.copy((r29 & 1) != 0 ? existing.id : null, (r29 & 2) != 0 ? existing.name : null, (r29 & 4) != 0 ? existing.category : null, (r29 & 8) != 0 ? existing.iconSymbol : null, (r29 & 16) != 0 ? existing.quantity : existing.getQuantity() + 1, (r29 & 32) != 0 ? existing.description : null, (r29 & 64) != 0 ? existing.statModifierDesc : null, (r29 & 128) != 0 ? existing.hpBonus : 0, (r29 & 256) != 0 ? existing.mpBonus : 0, (r29 & 512) != 0 ? existing.atkBonus : 0, (r29 & 1024) != 0 ? existing.defBonus : 0, (r29 & 2048) != 0 ? existing.isConsumable : false, (r29 & 4096) != 0 ? existing.isEquippable : false, (r29 & 8192) != 0 ? existing.isEquipped : false);
                    updatedInventory.set(existingIndex, copy);
                    current2 = current;
                } else {
                    current2 = current;
                    Boxing.boxBoolean(updatedInventory.add(new InventoryItem("crafted_" + System.nanoTime(), this.$resultItemName, this.$resultCategory, this.$resultSymbol, 1, this.$resultDesc, null, this.$hpBonus, this.$mpBonus, this.$atkBonus, this.$defBonus, this.$isConsumable, this.$isEquippable, false, 64, null)));
                }
                int finalXp2 = current2.getXp() + 150;
                int finalLevel = current2.getLevel();
                int requiredXp = finalLevel * 500;
                if (finalXp2 >= requiredXp) {
                    finalLevel++;
                    finalXp = finalXp2 - requiredXp;
                } else {
                    finalXp = finalXp2;
                }
                GameState current3 = current2;
                int requiredXp2 = finalLevel;
                GameState updated = GameState.copy$default(current3, null, requiredXp2, finalXp, current2.getCredits() - this.$creditsCost, current2.getNanites() - this.$nanitesCost, 0, 0, 0, 0, null, null, null, null, null, null, null, null, updatedInventory, null, 393185, null);
                this.L$0 = current3;
                this.L$1 = updatedInventory;
                this.L$2 = updated;
                this.I$0 = existingIndex;
                this.I$1 = finalXp;
                this.I$2 = requiredXp2;
                this.I$3 = requiredXp;
                this.label = 2;
                return this.this$0.repository.saveGameState(updated, this) == coroutine_suspended ? coroutine_suspended : Unit.INSTANCE;
            case 1:
                ResultKt.throwOnFailure($result);
                gameState = $result;
                current = (GameState) gameState;
                if (current.getCredits() >= this.$creditsCost) {
                    break;
                }
                return Unit.INSTANCE;
            case 2:
                int i2 = this.I$3;
                int i3 = this.I$2;
                int i4 = this.I$1;
                int i5 = this.I$0;
                ResultKt.throwOnFailure($result);
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
