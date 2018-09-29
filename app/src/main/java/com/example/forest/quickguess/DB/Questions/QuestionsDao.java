package com.example.forest.quickguess.DB.Questions;

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

    //get the unanswered question of the user
    @Query("SELECT * FROM questions WHERE category_id = :category_id AND classication_id = :classification_id AND id NOT " +
            "IN (SELECT question_id FROM user_status)")
    List<Questions> getQuestionsByCategoryAndClass(int category_id,int classification_id);

    //get all the questions by category_id
    @Query("SELECT * FROM questions WHERE category_id = :category_id")
    List<Questions> getQuestionByCategoryId(int category_id);

    //get the number of questions by id
    @Query("SELECT COUNT(*) FROM questions WHERE category_id = :category_id")
    int countQuestionByCategory(int category_id);

    @Query("SELECT COUNT(*) FROM questions WHERE category_id = :category_id AND " +
            "classication_id = :class_id")
    int countAllByClassification(int category_id,int class_id);

}
