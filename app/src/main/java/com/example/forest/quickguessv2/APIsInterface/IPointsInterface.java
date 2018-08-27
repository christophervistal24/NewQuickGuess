package com.example.forest.quickguessv2.APIsInterface;

import com.example.forest.quickguessv2.Services.Points.PointsRequest;
import com.example.forest.quickguessv2.Services.Points.PointsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IPointsInterface {
    @POST("api/rank")
    Call<PointsResponse> updatePoints(@Body PointsRequest pointsRequest);
}
