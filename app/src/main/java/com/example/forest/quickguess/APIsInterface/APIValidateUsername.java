package com.example.forest.quickguess.APIsInterface;

import com.example.forest.quickguess.Services.WebService.PointsRequest;
import com.example.forest.quickguess.Services.WebService.PointsResponse;
import com.example.forest.quickguess.Services.WebService.UsernameRequest;
import com.example.forest.quickguess.Services.WebService.UsernameResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIValidateUsername {
    @POST("api/validate/username")
    Call<UsernameResponse> checkUsername(@Body UsernameRequest usernameRequest);
}
