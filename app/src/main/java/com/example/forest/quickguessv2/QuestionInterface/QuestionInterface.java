package com.example.forest.quickguessv2.QuestionInterface;

public interface QuestionInterface {
    void correct();
    void wrong();
    void getAnswer(String answer, String correct_answer);
    void result();
}
