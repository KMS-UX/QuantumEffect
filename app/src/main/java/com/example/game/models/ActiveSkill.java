package com.example.game.models;

import com.example.ui.theme.ColorKt;




/* compiled from: GameModels.kt */
/* loaded from: classes4.dex */
public enum ActiveSkill {
    QUANTUM_BURST("Quantum Burst", 20, "Deal 120 Magic damage to all active threats.", ColorKt.getQuantumNeonPurple()),
    TIME_STEP("Time Step", 15, "Gain +50% evasion and blink forward, dealing 40 Kinetic damage.", ColorKt.getQuantumNeonBlue()),
    PHASE_SHIELD("Phase Shield", 25, "Create an energy barrier absorbing up to 150 incoming damage.", ColorKt.getQuantumNeonGreen()),
    RESONANCE_WAVE("Resonance Wave", 18, "Emit an anomaly wave reducing enemy armor by 40% for 3 turns.", ColorKt.getQuantumNeonOrange()),
    GRAVITY_WELL("Gravity Well", 30, "Pull enemies together, trapping them and dealing 30 damage per turn.", ColorKt.getQuantumNeonPurple()),
    HEALING_PULSE("Healing Pulse", 22, "Activate dynamic bio-regenerators to restore 150 HP immediately.", ColorKt.getQuantumNeonGreen()),
    VOID_TOUCH("Void Touch", 12, "Empower strikes to convert 30% of physical damage into health.", ColorKt.getQuantumNeonPurple()),
    TEMPORAL_LOOP("Temporal Loop", 35, "Rewind timeline by 3 seconds, removing all debuffs and restoring 80 HP.", ColorKt.getQuantumNeonBlue()),
    ENERGY_OVERLOAD("Energy Overload", 28, "Enter an overcharged state, boosting ATK and MAG by 50% for 2 turns.", ColorKt.getQuantumNeonOrange());

    private final long color;
    private final String description;
    private final String displayName;
    private final int mpCost;

    public static java.util.List<ActiveSkill> getEntries() {
        return java.util.Arrays.asList(values());
    }

    ActiveSkill(String displayName, int mpCost, String description, long color) {
        this.displayName = displayName;
        this.mpCost = mpCost;
        this.description = description;
        this.color = color;
    }

    /* renamed from: getColor-0d7_KjU, reason: not valid java name and from getter */
    public final long getColor() {
        return this.color;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final int getMpCost() {
        return this.mpCost;
    }
}
