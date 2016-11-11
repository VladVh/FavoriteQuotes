package com.development.vvoitsekh.favoritequotes.network;

import android.support.annotation.NonNull;

import com.development.vvoitsekh.favoritequotes.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by v.voitsekh on 10.11.2016.
 */

public final class ApiFactory {

    private static OkHttpClient sClient;

    private static QuoteService sService;

    public static QuoteService getMoviesService() {
        //I know that double checked locking is not a good pattern, but it's enough here
        QuoteService service = sService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = createService();
                }
            }
        }
        return service;
    }

    private static QuoteService createService() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(QuoteService.class);
    }

    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .build();
    }
}