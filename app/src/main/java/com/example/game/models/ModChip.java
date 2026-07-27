package com.example.game.models;





/* compiled from: GameModels.kt */

/* loaded from: classes4.dex */
public enum ModChip {
    CRITICAL_BOOST("Critical Boost", "Increases critical strike rate by 15%.", "Offensive"),
    LIFE_DRAIN("Life Drain", "Restores 10% of dealt damage as HP.", "Defensive"),
    SHIELD_OVERDRIVE("Shield Overdrive", "Increases maximum shield capacity by 20%.", "Defensive"),
    QUANTUM_LEECH("Quantum Leech", "Drains 5 MP from target on attack.", "Offensive"),
    COOLDOWN_REDUCER("Cooldown Reducer", "Reduces all skill cooldowns by 15%.", "Utility"),
    STEALTH_MODULE("Stealth Module", "Reduces enemy detection range on the map by 30%.", "Utility"),
    EXP_BOOSTER("EXP Booster", "Increases experience points gained by 25%.", "Special"),
    DROP_RATE_UP("Drop Rate Up", "Increases material drop rate from bosses by 30%.", "Special");

    private final String bonusDesc;
    private final String category;
    private final String displayName;

    public static java.util.List<ModChip> getEntries() {
        return java.util.Arrays.asList(values());
    }

    ModChip(String displayName, String bonusDesc, String category) {
        this.displayName = displayName;
        this.bonusDesc = bonusDesc;
        this.category = category;
    }

    public final String getBonusDesc() {
        return this.bonusDesc;
    }

    public final String getCategory() {
        return this.category;
    }

    public final String getDisplayName() {
        return this.displayName;
    }
}
