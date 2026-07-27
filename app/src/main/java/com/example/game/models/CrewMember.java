package com.example.game.models;


import kotlin.jvm.internal.Intrinsics;

/* compiled from: StarshipModels.kt */

/* loaded from: classes4.dex */
public final /* data */ class CrewMember {
    public static final int $stable = 0;
    private final String avatarEmoji;
    private final String description;
    private final String id;
    private final boolean isRecruited;
    private final String name;
    private final int rating;
    private final int recruitmentCost;
    private final String role;
    private final String specialty;

    public static /* synthetic */ CrewMember copy$default(CrewMember crewMember, String str, String str2, String str3, String str4, int i, String str5, boolean z, int i2, String str6, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = crewMember.id;
        }
        if ((i3 & 2) != 0) {
            str2 = crewMember.name;
        }
        if ((i3 & 4) != 0) {
            str3 = crewMember.role;
        }
        if ((i3 & 8) != 0) {
            str4 = crewMember.specialty;
        }
        if ((i3 & 16) != 0) {
            i = crewMember.rating;
        }
        if ((i3 & 32) != 0) {
            str5 = crewMember.description;
        }
        if ((i3 & 64) != 0) {
            z = crewMember.isRecruited;
        }
        if ((i3 & 128) != 0) {
            i2 = crewMember.recruitmentCost;
        }
        if ((i3 & 256) != 0) {
            str6 = crewMember.avatarEmoji;
        }
        int i4 = i2;
        String str7 = str6;
        String str8 = str5;
        boolean z2 = z;
        int i5 = i;
        String str9 = str3;
        return crewMember.copy(str, str2, str9, str4, i5, str8, z2, i4, str7);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.id;
    }

    /* renamed from: component2, reason: from getter */
    public final String component2() {
        return this.name;
    }

    /* renamed from: component3, reason: from getter */
    public final String component3() {
        return this.role;
    }

    /* renamed from: component4, reason: from getter */
    public final String component4() {
        return this.specialty;
    }

    /* renamed from: component5, reason: from getter */
    public final int component5() {
        return this.rating;
    }

    /* renamed from: component6, reason: from getter */
    public final String component6() {
        return this.description;
    }

    /* renamed from: component7, reason: from getter */
    public final boolean component7() {
        return this.isRecruited;
    }

    /* renamed from: component8, reason: from getter */
    public final int component8() {
        return this.recruitmentCost;
    }

    /* renamed from: component9, reason: from getter */
    public final String component9() {
        return this.avatarEmoji;
    }

    public final CrewMember copy(String id, String name, String role, String specialty, int rating, String description, boolean isRecruited, int recruitmentCost, String avatarEmoji) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(role, "role");
        Intrinsics.checkNotNullParameter(specialty, "specialty");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(avatarEmoji, "avatarEmoji");
        return new CrewMember(id, name, role, specialty, rating, description, isRecruited, recruitmentCost, avatarEmoji);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CrewMember)) {
            return false;
        }
        CrewMember crewMember = (CrewMember) other;
        return Intrinsics.areEqual(this.id, crewMember.id) && Intrinsics.areEqual(this.name, crewMember.name) && Intrinsics.areEqual(this.role, crewMember.role) && Intrinsics.areEqual(this.specialty, crewMember.specialty) && this.rating == crewMember.rating && Intrinsics.areEqual(this.description, crewMember.description) && this.isRecruited == crewMember.isRecruited && this.recruitmentCost == crewMember.recruitmentCost && Intrinsics.areEqual(this.avatarEmoji, crewMember.avatarEmoji);
    }

    public int hashCode() {
        return (((((((((((((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.role.hashCode()) * 31) + this.specialty.hashCode()) * 31) + Integer.hashCode(this.rating)) * 31) + this.description.hashCode()) * 31) + Boolean.hashCode(this.isRecruited)) * 31) + Integer.hashCode(this.recruitmentCost)) * 31) + this.avatarEmoji.hashCode();
    }

    public String toString() {
        return "CrewMember(id=" + this.id + ", name=" + this.name + ", role=" + this.role + ", specialty=" + this.specialty + ", rating=" + this.rating + ", description=" + this.description + ", isRecruited=" + this.isRecruited + ", recruitmentCost=" + this.recruitmentCost + ", avatarEmoji=" + this.avatarEmoji + ")";
    }

    public CrewMember(String id, String name, String role, String specialty, int rating, String description, boolean isRecruited, int recruitmentCost, String avatarEmoji) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(role, "role");
        Intrinsics.checkNotNullParameter(specialty, "specialty");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(avatarEmoji, "avatarEmoji");
        this.id = id;
        this.name = name;
        this.role = role;
        this.specialty = specialty;
        this.rating = rating;
        this.description = description;
        this.isRecruited = isRecruited;
        this.recruitmentCost = recruitmentCost;
        this.avatarEmoji = avatarEmoji;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ CrewMember(java.lang.String r13, java.lang.String r14, java.lang.String r15, java.lang.String r16, int r17, java.lang.String r18, boolean r19, int r20, java.lang.String r21, int r22, kotlin.jvm.internal.DefaultConstructorMarker r23) {
        /*
            r12 = this;
            r0 = r22
            r1 = r0 & 64
            if (r1 == 0) goto L9
            r1 = 0
            r9 = r1
            goto Lb
        L9:
            r9 = r19
        Lb:
            r0 = r0 & 128(0x80, float:1.8E-43)
            if (r0 == 0) goto L13
            r0 = 500(0x1f4, float:7.0E-43)
            r10 = r0
            goto L15
        L13:
            r10 = r20
        L15:
            r2 = r12
            r3 = r13
            r4 = r14
            r5 = r15
            r6 = r16
            r7 = r17
            r8 = r18
            r11 = r21
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.game.models.CrewMember.<init>(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, boolean, int, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getRole() {
        return this.role;
    }

    public final String getSpecialty() {
        return this.specialty;
    }

    public final int getRating() {
        return this.rating;
    }

    public final String getDescription() {
        return this.description;
    }

    public final boolean isRecruited() {
        return this.isRecruited;
    }

    public final int getRecruitmentCost() {
        return this.recruitmentCost;
    }

    public final String getAvatarEmoji() {
        return this.avatarEmoji;
    }
}
