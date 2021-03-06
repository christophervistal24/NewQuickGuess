package com.example.forest.quickguess.Models;

public class Status {

    private String user_id;
    private int question_id;
    private int question_result;
    private int category_id;
    private int class_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }
}
