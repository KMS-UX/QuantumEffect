package com.example.game.models;

import androidx.compose.ui.graphics.Color;
import com.example.ui.theme.ColorKt;





/* compiled from: GameModels.kt */

/* loaded from: classes4.dex */
public enum ReputationTier {
    HATED("Hated", -500, ColorKt.getQuantumNeonRed()),
    HOSTILE("Hostile", -100, ColorKt.getQuantumNeonOrange()),
    NEUTRAL("Neutral", 100, Color.INSTANCE.m4191getLightGray0d7_KjU()),
    FAVORABLE("Favorable", 500, ColorKt.getQuantumNeonBlue()),
    REVERED("Revered", 1000, ColorKt.getQuantumNeonGreen());

    private final long color;
    private final int threshold;
    private final String title;

    public static java.util.List<ReputationTier> getEntries() {
        return java.util.Arrays.asList(values());
    }

    ReputationTier(String title, int threshold, long color) {
        this.title = title;
        this.threshold = threshold;
        this.color = color;
    }

    /* renamed from: getColor-0d7_KjU, reason: not valid java name and from getter */
    public final long getColor() {
        return this.color;
    }

    public final int getThreshold() {
        return this.threshold;
    }

    public final String getTitle() {
        return this.title;
    }
}
