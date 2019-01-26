package com.example.forest.quickguess.Services.WebService;

import android.content.Context;

import com.example.forest.quickguess.R;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserStatusService {

    public static Retrofit RetrofitInstance(Context context)
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Content-Type", "application/json");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.baseUrl))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
