package com.example.forest.quickguess.QuestionInterface;

public interface QuestionInterface {
    void correct();
    void wrong();
    void getAnswer(String answer, String correct_answer);
    void result();
}
