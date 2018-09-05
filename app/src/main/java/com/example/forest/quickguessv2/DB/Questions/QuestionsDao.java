package com.example.forest.quickguessv2.DB.Questions;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface QuestionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Questions... ques);

/*    @Query("SELECT * FROM questions")
    List<Questions> getQuestion();*/

    @Query("SELECT COUNT(*) from questions")
    int countQuestion();

    @Query("SELECT * FROM questions WHERE category_id = :category_id AND id NOT " +
            "IN (SELECT question_id FROM user_status) LIMIT 5")
    List<Questions> getQuestionsByCategory(int category_id);
}
