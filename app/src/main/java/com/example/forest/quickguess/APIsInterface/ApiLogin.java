package com.example.forest.quickguess.APIsInterface;

import com.example.forest.quickguess.Services.WebService.UserLoginRequest;
import com.example.forest.quickguess.Services.WebService.UserLoginResponse;
import com.example.forest.quickguess.Services.WebService.UserRegisterRequest;
import com.example.forest.quickguess.Services.WebService.UserRegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiLogin {
    @POST("api/login")
    Call<UserLoginResponse> login(@Body UserLoginRequest userLoginRequest);
}
