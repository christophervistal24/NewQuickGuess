package com.example.forest.quickguess.Services.WebService;

import com.google.gson.annotations.SerializedName;

import butterknife.Optional;

public class UserStatusRequest {
    @SerializedName("data")
    private String data;

    @SerializedName("username")
    private String username;

    @SerializedName("token")
    private String token;





    public UserStatusRequest(String username , String data , String token ) {
        this.data = data;
        this.username = username;
        this.token = token;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public void setData(String data) {
        this.data = data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getData() {
        return data;
    }



}
