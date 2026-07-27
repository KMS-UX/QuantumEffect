package com.example.game.models;

import com.example.ui.theme.ColorKt;




/* compiled from: GameModels.kt */

/* loaded from: classes4.dex */
public enum Faction {
    AURELIAN_ORDER("Aurelian Order", "A technomystic theocracy dedicated to the preservation of ancient space coordinates.", "Lawful • Structured", "Aurelian Prime", "Advanced Tech Access", "Discount on Tech & Medical Supplies", ColorKt.getQuantumNeonBlue()),
    EMBERPACT("Emberpact", "A fierce coalition of frontier mining clans and survivalist raiders.", "Chaotic • Fierce", "Kharag Wastes", "Heavy Weapon Mastery", "Increased loot from defeated mechanoids", ColorKt.getQuantumNeonOrange()),
    VOID_SEEKERS("Void Seekers", "Mystics and outcasts utilizing dark-matter technology to transcend physical boundaries.", "Mysterious • Insightful", "Nyx Expanse", "Void Magic Mastery", "Increases Void anomaly resistances by 30%", ColorKt.getQuantumNeonPurple()),
    IRONWARD("Ironward", "A highly militarized industrial union focused on defense, security, and robotic engineering.", "Lawful • Practical", "Vestra Industrial", "Armored Vehicle Support", "Ability to deploy battle turrets & drones", ColorKt.getQuantumNeonGreen()),
    SILENT_VEIL("Silent Veil", "A secretive network of shadow spies, data brokers, and cutthroat herbalists.", "Neutral • Stealthy", "Unknown", "Stealth Expertise", "Black market trades & hidden path access", ColorKt.getQuantumNeonRed());

    private final String alignment;
    private final String baseLocation;
    private final long color;
    private final String displayName;
    private final String perkDesc;
    private final String perkName;
    private final String shortDesc;

    public static java.util.List<Faction> getEntries() {
        return java.util.Arrays.asList(values());
    }

    Faction(String displayName, String shortDesc, String alignment, String baseLocation, String perkName, String perkDesc, long color) {
        this.displayName = displayName;
        this.shortDesc = shortDesc;
        this.alignment = alignment;
        this.baseLocation = baseLocation;
        this.perkName = perkName;
        this.perkDesc = perkDesc;
        this.color = color;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final String getShortDesc() {
        return this.shortDesc;
    }

    public final String getAlignment() {
        return this.alignment;
    }

    public final String getBaseLocation() {
        return this.baseLocation;
    }

    public final String getPerkName() {
        return this.perkName;
    }

    public final String getPerkDesc() {
        return this.perkDesc;
    }

    /* renamed from: getColor-0d7_KjU, reason: not valid java name and from getter */
    public final long getColor() {
        return this.color;
    }
}
