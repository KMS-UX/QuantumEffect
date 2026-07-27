package com.example.game.api;

import androidx.core.app.NotificationCompat;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/* compiled from: GeminiApi.kt */
/* loaded from: classes6.dex */
public final class RetrofitClient {
    public static final int $stable;
    private static final String BASE_URL = "https://generativelanguage.googleapis.com/";
    public static final RetrofitClient INSTANCE = new RetrofitClient();
    private static final Moshi moshi;

    /* renamed from: okHttpClient$delegate, reason: from kotlin metadata */
    private static final Lazy okHttpClient;

    /* renamed from: service$delegate, reason: from kotlin metadata */
    private static final Lazy service;

    private RetrofitClient() {
    }

    static {
        Moshi build = new Moshi.Builder().add((JsonAdapter.Factory) new KotlinJsonAdapterFactory()).build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        moshi = build;
        okHttpClient = LazyKt.lazy(new Function0() { // from class: com.example.game.api.RetrofitClient$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return RetrofitClient.okHttpClient_delegate$lambda$1();
            }
        });
        service = LazyKt.lazy(new Function0() { // from class: com.example.game.api.RetrofitClient$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return RetrofitClient.service_delegate$lambda$2();
            }
        });
        $stable = 8;
    }

    private final OkHttpClient getOkHttpClient() {
        return (OkHttpClient) okHttpClient.getValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final OkHttpClient okHttpClient_delegate$lambda$1() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(null, 1, 0 == true ? 1 : 0);
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().connectTimeout(60L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).writeTimeout(60L, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor).build();
    }

    public final GeminiApiService getService() {
        Object value = service.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (GeminiApiService) value;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final GeminiApiService service_delegate$lambda$2() {
        return (GeminiApiService) new Retrofit.Builder().baseUrl(BASE_URL).client(INSTANCE.getOkHttpClient()).addConverterFactory(MoshiConverterFactory.create(moshi)).build().create(GeminiApiService.class);
    }
}
