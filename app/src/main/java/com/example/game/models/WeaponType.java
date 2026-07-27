package com.example.game.models;





/* compiled from: GameModels.kt */

/* loaded from: classes4.dex */
public enum WeaponType {
    MELEE,
    PISTOL,
    RIFLE,
    HEAVY,
    ENERGY,
    QUANTUM;


    public static java.util.List<WeaponType> getEntries() {
        return java.util.Arrays.asList(values());
    }
}
