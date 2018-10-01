package com.example.forest.quickguess.DB.Ranks;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "ranks",indices = {@Index(value = {"username"},
        unique = true)})
public class Ranks {
    @PrimaryKey(autoGenerate = true)
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
