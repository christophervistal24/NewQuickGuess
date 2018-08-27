package com.example.forest.quickguessv2.Services.Points;

import android.content.Context;

import com.example.forest.quickguessv2.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PointsService {

    public static Retrofit RetrofitInstance(Context context)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
