package com.example.forest.quickguessv2.RecyclerView;

public class Questions {

    private String question_fun_facts;
    private String image;

    public Questions(String question_fun_facts, String image) {
        this.question_fun_facts = question_fun_facts;
        this.image = image;
    }

    public String getQuestion_fun_facts() {
        return question_fun_facts;
    }

    public void setQuestion_fun_facts(String question_fun_facts) {
        this.question_fun_facts = question_fun_facts;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}