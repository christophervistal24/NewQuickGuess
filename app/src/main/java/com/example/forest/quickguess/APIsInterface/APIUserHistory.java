package com.example.forest.quickguess.APIsInterface;

import com.example.forest.quickguess.Services.WebService.UserHistoryRequest;
import com.example.forest.quickguess.Services.WebService.UserHistoryResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIUserHistory {
    @POST("api/userhistory")
    Call<UserHistoryResponse> insertUserHistory(@Body UserHistoryRequest userHistoryRequest);
}
