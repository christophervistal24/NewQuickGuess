package com.example.forest.quickguessv2.DB.Life;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "life")
public class Life {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "user_life")
    private int life;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
