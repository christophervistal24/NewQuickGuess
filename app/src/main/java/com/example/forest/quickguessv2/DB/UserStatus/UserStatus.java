package com.example.forest.quickguessv2.DB.UserStatus;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user_status")
public class UserStatus {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int question_id;
    private int question_result;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getQuestion_result() {
        return question_result;
    }

    public void setQuestion_result(int question_result) {
        this.question_result = question_result;
    }

}
