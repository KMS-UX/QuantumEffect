package com.example.game.viewmodel;

import android.app.Application;
import androidx.compose.animation.core.AnimationConstants;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.LayoutKt;
import androidx.core.app.FrameMetricsAggregator;
import androidx.core.view.PointerIconCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelKt;
import com.example.game.api.WebSource;
import com.example.game.db.GameDatabase;
import com.example.game.db.GameRepository;
import com.example.game.db.GameState;
import com.example.game.models.ActiveSkill;
import com.example.game.models.AugmentSlot;
import com.example.game.models.BattleLog;
import com.example.game.models.BattleState;
import com.example.game.models.CrewMember;
import com.example.game.models.DeployedStructure;
import com.example.game.models.Enemy;
import com.example.game.models.EnvironmentalPuzzlesState;
import com.example.game.models.Faction;
import com.example.game.models.ModChip;
import com.example.game.models.Outfit;
import com.example.game.models.ParallelEarth;
import com.example.game.models.ShipSystem;
import com.example.game.models.ShipUpgrade;
import com.example.game.models.SpaceCombatState;
import com.example.game.models.StarSystem;
import com.example.game.models.StarshipState;
import com.example.game.models.Weapon;
import com.example.ui.theme.ColorKt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: GameViewModel.kt */
/* loaded from: classes4.dex */
public final class GameViewModel extends AndroidViewModel {
    public static final int $stable = 8;
    private final MutableStateFlow<String> _activeCompanionName;
    private final MutableStateFlow<BattleState> _battleState;
    private final MutableStateFlow<ParallelEarth> _currentParallelEarth;
    private final MutableStateFlow<Boolean> _isSearching;
    private final MutableStateFlow<Boolean> _isThinking;
    private final MutableStateFlow<Boolean> _isTransmitting;
    private final MutableStateFlow<Boolean> _isWarping;
    private final MutableStateFlow<EnvironmentalPuzzlesState> _puzzlesState;
    private final MutableStateFlow<List<RadioMessage>> _radioTransmissionLog;
    private final MutableStateFlow<List<WebSource>> _searchSources;
    private final MutableStateFlow<String> _searchText;
    private final MutableStateFlow<SpaceCombatState> _spaceCombat;
    private final MutableStateFlow<StarshipState> _starshipState;
    private final MutableStateFlow<String> _thinkingText;
    private final MutableStateFlow<Set<String>> _unlockedGates;
    private final MutableStateFlow<String> _warpMessage;
    private final MutableStateFlow<Float> _warpProgress;
    private final StateFlow<String> activeCompanionName;
    private final StateFlow<BattleState> battleState;
    private final StateFlow<ParallelEarth> currentParallelEarth;
    private final GameDatabase db;
    private final StateFlow<GameState> gameStateFlow;
    private final StateFlow<Boolean> isSearching;
    private final StateFlow<Boolean> isThinking;
    private final StateFlow<Boolean> isTransmitting;
    private final StateFlow<Boolean> isWarping;
    private final StateFlow<EnvironmentalPuzzlesState> puzzlesState;
    private final StateFlow<List<RadioMessage>> radioTransmissionLog;
    private final GameRepository repository;
    private final StateFlow<List<WebSource>> searchSources;
    private final StateFlow<String> searchText;
    private final StateFlow<SpaceCombatState> spaceCombat;
    private final StateFlow<StarshipState> starshipState;
    private final StateFlow<String> thinkingText;
    private final StateFlow<Set<String>> unlockedGates;
    private final StateFlow<String> warpMessage;
    private final StateFlow<Float> warpProgress;

    /* compiled from: GameViewModel.kt */
    
    /* loaded from: classes4.dex */
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ParallelEarth.values().length];
            try {
                iArr[ParallelEarth.EARTH_PRIME.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[ParallelEarth.NOVA_TELLUS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[ParallelEarth.VOID_CORE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameViewModel(Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this.db = GameDatabase.Companion.getDatabase(application);
        this.repository = new GameRepository(this.db.gameProgressDao());
        this.gameStateFlow = FlowKt.stateIn(this.repository.getGameStateFlow(), ViewModelKt.getViewModelScope(this), SharingStarted.Companion.WhileSubscribed(5000L, 0L), new GameState());
        this._battleState = StateFlowKt.MutableStateFlow(new BattleState(false, 0, 0, 0, 0, null, null, 0, false, 0, 0, false, false, 8191, null));
        this.battleState = FlowKt.asStateFlow(this._battleState);
        this._thinkingText = StateFlowKt.MutableStateFlow("");
        this.thinkingText = FlowKt.asStateFlow(this._thinkingText);
        this._isThinking = StateFlowKt.MutableStateFlow(false);
        this.isThinking = FlowKt.asStateFlow(this._isThinking);
        this._searchText = StateFlowKt.MutableStateFlow("");
        this.searchText = FlowKt.asStateFlow(this._searchText);
        this._isSearching = StateFlowKt.MutableStateFlow(false);
        this.isSearching = FlowKt.asStateFlow(this._isSearching);
        this._searchSources = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
        this.searchSources = FlowKt.asStateFlow(this._searchSources);
        this._activeCompanionName = StateFlowKt.MutableStateFlow(null);
        this.activeCompanionName = FlowKt.asStateFlow(this._activeCompanionName);
        this._radioTransmissionLog = StateFlowKt.MutableStateFlow(CollectionsKt.emptyList());
        this.radioTransmissionLog = FlowKt.asStateFlow(this._radioTransmissionLog);
        this._isTransmitting = StateFlowKt.MutableStateFlow(false);
        this.isTransmitting = FlowKt.asStateFlow(this._isTransmitting);
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3, null);
        this._currentParallelEarth = StateFlowKt.MutableStateFlow(ParallelEarth.EARTH_PRIME);
        this.currentParallelEarth = FlowKt.asStateFlow(this._currentParallelEarth);
        this._unlockedGates = StateFlowKt.MutableStateFlow(new java.util.HashSet<>(java.util.Arrays.asList("gate_prime", "gate_nova")));
        this.unlockedGates = FlowKt.asStateFlow(this._unlockedGates);
        this._puzzlesState = StateFlowKt.MutableStateFlow(new EnvironmentalPuzzlesState(false, false, false, false, false, false, false, 0.0f, 0.0f, 0.0f, 1023, null));
        this.puzzlesState = FlowKt.asStateFlow(this._puzzlesState);
        this._isWarping = StateFlowKt.MutableStateFlow(false);
        this.isWarping = FlowKt.asStateFlow(this._isWarping);
        this._warpProgress = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
        this.warpProgress = FlowKt.asStateFlow(this._warpProgress);
        this._warpMessage = StateFlowKt.MutableStateFlow("");
        this.warpMessage = FlowKt.asStateFlow(this._warpMessage);
        this._starshipState = StateFlowKt.MutableStateFlow(new StarshipState(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, null, java.util.Arrays.asList(new ShipUpgrade[]{new ShipUpgrade("up_shield_boost", "Hyper-Flux Shield Cell", "Boosts maximum shield output by +35%.", ShipSystem.SHIELDS, 400, 100, 1.35f, false, 128, null), new ShipUpgrade("up_railgun", "Hyper-Velocity Railgun", "Increases cannon firepower by +40%.", ShipSystem.WEAPONS, 600, 150, 1.4f, false, 128, null), new ShipUpgrade("up_chronos", "Chronos Warp Stabilizer", "Reduces warp travel fuel cost by 25%.", ShipSystem.NAVIGATION, 800, ComposerKt.invocationKey, 0.75f, false, 128, null), new ShipUpgrade("up_thrusters", "Tachyon Drive Boosters", "Increases engine sub-light velocity by +25%.", ShipSystem.ENGINES, 500, 120, 1.25f, false, 128, null), new ShipUpgrade("up_biosphere", "Automated Bio-Purifiers", "Increases life support efficiency, restoring health after travel.", ShipSystem.LIFE_SUPPORT, AnimationConstants.DefaultDurationMillis, 80, 1.2f, false, 128, null)}), java.util.Arrays.asList(new CrewMember[]{new CrewMember("crew_jaxx", "Commander Jaxx", "Pilot", "Tactical Evacuation", 4, "A former Aegis special ops pilot who turned rogue to find the missing Quantum Babies.", false, 400, "👨\u200d🚀"), new CrewMember("crew_sera", "Sera Moss", "Engineer", "Overclocked Shields", 5, "Biomechanical engineer who designed the Nova Tellus canopy. Can hotwire Singularium reactors.", false, 600, "👩\u200d🔧"), new CrewMember("crew_draks", "Draks", "Gunner", "Aerosol Flak Burst", 3, "A scrap-merchant mercenary with an extreme obsession with heavy munitions.", false, AnimationConstants.DefaultDurationMillis, "👽"), new CrewMember("crew_alistar", "Dr. Alistair", "Scientist", "Quantum Decryption", 4, "Obsessive researcher of the 'Original Sins'. Knows the key frequencies of parallel universes.", false, 500, "👨\u200d🔬")}), java.util.Arrays.asList(new StarSystem[]{new StarSystem("sys_sol_prime", "Solis System (Earth Prime Core)", 0.0f, 0.0f, "Aurelian Corp", "The baseline center of Neo Solis. Densely populated and suffering under cybernetic decay.", ParallelEarth.EARTH_PRIME, 1.0f, "Aurelian Hyper-Reactor", true), new StarSystem("sys_tellus_alpha", "Tellus Alpha Sector", -40.0f, 70.0f, "Emberpact Cultivators", "An overgrown sector in the Nova Tellus cluster teeming with bioluminescent space flora and feral biomechanical creatures.", ParallelEarth.NOVA_TELLUS, 1.5f, "Ancient Arbor Anchor", true), new StarSystem("sys_void_omega", "Void Omega Cluster", 80.0f, -50.0f, "Void Seekers", "A fractured ocean of spatial rifts where forgotten outposts float inside cosmic gravity wells.", ParallelEarth.VOID_CORE, 2.0f, "Reality Anchor Core", true), new StarSystem("sys_singularity", "The Core Singularium", 120.0f, 100.0f, "Quantum Guardians", "The cradle of Project Genesis. A highly unstable spacetime nexus where the original Quantum Babies remain suspended.", ParallelEarth.VOID_CORE, 3.0f, "Nursery Nexus Obelisk", false)}), null, 0, 0, null, 61951, null));
        this.starshipState = FlowKt.asStateFlow(this._starshipState);
        this._spaceCombat = StateFlowKt.MutableStateFlow(new SpaceCombatState(false, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, null, 0, 0, false, false, 8191, null));
        this.spaceCombat = FlowKt.asStateFlow(this._spaceCombat);
    }

    public final StateFlow<GameState> getGameStateFlow() {
        return this.gameStateFlow;
    }

    public final StateFlow<BattleState> getBattleState() {
        return this.battleState;
    }

    public final StateFlow<String> getThinkingText() {
        return this.thinkingText;
    }

    public final StateFlow<Boolean> isThinking() {
        return this.isThinking;
    }

    public final StateFlow<String> getSearchText() {
        return this.searchText;
    }

    public final StateFlow<Boolean> isSearching() {
        return this.isSearching;
    }

    public final StateFlow<List<WebSource>> getSearchSources() {
        return this.searchSources;
    }

    public final StateFlow<String> getActiveCompanionName() {
        return this.activeCompanionName;
    }

    public final StateFlow<List<RadioMessage>> getRadioTransmissionLog() {
        return this.radioTransmissionLog;
    }

    public final StateFlow<Boolean> isTransmitting() {
        return this.isTransmitting;
    }

    /* compiled from: GameViewModel.kt */
    
    @DebugMetadata(c = "com.example.game.viewmodel.GameViewModel$1", f = "GameViewModel.kt", i = {1, 1, 1, 1, 2, 2}, l = {75, 156, 158}, m = "invokeSuspend", n = {"current", "premiumCompanions", "updatedCompanions", "hasPremium", "current", "hasPremium"}, s = {"L$0", "L$1", "L$2", "I$0", "L$0", "I$0"})
    /* renamed from: com.example.game.viewmodel.GameViewModel$1, reason: invalid class name */
    /* loaded from: classes4.dex */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0009. Please report as an issue. */
        /* JADX WARN: Removed duplicated region for block: B:19:0x008c  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x01f6  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0071  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x0087 A[SYNTHETIC] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r29) {
            /*
                Method dump skipped, instructions count: 546
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.example.game.viewmodel.GameViewModel.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void deployCompanionOnField(String companionName) {
        this._activeCompanionName.setValue(companionName);
    }

    public final void recruitCompanion(String companionName, int creditsCost, int nanitesCost) {
        Intrinsics.checkNotNullParameter(companionName, "companionName");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$recruitCompanion$1(this, creditsCost, nanitesCost, companionName, null), 3, null);
    }

    public final void levelUpCompanion(String companionName, int creditsCost, int nanitesCost) {
        Intrinsics.checkNotNullParameter(companionName, "companionName");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$levelUpCompanion$1(this, creditsCost, nanitesCost, companionName, null), 3, null);
    }

    public final void equipCompanionGear(String companionName, String slot, String gearName) {
        Intrinsics.checkNotNullParameter(companionName, "companionName");
        Intrinsics.checkNotNullParameter(slot, "slot");
        Intrinsics.checkNotNullParameter(gearName, "gearName");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$equipCompanionGear$1(this, companionName, slot, gearName, null), 3, null);
    }

    public final void equipWeapon(Weapon weapon) {
        Intrinsics.checkNotNullParameter(weapon, "weapon");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$equipWeapon$1(this, weapon, null), 3, null);
    }

    public final void equipOutfit(Outfit outfit) {
        Intrinsics.checkNotNullParameter(outfit, "outfit");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$equipOutfit$1(this, outfit, null), 3, null);
    }

    public final void installAugment(AugmentSlot slot, String name) {
        Intrinsics.checkNotNullParameter(slot, "slot");
        Intrinsics.checkNotNullParameter(name, "name");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$installAugment$1(this, slot, name, null), 3, null);
    }

    public final void toggleModChip(ModChip chip) {
        Intrinsics.checkNotNullParameter(chip, "chip");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$toggleModChip$1(this, chip, null), 3, null);
    }

    public final void completePOIMission(String poiName) {
        Intrinsics.checkNotNullParameter(poiName, "poiName");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$completePOIMission$1(this, poiName, null), 3, null);
    }

    public final void triggerCompanionBond(String companionName) {
        Intrinsics.checkNotNullParameter(companionName, "companionName");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$triggerCompanionBond$1(this, companionName, null), 3, null);
    }

    public final void addResources(int credits, int nanites, int xp) {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$addResources$1(this, xp, credits, nanites, null), 3, null);
    }

    public final void addItemToInventory(String name, String category, String symbol, int amount, String description, int hpBonus, int mpBonus, int atkBonus, int defBonus, boolean isConsumable, boolean isEquippable) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(description, "description");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$addItemToInventory$1(this, name, category, symbol, amount, description, hpBonus, mpBonus, atkBonus, defBonus, isConsumable, isEquippable, null), 3, null);
    }

    public final void addItemToInventory(String name, String category, String symbol, int amount, String description) {
        addItemToInventory(name, category, symbol, amount, description, 0, 0, 0, 0, false, false);
    }

    public final void equipInventoryItem(String itemId) {
        Intrinsics.checkNotNullParameter(itemId, "itemId");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$equipInventoryItem$1(this, itemId, null), 3, null);
    }

    public final void consumeInventoryItem(String itemId) {
        Intrinsics.checkNotNullParameter(itemId, "itemId");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$consumeInventoryItem$1(this, itemId, null), 3, null);
    }

    public final void deployArchitecture(Faction faction, int creditsCost, int nanitesCost, int xpGain, int repIncrease, DeployedStructure structure) {
        Intrinsics.checkNotNullParameter(faction, "faction");
        Intrinsics.checkNotNullParameter(structure, "structure");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$deployArchitecture$1(this, creditsCost, nanitesCost, xpGain, faction, repIncrease, structure, null), 3, null);
    }

    public final void healPlayerToFull() {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$healPlayerToFull$1(this, null), 3, null);
    }

    public final void restorePlayerMp(int amount) {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$restorePlayerMp$1(this, amount, null), 3, null);
    }

    public final void updatePlayerHealth(int hp) {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$updatePlayerHealth$1(this, hp, null), 3, null);
    }

    public final void consumePlayerMp(int amount) {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$consumePlayerMp$1(this, amount, null), 3, null);
    }

    public final void upgradeDeployedStructure(long structureId, int creditsCost, int nanitesCost) {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$upgradeDeployedStructure$1(this, creditsCost, nanitesCost, structureId, null), 3, null);
    }

    public final void adoptPet(String name, String emoji, int creditsCost, int nanitesCost) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(emoji, "emoji");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$adoptPet$1(this, creditsCost, nanitesCost, name, emoji, null), 3, null);
    }

    public final void trainPet(long petId, int creditsCost, int nanitesCost) {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$trainPet$1(this, creditsCost, nanitesCost, petId, null), 3, null);
    }

    public final void setActivePet(long petId) {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$setActivePet$1(this, petId, null), 3, null);
    }

    public final void craftItem(String recipeName, int creditsCost, int nanitesCost, List<Pair<String, Integer>> materialsNeeded, String resultItemName, String resultCategory, String resultSymbol, String resultDesc, int hpBonus, int mpBonus, int atkBonus, int defBonus, boolean isConsumable, boolean isEquippable) {
        Intrinsics.checkNotNullParameter(recipeName, "recipeName");
        Intrinsics.checkNotNullParameter(materialsNeeded, "materialsNeeded");
        Intrinsics.checkNotNullParameter(resultItemName, "resultItemName");
        Intrinsics.checkNotNullParameter(resultCategory, "resultCategory");
        Intrinsics.checkNotNullParameter(resultSymbol, "resultSymbol");
        Intrinsics.checkNotNullParameter(resultDesc, "resultDesc");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$craftItem$1(this, creditsCost, nanitesCost, materialsNeeded, resultItemName, resultCategory, resultSymbol, resultDesc, hpBonus, mpBonus, atkBonus, defBonus, isConsumable, isEquippable, null), 3, null);
    }

    public final void advanceTimeAndProduce(Function2<? super Integer, ? super Integer, Unit> onYieldCalculated) {
        Intrinsics.checkNotNullParameter(onYieldCalculated, "onYieldCalculated");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$advanceTimeAndProduce$1(this, onYieldCalculated, null), 3, null);
    }

    public final void startCombatWithEnemy(String enemyName, String dangerLevel) {
        Intrinsics.checkNotNullParameter(enemyName, "enemyName");
        Intrinsics.checkNotNullParameter(dangerLevel, "dangerLevel");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$startCombatWithEnemy$1(this, dangerLevel, enemyName, null), 3, null);
    }

    public final void executePlayerAttack() {
        BattleState state = this._battleState.getValue();
        Enemy enemy = state.getActiveEnemy();
        if (enemy != null && state.isPlayerTurn() && !state.getVictory() && !state.getDefeat()) {
            BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$executePlayerAttack$1(this, enemy, state, null), 3, null);
        }
    }

    public final void executePlayerSkill(ActiveSkill skill) {
        Intrinsics.checkNotNullParameter(skill, "skill");
        BattleState state = this._battleState.getValue();
        Enemy enemy = state.getActiveEnemy();
        if (enemy == null) {
            return;
        }
        if (!state.isPlayerTurn() || state.getVictory()) {
            return;
        }
        if (state.getDefeat()) {
            return;
        }
        if (state.getPlayerMp() >= skill.getMpCost()) {
            BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$executePlayerSkill$1(this, state, skill, enemy, null), 3, null);
            return;
        }
        List newLogs = CollectionsKt.toMutableList((Collection) state.getLogs());
        newLogs.add(new BattleLog("ERROR: Insufficient Quantum MP to cycle " + skill.getDisplayName() + "!", true, ColorKt.getQuantumNeonRed(), null));
        this._battleState.setValue(BattleState.copy$default(state, false, 0, 0, 0, 0, null, newLogs, 0, false, 0, 0, false, false, 8127, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void triggerEnemyResponseTurn() {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$triggerEnemyResponseTurn$1(this, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to find 'out' block for switch in B:7:0x0027. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x018c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object handleBattleVictory(com.example.game.models.BattleState r33, com.example.game.models.Enemy r34, java.util.List<com.example.game.models.BattleLog> r35, kotlin.coroutines.Continuation<? super kotlin.Unit> r36) {
        /*
            Method dump skipped, instructions count: 458
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.viewmodel.GameViewModel.handleBattleVictory(com.example.game.models.BattleState, com.example.game.models.Enemy, java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void endBattle() {
        this._battleState.setValue(new BattleState(false, 0, 0, 0, 0, null, null, 0, false, 0, 0, false, false, 8190, null));
    }

    /* renamed from: injectCombatLog-mxwnekA$default, reason: not valid java name */
    public static /* synthetic */ void m7113injectCombatLogmxwnekA$default(GameViewModel gameViewModel, String str, boolean z, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            j = Color.INSTANCE.m4196getWhite0d7_KjU();
        }
        gameViewModel.m7114injectCombatLogmxwnekA(str, z, j);
    }

    /* renamed from: injectCombatLog-mxwnekA, reason: not valid java name */
    public final void m7114injectCombatLogmxwnekA(String text, boolean isPlayerAction, long color) {
        Intrinsics.checkNotNullParameter(text, "text");
        BattleState state = this._battleState.getValue();
        this._battleState.setValue(BattleState.copy$default(state, false, 0, 0, 0, 0, null, CollectionsKt.plus((Collection<? extends BattleLog>) state.getLogs(), new BattleLog(text, isPlayerAction, color, null)), 0, false, 0, 0, false, false, 8127, null));
    }

    public final void injectCombatLog(String text, boolean isPlayerAction, long color) {
        m7114injectCombatLogmxwnekA(text, isPlayerAction, color);
    }

    public final void analyzeQuantumSynergy(String prompt) {
        Intrinsics.checkNotNullParameter(prompt, "prompt");
        if (StringsKt.isBlank(prompt)) {
            return;
        }
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$analyzeQuantumSynergy$1(this, prompt, null), 3, null);
    }

    public final void searchSolisInfoband(String query) {
        Intrinsics.checkNotNullParameter(query, "query");
        if (StringsKt.isBlank(query)) {
            return;
        }
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$searchSolisInfoband$1(this, query, null), 3, null);
    }

    public final void transmitRadioMessage(float frequency, String userMessage) {
        Intrinsics.checkNotNullParameter(userMessage, "userMessage");
        if (StringsKt.isBlank(userMessage)) {
            return;
        }
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$transmitRadioMessage$1(userMessage, frequency, this, null), 3, null);
    }

    public final void clearRadioLogs() {
        this._radioTransmissionLog.setValue(CollectionsKt.emptyList());
    }

    public final void acceptQuest(String questId) {
        Intrinsics.checkNotNullParameter(questId, "questId");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$acceptQuest$1(this, questId, null), 3, null);
    }

    public final void makeQuestChoice(String questId, int choiceIndex) {
        Intrinsics.checkNotNullParameter(questId, "questId");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$makeQuestChoice$1(this, questId, choiceIndex, null), 3, null);
    }

    public final void purchaseFactionItem(String factionName, String itemName, int price, int requiredRep) {
        Intrinsics.checkNotNullParameter(factionName, "factionName");
        Intrinsics.checkNotNullParameter(itemName, "itemName");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$purchaseFactionItem$1(this, factionName, price, requiredRep, itemName, null), 3, null);
    }

    public final void modifyFactionReputations(Map<Faction, Integer> repChanges, String dialogueLogText) {
        Intrinsics.checkNotNullParameter(repChanges, "repChanges");
        Intrinsics.checkNotNullParameter(dialogueLogText, "dialogueLogText");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$modifyFactionReputations$1(this, repChanges, dialogueLogText, null), 3, null);
    }

    public final void spendCreditsDirectly(int amount) {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$spendCreditsDirectly$1(this, amount, null), 3, null);
    }

    public final void awardCreditsDirectly(int amount) {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$awardCreditsDirectly$1(this, amount, null), 3, null);
    }

    public final StateFlow<ParallelEarth> getCurrentParallelEarth() {
        return this.currentParallelEarth;
    }

    public final StateFlow<Set<String>> getUnlockedGates() {
        return this.unlockedGates;
    }

    public final StateFlow<EnvironmentalPuzzlesState> getPuzzlesState() {
        return this.puzzlesState;
    }

    public final StateFlow<Boolean> isWarping() {
        return this.isWarping;
    }

    public final StateFlow<Float> getWarpProgress() {
        return this.warpProgress;
    }

    public final StateFlow<String> getWarpMessage() {
        return this.warpMessage;
    }

    public final void discoverGate(String gateId) {
        Intrinsics.checkNotNullParameter(gateId, "gateId");
        this._unlockedGates.setValue(SetsKt.plus(this._unlockedGates.getValue(), gateId));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void startWarpShift$default(GameViewModel gameViewModel, ParallelEarth parallelEarth, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            function0 = new Function0() { // from class: com.example.game.viewmodel.GameViewModel$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit unit;
                    unit = Unit.INSTANCE;
                    return unit;
                }
            };
        }
        gameViewModel.startWarpShift(parallelEarth, function0);
    }

    public final void startWarpShift(ParallelEarth targetEarth, Function0<Unit> onComplete) {
        Intrinsics.checkNotNullParameter(targetEarth, "targetEarth");
        Intrinsics.checkNotNullParameter(onComplete, "onComplete");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$startWarpShift$2(this, targetEarth, onComplete, null), 3, null);
    }

    public final void toggleBridgeNovaRootNodes() {
        EnvironmentalPuzzlesState current = this._puzzlesState.getValue();
        boolean nextVal = !current.getBridgeNovaRootNodesActive();
        boolean nextSolved = nextVal && current.getBridgeVoidTemporalRiftActive();
        this._puzzlesState.setValue(EnvironmentalPuzzlesState.copy$default(current, nextSolved, false, false, nextVal, false, false, false, 0.0f, 0.0f, 0.0f, PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW, null));
    }

    public final void toggleBridgeVoidTemporalRift() {
        EnvironmentalPuzzlesState current = this._puzzlesState.getValue();
        boolean nextVal = !current.getBridgeVoidTemporalRiftActive();
        boolean nextSolved = current.getBridgeNovaRootNodesActive() && nextVal;
        this._puzzlesState.setValue(EnvironmentalPuzzlesState.copy$default(current, nextSolved, false, false, false, nextVal, false, false, 0.0f, 0.0f, 0.0f, PointerIconCompat.TYPE_CELL, null));
    }

    public final void toggleCryoShieldEarthGenerators() {
        EnvironmentalPuzzlesState current = this._puzzlesState.getValue();
        boolean nextVal = !current.getCryoShieldEarthGeneratorsDisabled();
        boolean nextSolved = nextVal && current.getCryoShieldNovaVinesHarvested();
        this._puzzlesState.setValue(EnvironmentalPuzzlesState.copy$default(current, false, nextSolved, false, false, false, nextVal, false, 0.0f, 0.0f, 0.0f, 989, null));
    }

    public final void toggleCryoShieldNovaVines() {
        EnvironmentalPuzzlesState current = this._puzzlesState.getValue();
        boolean nextVal = !current.getCryoShieldNovaVinesHarvested();
        boolean nextSolved = current.getCryoShieldEarthGeneratorsDisabled() && nextVal;
        this._puzzlesState.setValue(EnvironmentalPuzzlesState.copy$default(current, false, nextSolved, false, false, false, false, nextVal, 0.0f, 0.0f, 0.0f, 957, null));
    }

    public final void tuneDrillFrequency(ParallelEarth earth, float freq) {
        EnvironmentalPuzzlesState copy$default;
        Intrinsics.checkNotNullParameter(earth, "earth");
        EnvironmentalPuzzlesState current = this._puzzlesState.getValue();
        switch (WhenMappings.$EnumSwitchMapping$0[earth.ordinal()]) {
            case 1:
                copy$default = EnvironmentalPuzzlesState.copy$default(current, false, false, false, false, false, false, false, freq, 0.0f, 0.0f, 895, null);
                break;
            case 2:
                copy$default = EnvironmentalPuzzlesState.copy$default(current, false, false, false, false, false, false, false, 0.0f, freq, 0.0f, 767, null);
                break;
            case 3:
                copy$default = EnvironmentalPuzzlesState.copy$default(current, false, false, false, false, false, false, false, 0.0f, 0.0f, freq, FrameMetricsAggregator.EVERY_DURATION, null);
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        EnvironmentalPuzzlesState updated = copy$default;
        boolean isSolved = false;
        if (updated.getDrillPrimeFrequency() == 133.7f) {
            if (updated.getDrillNovaFrequency() == 133.7f) {
                if (updated.getDrillVoidFrequency() == 133.7f) {
                    isSolved = true;
                }
            }
        }
        this._puzzlesState.setValue(EnvironmentalPuzzlesState.copy$default(updated, false, false, isSolved, false, false, false, false, 0.0f, 0.0f, 0.0f, PointerIconCompat.TYPE_ZOOM_OUT, null));
    }

    public final StateFlow<StarshipState> getStarshipState() {
        return this.starshipState;
    }

    public final StateFlow<SpaceCombatState> getSpaceCombat() {
        return this.spaceCombat;
    }

    public final void allocateSystemPower(ShipSystem system, int power) {
        Intrinsics.checkNotNullParameter(system, "system");
        StarshipState current = this._starshipState.getValue();
        Integer num = current.getSystemPowerAllocation().get(system);
        int currentAlloc = num != null ? num.intValue() : 0;
        if (currentAlloc == power) {
            return;
        }
        Map systemPowerAllocation = current.getSystemPowerAllocation();
        Map linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : systemPowerAllocation.entrySet()) {
            if (entry.getKey() != system) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        int otherPower = CollectionsKt.sumOfInt(linkedHashMap.values());
        if (otherPower + power > 12) {
            return;
        }
        Map newAlloc = MapsKt.toMutableMap(current.getSystemPowerAllocation());
        newAlloc.put(system, Integer.valueOf(power));
        this._starshipState.setValue(StarshipState.copy$default(current, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, newAlloc, null, null, null, null, null, 0, 0, null, 65407, null));
    }

    public final void buyShipUpgrade(String upgradeId) {
        Intrinsics.checkNotNullParameter(upgradeId, "upgradeId");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$buyShipUpgrade$1(this, upgradeId, null), 3, null);
    }

    public final void recruitCrewMember(String crewId) {
        Intrinsics.checkNotNullParameter(crewId, "crewId");
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$recruitCrewMember$1(this, crewId, null), 3, null);
    }

    public final void refuelShip() {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$refuelShip$1(this, null), 3, null);
    }

    public final void repairShip() {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$repairShip$1(this, null), 3, null);
    }

    public static /* synthetic */ void startSpaceCombat$default(GameViewModel gameViewModel, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "Void Marauder Dreadnought";
        }
        gameViewModel.startSpaceCombat(str);
    }

    public final void startSpaceCombat() {
        startSpaceCombat("Void Marauder Dreadnought");
    }

    public final void startSpaceCombat(String enemyName) {
        Intrinsics.checkNotNullParameter(enemyName, "enemyName");
        this._starshipState.getValue();
        this._spaceCombat.setValue(new SpaceCombatState(true, enemyName, 120.0f, 120.0f, 60.0f, 60.0f, 18.0f, true, java.util.Collections.singletonList("⚠️ AMBUSH: A hostile " + enemyName + " has dropped out of hyperspace! System shields raised."), 0, 0, false, false, 7680, null));
    }

    public final void executeSpaceAction(String action) {
        Iterable iterable;
        Iterable iterable2;
        Iterable iterable3;
        Object obj;
        Intrinsics.checkNotNullParameter(action, "action");
        SpaceCombatState combat = this._spaceCombat.getValue();
        if (!combat.isCombatActive() || combat.getBattleOver() || !combat.getPlayerTurn()) {
            return;
        }
        StarshipState currentShip = this._starshipState.getValue();
        Integer num = currentShip.getSystemPowerAllocation().get(ShipSystem.WEAPONS);
        int weaponsAlloc = num != null ? num.intValue() : 1;
        Integer num2 = currentShip.getSystemPowerAllocation().get(ShipSystem.SHIELDS);
        int shieldsAlloc = num2 != null ? num2.intValue() : 1;
        Integer num3 = currentShip.getSystemPowerAllocation().get(ShipSystem.ENGINES);
        int enginesAlloc = num3 != null ? num3.intValue() : 1;
        Iterable crew = currentShip.getCrew();
        if (!(crew instanceof Collection) || !((Collection) crew).isEmpty()) {
            Iterator it = crew.iterator();
            while (true) {
                if (it.hasNext()) {
                    CrewMember crewMember = (CrewMember) it.next();
                    if (((Intrinsics.areEqual(crewMember.getRole(), "Gunner") && crewMember.isRecruited()) ? 1 : null) != null) {
                        iterable = 1;
                        break;
                    }
                } else {
                    iterable = null;
                    break;
                }
            }
        } else {
            iterable = null;
        }
        Iterable iterable4 = iterable;
        Iterable crew2 = currentShip.getCrew();
        if (!(crew2 instanceof Collection) || !((Collection) crew2).isEmpty()) {
            Iterator it2 = crew2.iterator();
            while (true) {
                if (it2.hasNext()) {
                    CrewMember crewMember2 = (CrewMember) it2.next();
                    if (((Intrinsics.areEqual(crewMember2.getRole(), "Engineer") && crewMember2.isRecruited()) ? 1 : null) != null) {
                        iterable2 = 1;
                        break;
                    }
                } else {
                    iterable2 = null;
                    break;
                }
            }
        } else {
            iterable2 = null;
        }
        Iterable iterable5 = iterable2;
        Iterable crew3 = currentShip.getCrew();
        if (!(crew3 instanceof Collection) || !((Collection) crew3).isEmpty()) {
            Iterator it3 = crew3.iterator();
            while (true) {
                if (it3.hasNext()) {
                    CrewMember crewMember3 = (CrewMember) it3.next();
                    if (((Intrinsics.areEqual(crewMember3.getRole(), "Pilot") && crewMember3.isRecruited()) ? 1 : null) != null) {
                        iterable3 = 1;
                        break;
                    }
                } else {
                    iterable3 = null;
                    break;
                }
            }
        } else {
            iterable3 = null;
        }
        Iterable iterable6 = iterable3;
        Iterator<T> it4 = currentShip.getUpgrades().iterator();
        while (true) {
            if (!it4.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it4.next();
                if (Intrinsics.areEqual(((ShipUpgrade) obj).getId(), "up_railgun")) {
                    break;
                }
            }
        }
        ShipUpgrade shipUpgrade = (ShipUpgrade) obj;
        boolean railsUpgrade = shipUpgrade != null && shipUpgrade.isOwned();
        float railsMultiplier = railsUpgrade ? 1.4f : 1.0f;
        List logs = CollectionsKt.toMutableList((Collection) combat.getCombatLogs());
        switch (action.hashCode()) {
            case -23564633:
                int shieldsAlloc2 = shieldsAlloc;
                if (action.equals("RECHARGE")) {
                    float restoreAmount = (shieldsAlloc2 * 6.0f) + 15.0f + (iterable5 == null ? 0.0f : 10.0f);
                    float newShield = Math.min(currentShip.getMaxShield(), currentShip.getShield() + restoreAmount);
                    this._starshipState.setValue(StarshipState.copy$default(currentShip, null, 0.0f, 0.0f, newShield, 0.0f, 0.0f, 0.0f, null, null, null, null, null, null, 0, 0, null, 65527, null));
                    logs.add("🛡️ Shield regenerators overclocked! Restored " + MathKt.roundToInt(restoreAmount) + " shield capacity.");
                    this._spaceCombat.setValue(SpaceCombatState.copy$default(combat, false, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, logs, 0, 0, false, false, 7807, null));
                    triggerEnemyTurn();
                    return;
                }
                return;
            case 2158134:
                int weaponsAlloc2 = weaponsAlloc;
                if (action.equals("FIRE")) {
                    float baseDamage = (weaponsAlloc2 * 5.0f) + 10.0f;
                    int finalDamage = MathKt.roundToInt(baseDamage * railsMultiplier * (iterable4 != null ? 1.25f : 1.0f));
                    float remainingDamage = finalDamage;
                    float enemyShield = combat.getEnemyShield();
                    float enemyHull = combat.getEnemyHull();
                    if (enemyShield > 0.0f) {
                        float absorbed = Math.min(enemyShield, remainingDamage);
                        enemyShield -= absorbed;
                        remainingDamage -= absorbed;
                        logs.add("💥 You fired railguns! Dealt " + absorbed + " to enemy shield.");
                    }
                    if (remainingDamage > 0.0f) {
                        enemyHull = Math.max(0.0f, enemyHull - remainingDamage);
                        logs.add("💥 You fired railguns! Dealt " + remainingDamage + " direct damage to enemy hull.");
                    }
                    if (enemyHull <= 0.0f) {
                        int rewardCr = Random.INSTANCE.nextInt(ComposerKt.invocationKey) + AnimationConstants.DefaultDurationMillis;
                        int rewardNa = Random.INSTANCE.nextInt(50) + 50;
                        this._spaceCombat.setValue(SpaceCombatState.copy$default(combat, false, null, enemyHull, 0.0f, enemyShield, 0.0f, 0.0f, false, CollectionsKt.plus((Collection<? extends String>) logs, "🏆 VICTORY: Enemy vessel has been vaporized! Acquired salvage: " + rewardCr + " Credits, " + rewardNa + " Nanites."), rewardCr, rewardNa, true, true, 107, null));
                        return;
                    } else {
                        this._spaceCombat.setValue(SpaceCombatState.copy$default(combat, false, null, enemyHull, 0.0f, enemyShield, 0.0f, 0.0f, false, logs, 0, 0, false, false, 7787, null));
                        triggerEnemyTurn();
                        return;
                    }
                }
                return;
            case 2160614:
                if (action.equals("FLEE")) {
                    int fleeChance = (enginesAlloc * 15) + 30 + (iterable6 != null ? 20 : 0);
                    if (Random.INSTANCE.nextInt(100) < fleeChance) {
                        logs.add("🚀 ENGINE OVERDRIVE: Successfully executed a short-range warp! Hostile lost.");
                        this._spaceCombat.setValue(SpaceCombatState.copy$default(combat, false, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, logs, 0, 0, true, true, 1791, null));
                        return;
                    } else {
                        logs.add("❌ FLEE FAILED: Engines stalled under gravitational dampening!");
                        this._spaceCombat.setValue(SpaceCombatState.copy$default(combat, false, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, false, logs, 0, 0, false, false, 7807, null));
                        triggerEnemyTurn();
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }

    private final void triggerEnemyTurn() {
        BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$triggerEnemyTurn$1(this, null), 3, null);
    }

    public final void collectCombatRewards() {
        SpaceCombatState combat = this._spaceCombat.getValue();
        if (combat.getBattleOver()) {
            BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$collectCombatRewards$1(combat, this, null), 3, null);
        }
    }

    public final void jumpToSystem(String systemId) {
        Object obj;
        Object obj2;
        Object obj3;
        Intrinsics.checkNotNullParameter(systemId, "systemId");
        StarshipState currentShip = this._starshipState.getValue();
        Iterator<T> it = currentShip.getStarSystems().iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                obj2 = null;
                break;
            } else {
                obj2 = it.next();
                if (Intrinsics.areEqual(((StarSystem) obj2).getId(), currentShip.getCurrentSystemId())) {
                    break;
                }
            }
        }
        StarSystem originSystem = (StarSystem) obj2;
        if (originSystem == null) {
            return;
        }
        Iterator<T> it2 = currentShip.getStarSystems().iterator();
        while (true) {
            if (!it2.hasNext()) {
                obj3 = null;
                break;
            } else {
                obj3 = it2.next();
                if (Intrinsics.areEqual(((StarSystem) obj3).getId(), systemId)) {
                    break;
                }
            }
        }
        StarSystem targetSystem = (StarSystem) obj3;
        if (targetSystem == null || Intrinsics.areEqual(originSystem.getId(), targetSystem.getId())) {
            return;
        }
        float dx = targetSystem.getSectorX() - originSystem.getSectorX();
        float dy = targetSystem.getSectorY() - originSystem.getSectorY();
        float dist = (float) Math.sqrt((dx * dx) + (dy * dy));
        Iterator<T> it3 = currentShip.getUpgrades().iterator();
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            Object next = it3.next();
            if (Intrinsics.areEqual(((ShipUpgrade) next).getId(), "up_chronos")) {
                obj = next;
                break;
            }
        }
        ShipUpgrade shipUpgrade = (ShipUpgrade) obj;
        boolean z = false;
        if (shipUpgrade != null && shipUpgrade.isOwned()) {
            z = true;
        }
        boolean chronosUpgrade = z;
        float fuelMultiplier = chronosUpgrade ? 0.75f : 1.0f;
        int fuelNeeded = MathKt.roundToInt(0.4f * dist * fuelMultiplier);
        if (currentShip.getFuel() >= fuelNeeded) {
            BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new GameViewModel$jumpToSystem$1(this, currentShip, fuelNeeded, systemId, targetSystem, null), 3, null);
        }
    }

    public final void adjustUniverseAlignment(int delta) {
        StarshipState current = this._starshipState.getValue();
        this._starshipState.setValue(StarshipState.copy$default(current, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, null, null, null, null, null, RangesKt.coerceIn(current.getUniverseAlignment() + delta, 0, 100), 0, null, 57343, null));
    }

    public final void discoverQuantumBaby() {
        StarshipState current = this._starshipState.getValue();
        int nextVal = Math.min(7, current.getQuantumBabiesDiscovered() + 1);
        this._starshipState.setValue(StarshipState.copy$default(current, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, null, null, null, null, null, 0, nextVal, null, 49151, null));
        if (nextVal >= 3) {
            Iterable<StarSystem> starSystems = current.getStarSystems();
            Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(starSystems, 10));
            for (StarSystem starSystem : starSystems) {
                if (Intrinsics.areEqual(starSystem.getId(), "sys_singularity")) {
                    starSystem = StarSystem.copy$default(starSystem, null, null, 0.0f, 0.0f, null, null, null, 0.0f, null, true, FrameMetricsAggregator.EVERY_DURATION, null);
                }
                arrayList.add(starSystem);
            }
            List updatedSystems = (List) arrayList;
            this._starshipState.setValue(StarshipState.copy$default(this._starshipState.getValue(), null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, null, null, null, updatedSystems, null, 0, nextVal, null, 47103, null));
        }
    }

    public final void resolveGameEnding(String endingId) {
        this._starshipState.setValue(StarshipState.copy$default(this._starshipState.getValue(), null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, null, null, null, null, null, 0, 0, endingId, LayoutKt.LargeDimension, null));
    }
}
