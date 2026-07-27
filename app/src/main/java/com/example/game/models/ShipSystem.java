package com.example.game.models;

import androidx.compose.ui.graphics.ColorKt;




/* compiled from: StarshipModels.kt */

/* loaded from: classes4.dex */
public enum ShipSystem {
    WEAPONS("Hyper-Charged Cannons", "⚔️", ColorKt.Color(4294907716L)),
    SHIELDS("Singularium Deflectors", "🛡️", ColorKt.Color(4280923894L)),
    ENGINES("Quantum Thrust Impellers", "🚀", ColorKt.Color(4294938880L)),
    NAVIGATION("Chronos Core Computer", "🧭", ColorKt.Color(4292149497L)),
    LIFE_SUPPORT("Biomechanical Biosphere", "🌿", ColorKt.Color(4278249078L));

    private final long color;
    private final String displayName;
    private final String icon;

    public static java.util.List<ShipSystem> getEntries() {
        return java.util.Arrays.asList(values());
    }

    ShipSystem(String displayName, String icon, long color) {
        this.displayName = displayName;
        this.icon = icon;
        this.color = color;
    }

    /* renamed from: getColor-0d7_KjU, reason: not valid java name and from getter */
    public final long getColor() {
        return this.color;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final String getIcon() {
        return this.icon;
    }
}
