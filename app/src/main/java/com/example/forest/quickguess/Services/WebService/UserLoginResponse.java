package com.example.forest.quickguess.Services.WebService;

import com.example.forest.quickguess.Models.Status;
import com.example.forest.quickguess.Models.UserHistory;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class UserLoginResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("stat")
    private List<Status> stat;

    @SerializedName("user_history")
    private UserHistory userHistory;

    @SerializedName("token")
    private String token;

    @SerializedName("status")
    private boolean status;

    @SerializedName("reset_stat")
    private int reset_stat;

    public UserHistory getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(UserHistory userHistory) {
        this.userHistory = userHistory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Status> getStat() {
        return stat;
    }

    public void setStat(List<Status> stat) {
        this.stat = stat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getReset_stat() {
        return reset_stat;
    }

    public void setReset_stat(int reset_stat) {
        this.reset_stat = reset_stat;
    }
}
