package com.example.forest.quickguess.Services.WebService;

import com.google.gson.annotations.SerializedName;

public class UsernameResponse {
    @SerializedName("status")
    public boolean status;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }





}
