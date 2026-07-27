package com.example.game.models;

import androidx.compose.runtime.ComposerKt;





/* compiled from: GameModels.kt */

/* loaded from: classes4.dex */
public enum Weapon {
    QUANTUM_BLADE("Quantum Blade", WeaponType.MELEE, 120, 40, 5, "A katana-style blade that vibrates with localized spatial anomalies.", "Ignores 20% Armor"),
    MONO_KATANA("Monomolecular Katana", WeaponType.MELEE, 100, 10, 0, "A super-sharp carbon-nanotube edge that slices on contact.", "+15% Critical Chance"),
    PULSE_PISTOL("Pulse Pistol", WeaponType.PISTOL, 70, 20, 2, "A compact energy blaster with auto-stabilizing magnetic coils.", "Drains shield on hit"),
    VOID_HANDCANNON("Void Handcannon", WeaponType.PISTOL, 150, 80, 8, "Fires condensed dark matter shells. High recoil, catastrophic force.", "+25% Critical Damage"),
    RAILGUN_AR("Railgun AR", WeaponType.RIFLE, 110, 10, 4, "An electromagnetic assault rifle capable of armor-piercing kinetic rounds.", "Pierce through enemies"),
    PLASMA_CARBINE("Plasma Carbine", WeaponType.RIFLE, 90, 60, 5, "Fires localized plasma charges that splash on impact.", "Applies burn effect over time"),
    GAUSS_CANNON("Gauss Cannon", WeaponType.HEAVY, 180, 20, 10, "A heavy micro-projectile launcher designed to halt tactical mechs.", "Chance to stun targets"),
    SIEGE_RAILGUN("Siege Railgun", WeaponType.HEAVY, 220, 50, 15, "A shoulder-mounted weapon utilizing orbital dreadnought tech.", "High damage but -10% Speed"),
    LASER_LANCE("Laser Lance", WeaponType.ENERGY, 80, 100, 6, "A continuous beam focuser delivering thermal energy.", "Melts defense stacks by 5%"),
    PARTICLE_SCYTHE("Particle Scythe", WeaponType.ENERGY, 110, 130, 8, "Harvests subatomic collisions, sweeping fields with heavy damage.", "+10% HP lifesteal"),
    SINGULARITY_LANCE("Singularity Lance", WeaponType.QUANTUM, 140, 180, 12, "Fires miniature gravitational collapses, dragging threats together.", "Attacks drag in & slow"),
    REALITY_SHREDDER("Reality Shredder", WeaponType.QUANTUM, ComposerKt.invocationKey, 250, 20, "Warp technology that temporarily creates localized rift breaches.", "+30% Void corruption damage");

    private final int baseAtk;
    private final int baseMag;
    private final String description;
    private final String displayName;
    private final int energyCost;
    private final String specialtyDesc;
    private final WeaponType type;

    public static java.util.List<Weapon> getEntries() {
        return java.util.Arrays.asList(values());
    }

    Weapon(String displayName, WeaponType type, int baseAtk, int baseMag, int energyCost, String description, String specialtyDesc) {
        this.displayName = displayName;
        this.type = type;
        this.baseAtk = baseAtk;
        this.baseMag = baseMag;
        this.energyCost = energyCost;
        this.description = description;
        this.specialtyDesc = specialtyDesc;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final WeaponType getType() {
        return this.type;
    }

    public final int getBaseAtk() {
        return this.baseAtk;
    }

    public final int getBaseMag() {
        return this.baseMag;
    }

    public final int getEnergyCost() {
        return this.energyCost;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getSpecialtyDesc() {
        return this.specialtyDesc;
    }
}
