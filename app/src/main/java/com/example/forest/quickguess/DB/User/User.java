package com.example.forest.quickguess.DB.User;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "users")
public class User
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;

    public User(String username) {
        setUsername(username);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }


}
