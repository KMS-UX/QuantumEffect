package com.example.game.models;




import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: GameModels.kt */

/* loaded from: classes4.dex */
public enum Outfit {
    DEFAULT("Default Uniform", "Standard survivalist suit from the Unknown Earth experimental labs.", "+0% base stats", 1.0f, 1.0f, 1.0f),
    EXPLORER("Scout Exosuit", "Rigged with thrusters and thermal plating for desolate frontiers.", "+15% Mobility & Speed", 1.0f, 0.9f, 1.15f),
    STEALTH("Phantom Shroud", "Dampens electromagnetic signatures and sound. Preferred by the Eclipse Syndicate.", "+20% Dodge Rate & +10% Crit", 0.9f, 0.9f, 1.1f),
    ACADEMIC("Archivist Robe", "Reinforced with nanite fabrics and mental dampeners. Sourced from the Aurelian Order.", "+25% Magic & MP capacity", 0.9f, 1.1f, 0.9f),
    VOID_TOUCH("Void Singularity Mesh", "Infused with raw condensed Voidium crystals. Absorbs ambient radiation.", "+20% Max Health & Lifesteal", 1.2f, 1.1f, 0.9f);

    private final String bonusDesc;
    private final float defMult;
    private final String description;
    private final String displayName;
    private final float hpMult;
    private final float spdMult;

    public static java.util.List<Outfit> getEntries() {
        return java.util.Arrays.asList(values());
    }

    Outfit(String displayName, String description, String bonusDesc, float hpMult, float defMult, float spdMult) {
        this.displayName = displayName;
        this.description = description;
        this.bonusDesc = bonusDesc;
        this.hpMult = hpMult;
        this.defMult = defMult;
        this.spdMult = spdMult;
    }

    /* synthetic */ Outfit(String str, String str2, String str3, float f, float f2, float f3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, (i & 8) != 0 ? 1.0f : f, (i & 16) != 0 ? 1.0f : f2, (i & 32) != 0 ? 1.0f : f3);
    }

    public final String getBonusDesc() {
        return this.bonusDesc;
    }

    public final float getDefMult() {
        return this.defMult;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final float getHpMult() {
        return this.hpMult;
    }

    public final float getSpdMult() {
        return this.spdMult;
    }
}
