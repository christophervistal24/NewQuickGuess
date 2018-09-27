package com.example.forest.quickguessv2.Services.WebService;

import android.content.Context;

import com.example.forest.quickguessv2.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PointsService {

    public static Retrofit RetrofitInstance(Context context)
    {
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
