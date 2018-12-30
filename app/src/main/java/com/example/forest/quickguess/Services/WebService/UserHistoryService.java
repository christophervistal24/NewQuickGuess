package com.example.forest.quickguess.Services.WebService;

import android.content.Context;

import com.example.forest.quickguess.R;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.LongSerializationPolicy;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserHistoryService {
    public static Retrofit RetrofitInstance(Context context)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        Gson gson = gsonBuilder.create();

        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create(gson)  )
                .build();

    }
}
