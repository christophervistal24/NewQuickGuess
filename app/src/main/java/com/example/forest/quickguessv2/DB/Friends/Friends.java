package com.example.forest.quickguessv2.DB.Friends;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "friends")
public class Friends {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int status = 0;
    private int belongs_to_category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBelongs_to_category() {
        return belongs_to_category;
    }

    public void setBelongs_to_category(int belongs_to_category) {
        this.belongs_to_category = belongs_to_category;
    }
}
