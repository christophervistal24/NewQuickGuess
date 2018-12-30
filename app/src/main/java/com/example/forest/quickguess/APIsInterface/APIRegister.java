package com.example.forest.quickguess.APIsInterface;

import com.example.forest.quickguess.Services.WebService.PointsRequest;
import com.example.forest.quickguess.Services.WebService.PointsResponse;
import com.example.forest.quickguess.Services.WebService.UserRegisterRequest;
import com.example.forest.quickguess.Services.WebService.UserRegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIRegister {
    @POST("api/register")
    Call<UserRegisterResponse> register(@Body UserRegisterRequest userRegisterRequest);
}
