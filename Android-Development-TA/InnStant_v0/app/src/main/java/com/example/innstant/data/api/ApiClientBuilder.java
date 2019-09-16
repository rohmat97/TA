package com.example.innstant.data.api;

import com.example.innstant.data.PreferenceHelper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientBuilder {
    private static Retrofit mRetrofit;

    public static Retrofit getApiClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(PreferenceHelper.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
