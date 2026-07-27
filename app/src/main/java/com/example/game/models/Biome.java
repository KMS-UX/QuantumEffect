package com.example.game.models;

import com.example.ui.theme.ColorKt;




/* compiled from: GameModels.kt */

/* loaded from: classes4.dex */
public enum Biome {
    GRASSLANDS("Aurelian Heartlands", ColorKt.getQuantumNeonGreen()),
    SNOW("Frostveil Reaches", ColorKt.getQuantumNeonBlue()),
    DESERT("Emberfall Wastes", ColorKt.getQuantumNeonOrange()),
    VOLCANIC("Ironward Highlands", ColorKt.getQuantumNeonRed()),
    RUINS("Sunken Ruins", ColorKt.getQuantumNeonPurple()),
    VOID_SEA("The Void Sea", ColorKt.getQuantumNeonPurple());

    private final long color;
    private final String displayName;

    public static java.util.List<Biome> getEntries() {
        return java.util.Arrays.asList(values());
    }

    Biome(String displayName, long color) {
        this.displayName = displayName;
        this.color = color;
    }

    /* renamed from: getColor-0d7_KjU, reason: not valid java name and from getter */
    public final long getColor() {
        return this.color;
    }

    public final String getDisplayName() {
        return this.displayName;
    }
}
