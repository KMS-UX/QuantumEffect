package com.example.game.models;

import androidx.core.app.NotificationCompat;
import com.squareup.moshi.JsonClass;


import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameModels.kt */
@JsonClass(generateAdapter = true)

/* loaded from: classes4.dex */
public final /* data */ class Quest {
    public static final int $stable = 0;
    private final String description;
    private final String faction;
    private final String giverName;
    private final String id;
    private final String objectiveDesc;
    private final String objectiveType;
    private final int progress;
    private final int rewardCredits;
    private final int rewardNanites;
    private final int rewardReputationPoints;
    private final int rewardXp;
    private final String status;
    private final int targetCount;
    private final String title;

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.id;
    }

    /* renamed from: component10, reason: from getter */
    public final int component10() {
        return this.progress;
    }

    /* renamed from: component11, reason: from getter */
    public final int component11() {
        return this.targetCount;
    }

    /* renamed from: component12, reason: from getter */
    public final String component12() {
        return this.objectiveType;
    }

    /* renamed from: component13, reason: from getter */
    public final String component13() {
        return this.objectiveDesc;
    }

    /* renamed from: component14, reason: from getter */
    public final String component14() {
        return this.giverName;
    }

    /* renamed from: component2, reason: from getter */
    public final String component2() {
        return this.title;
    }

    /* renamed from: component3, reason: from getter */
    public final String component3() {
        return this.description;
    }

    /* renamed from: component4, reason: from getter */
    public final String component4() {
        return this.faction;
    }

    /* renamed from: component5, reason: from getter */
    public final int component5() {
        return this.rewardCredits;
    }

    /* renamed from: component6, reason: from getter */
    public final int component6() {
        return this.rewardNanites;
    }

    /* renamed from: component7, reason: from getter */
    public final int component7() {
        return this.rewardXp;
    }

    /* renamed from: component8, reason: from getter */
    public final int component8() {
        return this.rewardReputationPoints;
    }

    /* renamed from: component9, reason: from getter */
    public final String component9() {
        return this.status;
    }

    public final Quest copy(String id, String title, String description, String faction, int rewardCredits, int rewardNanites, int rewardXp, int rewardReputationPoints, String status, int progress, int targetCount, String objectiveType, String objectiveDesc, String giverName) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(faction, "faction");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(objectiveType, "objectiveType");
        Intrinsics.checkNotNullParameter(objectiveDesc, "objectiveDesc");
        Intrinsics.checkNotNullParameter(giverName, "giverName");
        return new Quest(id, title, description, faction, rewardCredits, rewardNanites, rewardXp, rewardReputationPoints, status, progress, targetCount, objectiveType, objectiveDesc, giverName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Quest)) {
            return false;
        }
        Quest quest = (Quest) other;
        return Intrinsics.areEqual(this.id, quest.id) && Intrinsics.areEqual(this.title, quest.title) && Intrinsics.areEqual(this.description, quest.description) && Intrinsics.areEqual(this.faction, quest.faction) && this.rewardCredits == quest.rewardCredits && this.rewardNanites == quest.rewardNanites && this.rewardXp == quest.rewardXp && this.rewardReputationPoints == quest.rewardReputationPoints && Intrinsics.areEqual(this.status, quest.status) && this.progress == quest.progress && this.targetCount == quest.targetCount && Intrinsics.areEqual(this.objectiveType, quest.objectiveType) && Intrinsics.areEqual(this.objectiveDesc, quest.objectiveDesc) && Intrinsics.areEqual(this.giverName, quest.giverName);
    }

    public int hashCode() {
        return (((((((((((((((((((((((((this.id.hashCode() * 31) + this.title.hashCode()) * 31) + this.description.hashCode()) * 31) + this.faction.hashCode()) * 31) + Integer.hashCode(this.rewardCredits)) * 31) + Integer.hashCode(this.rewardNanites)) * 31) + Integer.hashCode(this.rewardXp)) * 31) + Integer.hashCode(this.rewardReputationPoints)) * 31) + this.status.hashCode()) * 31) + Integer.hashCode(this.progress)) * 31) + Integer.hashCode(this.targetCount)) * 31) + this.objectiveType.hashCode()) * 31) + this.objectiveDesc.hashCode()) * 31) + this.giverName.hashCode();
    }

    public String toString() {
        return "Quest(id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", faction=" + this.faction + ", rewardCredits=" + this.rewardCredits + ", rewardNanites=" + this.rewardNanites + ", rewardXp=" + this.rewardXp + ", rewardReputationPoints=" + this.rewardReputationPoints + ", status=" + this.status + ", progress=" + this.progress + ", targetCount=" + this.targetCount + ", objectiveType=" + this.objectiveType + ", objectiveDesc=" + this.objectiveDesc + ", giverName=" + this.giverName + ")";
    }

    public Quest(String id, String title, String description, String faction, int rewardCredits, int rewardNanites, int rewardXp, int rewardReputationPoints, String status, int progress, int targetCount, String objectiveType, String objectiveDesc, String giverName) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(faction, "faction");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(objectiveType, "objectiveType");
        Intrinsics.checkNotNullParameter(objectiveDesc, "objectiveDesc");
        Intrinsics.checkNotNullParameter(giverName, "giverName");
        this.id = id;
        this.title = title;
        this.description = description;
        this.faction = faction;
        this.rewardCredits = rewardCredits;
        this.rewardNanites = rewardNanites;
        this.rewardXp = rewardXp;
        this.rewardReputationPoints = rewardReputationPoints;
        this.status = status;
        this.progress = progress;
        this.targetCount = targetCount;
        this.objectiveType = objectiveType;
        this.objectiveDesc = objectiveDesc;
        this.giverName = giverName;
    }

    public /* synthetic */ Quest(String str, String str2, String str3, String str4, int i, int i2, int i3, int i4, String str5, int i5, int i6, String str6, String str7, String str8, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, i, i2, i3, i4, (i7 & 256) != 0 ? "AVAILABLE" : str5, (i7 & 512) != 0 ? 0 : i5, (i7 & 1024) != 0 ? 1 : i6, (i7 & 2048) != 0 ? "TALK" : str6, (i7 & 4096) != 0 ? "Speak with the representative" : str7, (i7 & 8192) != 0 ? "Operator" : str8);
    }

    public final String getId() {
        return this.id;
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getFaction() {
        return this.faction;
    }

    public final int getRewardCredits() {
        return this.rewardCredits;
    }

    public final int getRewardNanites() {
        return this.rewardNanites;
    }

    public final int getRewardXp() {
        return this.rewardXp;
    }

    public final int getRewardReputationPoints() {
        return this.rewardReputationPoints;
    }

    public final String getStatus() {
        return this.status;
    }

    public final int getProgress() {
        return this.progress;
    }

    public final int getTargetCount() {
        return this.targetCount;
    }

    public final String getObjectiveType() {
        return this.objectiveType;
    }

    public final String getObjectiveDesc() {
        return this.objectiveDesc;
    }

    public final String getGiverName() {
        return this.giverName;
    }
}
