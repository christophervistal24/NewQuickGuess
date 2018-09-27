package com.example.forest.quickguessv2.Services.WebService;

public class RanksRequest {
        private String username;
        private int points;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
