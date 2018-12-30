package com.example.forest.quickguess.Services.WebService;

public class UserRegisterRequest {
    private String username;
    private String password;

    public UserRegisterRequest(String name,String password) {
        this.setUsername(name);
        this.setPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
