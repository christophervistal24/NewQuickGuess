package com.example.forest.quickguess.APIsInterface;

import com.example.forest.quickguess.Services.WebService.PointsRequest;
import com.example.forest.quickguess.Services.WebService.PointsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIPoints {
    @POST("api/rank")
    Call<PointsResponse> updatePoints(@Body PointsRequest pointsRequest);
}
