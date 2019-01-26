package com.example.forest.quickguess.Models;

public class UserHistory {
    private int user_id;
    private int prev_user_life;
    private String game_over_time;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
