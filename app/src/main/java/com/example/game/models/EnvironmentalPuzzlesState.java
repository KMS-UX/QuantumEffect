package com.example.game.models;


import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: ParallelEarthModels.kt */

/* loaded from: classes4.dex */
public final /* data */ class EnvironmentalPuzzlesState {
    public static final int $stable = 0;
    private final boolean bridgeNovaRootNodesActive;
    private final boolean bridgeVoidTemporalRiftActive;
    private final boolean cryoShieldEarthGeneratorsDisabled;
    private final boolean cryoShieldNovaVinesHarvested;
    private final float drillNovaFrequency;
    private final float drillPrimeFrequency;
    private final float drillVoidFrequency;
    private final boolean isAurelianBridgeSolved;
    private final boolean isCryoShieldSolved;
    private final boolean isVoidDrillSolved;

    public EnvironmentalPuzzlesState() {
        this(false, false, false, false, false, false, false, 0.0f, 0.0f, 0.0f, 1023, null);
    }

    public static /* synthetic */ EnvironmentalPuzzlesState copy$default(EnvironmentalPuzzlesState environmentalPuzzlesState, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, float f, float f2, float f3, int i, Object obj) {
        if ((i & 1) != 0) {
            z = environmentalPuzzlesState.isAurelianBridgeSolved;
        }
        if ((i & 2) != 0) {
            z2 = environmentalPuzzlesState.isCryoShieldSolved;
        }
        if ((i & 4) != 0) {
            z3 = environmentalPuzzlesState.isVoidDrillSolved;
        }
        if ((i & 8) != 0) {
            z4 = environmentalPuzzlesState.bridgeNovaRootNodesActive;
        }
        if ((i & 16) != 0) {
            z5 = environmentalPuzzlesState.bridgeVoidTemporalRiftActive;
        }
        if ((i & 32) != 0) {
            z6 = environmentalPuzzlesState.cryoShieldEarthGeneratorsDisabled;
        }
        if ((i & 64) != 0) {
            z7 = environmentalPuzzlesState.cryoShieldNovaVinesHarvested;
        }
        if ((i & 128) != 0) {
            f = environmentalPuzzlesState.drillPrimeFrequency;
        }
        if ((i & 256) != 0) {
            f2 = environmentalPuzzlesState.drillNovaFrequency;
        }
        if ((i & 512) != 0) {
            f3 = environmentalPuzzlesState.drillVoidFrequency;
        }
        float f4 = f2;
        float f5 = f3;
        boolean z8 = z7;
        float f6 = f;
        boolean z9 = z5;
        boolean z10 = z6;
        return environmentalPuzzlesState.copy(z, z2, z3, z4, z9, z10, z8, f6, f4, f5);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean component1() {
        return this.isAurelianBridgeSolved;
    }

    /* renamed from: component10, reason: from getter */
    public final float component10() {
        return this.drillVoidFrequency;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean component2() {
        return this.isCryoShieldSolved;
    }

    /* renamed from: component3, reason: from getter */
    public final boolean component3() {
        return this.isVoidDrillSolved;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean component4() {
        return this.bridgeNovaRootNodesActive;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean component5() {
        return this.bridgeVoidTemporalRiftActive;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean component6() {
        return this.cryoShieldEarthGeneratorsDisabled;
    }

    /* renamed from: component7, reason: from getter */
    public final boolean component7() {
        return this.cryoShieldNovaVinesHarvested;
    }

    /* renamed from: component8, reason: from getter */
    public final float component8() {
        return this.drillPrimeFrequency;
    }

    /* renamed from: component9, reason: from getter */
    public final float component9() {
        return this.drillNovaFrequency;
    }

    public final EnvironmentalPuzzlesState copy(boolean isAurelianBridgeSolved, boolean isCryoShieldSolved, boolean isVoidDrillSolved, boolean bridgeNovaRootNodesActive, boolean bridgeVoidTemporalRiftActive, boolean cryoShieldEarthGeneratorsDisabled, boolean cryoShieldNovaVinesHarvested, float drillPrimeFrequency, float drillNovaFrequency, float drillVoidFrequency) {
        return new EnvironmentalPuzzlesState(isAurelianBridgeSolved, isCryoShieldSolved, isVoidDrillSolved, bridgeNovaRootNodesActive, bridgeVoidTemporalRiftActive, cryoShieldEarthGeneratorsDisabled, cryoShieldNovaVinesHarvested, drillPrimeFrequency, drillNovaFrequency, drillVoidFrequency);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EnvironmentalPuzzlesState)) {
            return false;
        }
        EnvironmentalPuzzlesState environmentalPuzzlesState = (EnvironmentalPuzzlesState) other;
        return this.isAurelianBridgeSolved == environmentalPuzzlesState.isAurelianBridgeSolved && this.isCryoShieldSolved == environmentalPuzzlesState.isCryoShieldSolved && this.isVoidDrillSolved == environmentalPuzzlesState.isVoidDrillSolved && this.bridgeNovaRootNodesActive == environmentalPuzzlesState.bridgeNovaRootNodesActive && this.bridgeVoidTemporalRiftActive == environmentalPuzzlesState.bridgeVoidTemporalRiftActive && this.cryoShieldEarthGeneratorsDisabled == environmentalPuzzlesState.cryoShieldEarthGeneratorsDisabled && this.cryoShieldNovaVinesHarvested == environmentalPuzzlesState.cryoShieldNovaVinesHarvested && Float.compare(this.drillPrimeFrequency, environmentalPuzzlesState.drillPrimeFrequency) == 0 && Float.compare(this.drillNovaFrequency, environmentalPuzzlesState.drillNovaFrequency) == 0 && Float.compare(this.drillVoidFrequency, environmentalPuzzlesState.drillVoidFrequency) == 0;
    }

    public int hashCode() {
        return (((((((((((((((((Boolean.hashCode(this.isAurelianBridgeSolved) * 31) + Boolean.hashCode(this.isCryoShieldSolved)) * 31) + Boolean.hashCode(this.isVoidDrillSolved)) * 31) + Boolean.hashCode(this.bridgeNovaRootNodesActive)) * 31) + Boolean.hashCode(this.bridgeVoidTemporalRiftActive)) * 31) + Boolean.hashCode(this.cryoShieldEarthGeneratorsDisabled)) * 31) + Boolean.hashCode(this.cryoShieldNovaVinesHarvested)) * 31) + Float.hashCode(this.drillPrimeFrequency)) * 31) + Float.hashCode(this.drillNovaFrequency)) * 31) + Float.hashCode(this.drillVoidFrequency);
    }

    public String toString() {
        return "EnvironmentalPuzzlesState(isAurelianBridgeSolved=" + this.isAurelianBridgeSolved + ", isCryoShieldSolved=" + this.isCryoShieldSolved + ", isVoidDrillSolved=" + this.isVoidDrillSolved + ", bridgeNovaRootNodesActive=" + this.bridgeNovaRootNodesActive + ", bridgeVoidTemporalRiftActive=" + this.bridgeVoidTemporalRiftActive + ", cryoShieldEarthGeneratorsDisabled=" + this.cryoShieldEarthGeneratorsDisabled + ", cryoShieldNovaVinesHarvested=" + this.cryoShieldNovaVinesHarvested + ", drillPrimeFrequency=" + this.drillPrimeFrequency + ", drillNovaFrequency=" + this.drillNovaFrequency + ", drillVoidFrequency=" + this.drillVoidFrequency + ")";
    }

    public EnvironmentalPuzzlesState(boolean isAurelianBridgeSolved, boolean isCryoShieldSolved, boolean isVoidDrillSolved, boolean bridgeNovaRootNodesActive, boolean bridgeVoidTemporalRiftActive, boolean cryoShieldEarthGeneratorsDisabled, boolean cryoShieldNovaVinesHarvested, float drillPrimeFrequency, float drillNovaFrequency, float drillVoidFrequency) {
        this.isAurelianBridgeSolved = isAurelianBridgeSolved;
        this.isCryoShieldSolved = isCryoShieldSolved;
        this.isVoidDrillSolved = isVoidDrillSolved;
        this.bridgeNovaRootNodesActive = bridgeNovaRootNodesActive;
        this.bridgeVoidTemporalRiftActive = bridgeVoidTemporalRiftActive;
        this.cryoShieldEarthGeneratorsDisabled = cryoShieldEarthGeneratorsDisabled;
        this.cryoShieldNovaVinesHarvested = cryoShieldNovaVinesHarvested;
        this.drillPrimeFrequency = drillPrimeFrequency;
        this.drillNovaFrequency = drillNovaFrequency;
        this.drillVoidFrequency = drillVoidFrequency;
    }

    public /* synthetic */ EnvironmentalPuzzlesState(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, float f, float f2, float f3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? false : z3, (i & 8) != 0 ? false : z4, (i & 16) != 0 ? false : z5, (i & 32) != 0 ? false : z6, (i & 64) != 0 ? false : z7, (i & 128) != 0 ? 100.0f : f, (i & 256) != 0 ? 100.0f : f2, (i & 512) != 0 ? 100.0f : f3);
    }

    public final boolean isAurelianBridgeSolved() {
        return this.isAurelianBridgeSolved;
    }

    public final boolean isCryoShieldSolved() {
        return this.isCryoShieldSolved;
    }

    public final boolean isVoidDrillSolved() {
        return this.isVoidDrillSolved;
    }

    public final boolean getBridgeNovaRootNodesActive() {
        return this.bridgeNovaRootNodesActive;
    }

    public final boolean getBridgeVoidTemporalRiftActive() {
        return this.bridgeVoidTemporalRiftActive;
    }

    public final boolean getCryoShieldEarthGeneratorsDisabled() {
        return this.cryoShieldEarthGeneratorsDisabled;
    }

    public final boolean getCryoShieldNovaVinesHarvested() {
        return this.cryoShieldNovaVinesHarvested;
    }

    public final float getDrillPrimeFrequency() {
        return this.drillPrimeFrequency;
    }

    public final float getDrillNovaFrequency() {
        return this.drillNovaFrequency;
    }

    public final float getDrillVoidFrequency() {
        return this.drillVoidFrequency;
    }
}
