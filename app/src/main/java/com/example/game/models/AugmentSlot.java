package com.example.game.models;





/* compiled from: GameModels.kt */

/* loaded from: classes4.dex */
public enum AugmentSlot {
    CRANIAL("Cranial Core", "Mind & Cognition"),
    TORSO("Torso Chassis", "Core & Vitality"),
    ARMS("Arm Actuators", "Strength & Combat"),
    LEGS("Leg Boosters", "Mobility & Agility"),
    SENSORY("Sensory Array", "Awareness & Perception"),
    DERMAL("Dermal Shell", "Skin & Defense"),
    QUANTUM_CORE("Quantum Reactor", "Power Resonance");

    private final String category;
    private final String displayName;

    public static java.util.List<AugmentSlot> getEntries() {
        return java.util.Arrays.asList(values());
    }

    AugmentSlot(String displayName, String category) {
        this.displayName = displayName;
        this.category = category;
    }

    public final String getCategory() {
        return this.category;
    }

    public final String getDisplayName() {
        return this.displayName;
    }
}
