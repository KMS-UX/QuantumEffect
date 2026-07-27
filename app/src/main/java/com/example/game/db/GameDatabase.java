package com.example.game.db;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameDatabase.kt */

/* loaded from: classes5.dex */
public abstract class GameDatabase extends RoomDatabase {
    private static volatile GameDatabase INSTANCE;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    public abstract GameProgressDao gameProgressDao();

    /* compiled from: GameDatabase.kt */
    
    /* loaded from: classes5.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final GameDatabase getDatabase(Context context) {
            GameDatabase gameDatabase;
            Intrinsics.checkNotNullParameter(context, "context");
            GameDatabase gameDatabase2 = GameDatabase.INSTANCE;
            if (gameDatabase2 != null) {
                return gameDatabase2;
            }
            synchronized (this) {
                Context applicationContext = context.getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                gameDatabase = (GameDatabase) Room.databaseBuilder(applicationContext, GameDatabase.class, "quantum_effect_db").fallbackToDestructiveMigration().build();
                Companion companion = GameDatabase.Companion;
                GameDatabase.INSTANCE = gameDatabase;
            }
            return gameDatabase;
        }
    }
}
