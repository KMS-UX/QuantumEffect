package com.example.game.api;


import kotlin.coroutines.Continuation;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/* compiled from: GeminiApi.kt */

/* loaded from: classes6.dex */
public interface GeminiApiService {
    @POST("v1beta/models/{model}:generateContent")
    Object generateContent(@Path("model") String str, @Query("key") String str2, @Body GenerateContentRequest generateContentRequest, Continuation<? super GenerateContentResponse> continuation);
}
