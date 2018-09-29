package com.example.forest.quickguess.DB.Points;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user_points")
public class Points {

    @PrimaryKey
    private int id;
    private int points = 0;


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

