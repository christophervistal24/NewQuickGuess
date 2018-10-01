package com.example.forest.quickguess.Services.WebService;

public class UserRegisterRequest {
    private String username;

    public UserRegisterRequest(String name) {
        this.setUsername(name);
    }





    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
