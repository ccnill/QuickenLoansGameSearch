package com.example.gamesearch.di.module;

import com.example.gamesearch.di.scope.ApplicationScope;
import com.example.gamesearch.webservice.GameSearchApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class ApiModule {
    private static final String baseUrl =
            "https://www.giantbomb.com/api/";

    @Provides
    @ApplicationScope
    @Named("gameService")
    synchronized GameSearchApi gameSearchApi(@Named("gameRetrofit") Retrofit retrofit) {
        return retrofit.create(GameSearchApi.class);
    }

    @Provides
    @ApplicationScope
    @Named("gameRetrofit")
    public Retrofit retrofit(@Named("gameOkHttpClient") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @ApplicationScope
    @Named("gameOkHttpClient")
    OkHttpClient okHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        return client.build();

    }
}
