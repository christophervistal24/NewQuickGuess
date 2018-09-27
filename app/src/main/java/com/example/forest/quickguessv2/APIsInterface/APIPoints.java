package com.example.forest.quickguessv2.APIsInterface;

import com.example.forest.quickguessv2.Services.WebService.PointsRequest;
import com.example.forest.quickguessv2.Services.WebService.PointsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIPoints {
    @POST("api/rank")
    Call<PointsResponse> updatePoints(@Body PointsRequest pointsRequest);
}
