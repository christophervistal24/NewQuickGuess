package com.example.forest.quickguessv2.DB.Questions;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.forest.quickguessv2.DB.Questions.Questions;

import java.util.List;

@Dao
public interface QuestionsDao {
    @Insert
    void insert(Questions... ques);

    @Query("SELECT * FROM questions")
    List<Questions> getAllQuestions();

    @Query("SELECT COUNT(*) from questions")
    int countQuestion();

    @Query("SELECT * FROM questions WHERE category_id = :category_id AND id NOT " +
            "IN (SELECT question_id FROM user_status)")
    List<Questions> getQuestionsByCategory(int category_id);
}
