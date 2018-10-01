package com.example.forest.quickguess.Services.WebService;

import android.content.Context;

import com.example.forest.quickguess.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserStatusService {
    public static Retrofit RetrofitInstance()
    {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.1.107/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
