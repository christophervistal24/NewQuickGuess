package com.example.forest.quickguess.Services.WebService;

import android.content.Context;

import com.example.forest.quickguess.R;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RanksService {
    public static Retrofit RetrofitInstance(Context context)
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15,TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.baseUrl))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
