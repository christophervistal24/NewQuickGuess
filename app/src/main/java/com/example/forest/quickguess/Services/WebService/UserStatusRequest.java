package com.example.forest.quickguess.Services.WebService;

import com.example.forest.quickguess.DB.UserStatus.UserStatus;

import java.util.List;

public class UserStatusRequest {
    private String data;


    public UserStatusRequest(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
