package com.example.forest.quickguessv2.DBModule;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CategoriesQuestionDao {

    @Insert
    void insertAll(QuestionCategory... questionCategories);

    @Query("SELECT COUNT(*) from question_category")
    int countCategories();

    @Query("SELECT * FROM question_category")
    List<QuestionCategory> getAllCategories();


}
