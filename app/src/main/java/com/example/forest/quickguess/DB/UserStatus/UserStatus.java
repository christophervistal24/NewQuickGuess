package com.example.forest.quickguess.DB.UserStatus;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user_status")
public class UserStatus {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int user_id;
    private int question_id;
    private int question_result;
    private int category_id;
    private int class_id;

    public UserStatus creator(int question_id, int user_id, int question_result, int category_id, int class_id) {
        this.user_id = user_id;
        this.question_id = question_id;
        this.question_result = question_result;
        this.category_id = category_id;
        this.class_id = class_id;
        return this;
    }

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

    public int getCategory_id() {return category_id;}

    public void setCategory_id(int category_id) {this.category_id = category_id;}

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
