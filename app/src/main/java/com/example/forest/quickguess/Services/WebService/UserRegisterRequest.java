package com.example.forest.quickguess.Services.WebService;

public class UserRegisterRequest {
    private String username;
    private String email;

    public UserRegisterRequest(String name, String email) {
        this.setUsername(name);
        this.setEmail(email);
    }




    private void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
