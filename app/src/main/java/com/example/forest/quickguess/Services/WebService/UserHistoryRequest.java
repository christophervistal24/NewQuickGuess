package com.example.forest.quickguess.Services.WebService;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserHistoryRequest {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("prev_user_life")
    @Expose
    private int prev_user_life;

    @SerializedName("game_over_time")
    @Expose
    private String game_over_time;

    @SerializedName("token")
    @Expose
    private String token;


    public UserHistoryRequest(String username, int prev_user_life, String game_over_time, String token) {
        this.setUsername(username);
        this.setPrev_user_life(prev_user_life);
        this.setGame_over_time(game_over_time);
        this.setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPrev_user_life() {
        return prev_user_life;
    }

    public void setPrev_user_life(int prev_user_life) {
        this.prev_user_life = prev_user_life;
    }

    public String getGame_over_time() {
        return game_over_time;
    }

    public void setGame_over_time(String game_over_time) {
        this.game_over_time = game_over_time;
    }


}
