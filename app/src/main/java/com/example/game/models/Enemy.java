package com.example.game.models;

import com.squareup.moshi.JsonClass;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameModels.kt */
@JsonClass(generateAdapter = true)
/* loaded from: classes4.dex */
public final /* data */ class Enemy {
    public static final int $stable = 8;
    private final int atk;
    private final int currentHp;
    private final int defense;
    private final String description;
    private final int maxHp;
    private final String modName;
    private final String name;
    private final String resistanceType;
    private final int speed;
    private final List<String> statusEffects;
    private final String vulnerability;

    public static /* synthetic */ Enemy copy$default(Enemy enemy, String str, int i, int i2, int i3, int i4, int i5, String str2, String str3, String str4, String str5, List list, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            str = enemy.name;
        }
        if ((i6 & 2) != 0) {
            i = enemy.maxHp;
        }
        if ((i6 & 4) != 0) {
            i2 = enemy.currentHp;
        }
        if ((i6 & 8) != 0) {
            i3 = enemy.atk;
        }
        if ((i6 & 16) != 0) {
            i4 = enemy.defense;
        }
        if ((i6 & 32) != 0) {
            i5 = enemy.speed;
        }
        if ((i6 & 64) != 0) {
            str2 = enemy.description;
        }
        if ((i6 & 128) != 0) {
            str3 = enemy.resistanceType;
        }
        if ((i6 & 256) != 0) {
            str4 = enemy.vulnerability;
        }
        if ((i6 & 512) != 0) {
            str5 = enemy.modName;
        }
        if ((i6 & 1024) != 0) {
            list = enemy.statusEffects;
        }
        String str6 = str5;
        List list2 = list;
        String str7 = str3;
        String str8 = str4;
        int i7 = i5;
        String str9 = str2;
        int i8 = i4;
        int i9 = i2;
        return enemy.copy(str, i, i9, i3, i8, i7, str9, str7, str8, str6, list2);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.name;
    }

    /* renamed from: component10, reason: from getter */
    public final String component10() {
        return this.modName;
    }

    public final List<String> component11() {
        return this.statusEffects;
    }

    /* renamed from: component2, reason: from getter */
    public final int component2() {
        return this.maxHp;
    }

    /* renamed from: component3, reason: from getter */
    public final int component3() {
        return this.currentHp;
    }

    /* renamed from: component4, reason: from getter */
    public final int component4() {
        return this.atk;
    }

    /* renamed from: component5, reason: from getter */
    public final int component5() {
        return this.defense;
    }

    /* renamed from: component6, reason: from getter */
    public final int component6() {
        return this.speed;
    }

    /* renamed from: component7, reason: from getter */
    public final String component7() {
        return this.description;
    }

    /* renamed from: component8, reason: from getter */
    public final String component8() {
        return this.resistanceType;
    }

    /* renamed from: component9, reason: from getter */
    public final String component9() {
        return this.vulnerability;
    }

    public final Enemy copy(String name, int maxHp, int currentHp, int atk, int defense, int speed, String description, String resistanceType, String vulnerability, String modName, List<String> statusEffects) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(resistanceType, "resistanceType");
        Intrinsics.checkNotNullParameter(vulnerability, "vulnerability");
        Intrinsics.checkNotNullParameter(modName, "modName");
        Intrinsics.checkNotNullParameter(statusEffects, "statusEffects");
        return new Enemy(name, maxHp, currentHp, atk, defense, speed, description, resistanceType, vulnerability, modName, statusEffects);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Enemy)) {
            return false;
        }
        Enemy enemy = (Enemy) other;
        return Intrinsics.areEqual(this.name, enemy.name) && this.maxHp == enemy.maxHp && this.currentHp == enemy.currentHp && this.atk == enemy.atk && this.defense == enemy.defense && this.speed == enemy.speed && Intrinsics.areEqual(this.description, enemy.description) && Intrinsics.areEqual(this.resistanceType, enemy.resistanceType) && Intrinsics.areEqual(this.vulnerability, enemy.vulnerability) && Intrinsics.areEqual(this.modName, enemy.modName) && Intrinsics.areEqual(this.statusEffects, enemy.statusEffects);
    }

    public int hashCode() {
        return (((((((((((((((((((this.name.hashCode() * 31) + Integer.hashCode(this.maxHp)) * 31) + Integer.hashCode(this.currentHp)) * 31) + Integer.hashCode(this.atk)) * 31) + Integer.hashCode(this.defense)) * 31) + Integer.hashCode(this.speed)) * 31) + this.description.hashCode()) * 31) + this.resistanceType.hashCode()) * 31) + this.vulnerability.hashCode()) * 31) + this.modName.hashCode()) * 31) + this.statusEffects.hashCode();
    }

    public String toString() {
        return "Enemy(name=" + this.name + ", maxHp=" + this.maxHp + ", currentHp=" + this.currentHp + ", atk=" + this.atk + ", defense=" + this.defense + ", speed=" + this.speed + ", description=" + this.description + ", resistanceType=" + this.resistanceType + ", vulnerability=" + this.vulnerability + ", modName=" + this.modName + ", statusEffects=" + this.statusEffects + ")";
    }

    public Enemy(String name, int maxHp, int currentHp, int atk, int defense, int speed, String description, String resistanceType, String vulnerability, String modName, List<String> statusEffects) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(resistanceType, "resistanceType");
        Intrinsics.checkNotNullParameter(vulnerability, "vulnerability");
        Intrinsics.checkNotNullParameter(modName, "modName");
        Intrinsics.checkNotNullParameter(statusEffects, "statusEffects");
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = currentHp;
        this.atk = atk;
        this.defense = defense;
        this.speed = speed;
        this.description = description;
        this.resistanceType = resistanceType;
        this.vulnerability = vulnerability;
        this.modName = modName;
        this.statusEffects = statusEffects;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ Enemy(java.lang.String r14, int r15, int r16, int r17, int r18, int r19, java.lang.String r20, java.lang.String r21, java.lang.String r22, java.lang.String r23, java.util.List r24, int r25, kotlin.jvm.internal.DefaultConstructorMarker r26) {
        /*
            r13 = this;
            r0 = r25
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 == 0) goto Lc
            java.util.List r0 = kotlin.collections.CollectionsKt.emptyList()
            r12 = r0
            goto Le
        Lc:
            r12 = r24
        Le:
            r1 = r13
            r2 = r14
            r3 = r15
            r4 = r16
            r5 = r17
            r6 = r18
            r7 = r19
            r8 = r20
            r9 = r21
            r10 = r22
            r11 = r23
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.models.Enemy.<init>(java.lang.String, int, int, int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final String getName() {
        return this.name;
    }

    public final int getMaxHp() {
        return this.maxHp;
    }

    public final int getCurrentHp() {
        return this.currentHp;
    }

    public final int getAtk() {
        return this.atk;
    }

    public final int getDefense() {
        return this.defense;
    }

    public final int getSpeed() {
        return this.speed;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getResistanceType() {
        return this.resistanceType;
    }

    public final String getVulnerability() {
        return this.vulnerability;
    }

    public final String getModName() {
        return this.modName;
    }

    public final List<String> getStatusEffects() {
        return this.statusEffects;
    }
}
