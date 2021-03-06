package com.example.forest.quickguess.Services.WebService;

import android.content.Context;

import com.example.forest.quickguess.R;

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
