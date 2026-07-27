package com.example.game.db;

import androidx.core.app.NotificationCompat;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

/* compiled from: GameDatabase.kt */

/* loaded from: classes5.dex */
public interface GameProgressDao {
    Object clearProgress(Continuation<? super Unit> continuation);

    Object getProgress(Continuation<? super GameProgress> continuation);

    Flow<GameProgress> getProgressFlow();

    Object saveProgress(GameProgress gameProgress, Continuation<? super Unit> continuation);
}
