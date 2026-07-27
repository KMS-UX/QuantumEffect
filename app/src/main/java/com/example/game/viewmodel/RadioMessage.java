package com.example.game.viewmodel;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameViewModel.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0007HÆ\u0003J1\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Lcom/example/game/viewmodel/RadioMessage;", "", "sender", "", "text", "timestamp", "frequency", "", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;F)V", "getSender", "()Ljava/lang/String;", "getText", "getTimestamp", "getFrequency", "()F", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes4.dex */
public final /* data */ class RadioMessage {
    public static final int $stable = 0;
    private final float frequency;
    private final String sender;
    private final String text;
    private final String timestamp;

    public static /* synthetic */ RadioMessage copy$default(RadioMessage radioMessage, String str, String str2, String str3, float f, int i, Object obj) {
        if ((i & 1) != 0) {
            str = radioMessage.sender;
        }
        if ((i & 2) != 0) {
            str2 = radioMessage.text;
        }
        if ((i & 4) != 0) {
            str3 = radioMessage.timestamp;
        }
        if ((i & 8) != 0) {
            f = radioMessage.frequency;
        }
        return radioMessage.copy(str, str2, str3, f);
    }

    /* renamed from: component1, reason: from getter */
    public final String component1() {
        return this.sender;
    }

    /* renamed from: component2, reason: from getter */
    public final String component2() {
        return this.text;
    }

    /* renamed from: component3, reason: from getter */
    public final String component3() {
        return this.timestamp;
    }

    /* renamed from: component4, reason: from getter */
    public final float component4() {
        return this.frequency;
    }

    public final RadioMessage copy(String sender, String text, String timestamp, float frequency) {
        Intrinsics.checkNotNullParameter(sender, "sender");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(timestamp, "timestamp");
        return new RadioMessage(sender, text, timestamp, frequency);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RadioMessage)) {
            return false;
        }
        RadioMessage radioMessage = (RadioMessage) other;
        return Intrinsics.areEqual(this.sender, radioMessage.sender) && Intrinsics.areEqual(this.text, radioMessage.text) && Intrinsics.areEqual(this.timestamp, radioMessage.timestamp) && Float.compare(this.frequency, radioMessage.frequency) == 0;
    }

    public int hashCode() {
        return (((((this.sender.hashCode() * 31) + this.text.hashCode()) * 31) + this.timestamp.hashCode()) * 31) + Float.hashCode(this.frequency);
    }

    public String toString() {
        return "RadioMessage(sender=" + this.sender + ", text=" + this.text + ", timestamp=" + this.timestamp + ", frequency=" + this.frequency + ")";
    }

    public RadioMessage(String sender, String text, String timestamp, float frequency) {
        Intrinsics.checkNotNullParameter(sender, "sender");
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(timestamp, "timestamp");
        this.sender = sender;
        this.text = text;
        this.timestamp = timestamp;
        this.frequency = frequency;
    }

    public final String getSender() {
        return this.sender;
    }

    public final String getText() {
        return this.text;
    }

    public final String getTimestamp() {
        return this.timestamp;
    }

    public final float getFrequency() {
        return this.frequency;
    }
}
