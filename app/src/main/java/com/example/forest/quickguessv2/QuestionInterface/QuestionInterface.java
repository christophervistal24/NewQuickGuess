package com.example.forest.quickguessv2.QuestionInterface;

public interface QuestionInterface {
    public void correct();
    public void wrong();
    public void getAnswer(String answer , String correct_answer);
    public void result();
}
