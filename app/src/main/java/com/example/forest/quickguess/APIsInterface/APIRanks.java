package com.example.forest.quickguess.APIsInterface;

import com.example.forest.quickguess.Services.WebService.RanksRequest;
import com.example.forest.quickguess.Services.WebService.RanksResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIRanks {
    @POST("api/ranks")
    Call<List<RanksResponse>> getAllRanks(@Body RanksRequest ranksRequest);
}
