package com.example.forest.quickguess.APIsInterface;


import com.example.forest.quickguess.Services.WebService.UserStatusRequest;
import com.example.forest.quickguess.Services.WebService.UserStatusResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APISendStatus {
    @POST("api/userstat")
    Call<UserStatusResponse> sendUserStatus(@Body UserStatusRequest userStatusRequest);
}
