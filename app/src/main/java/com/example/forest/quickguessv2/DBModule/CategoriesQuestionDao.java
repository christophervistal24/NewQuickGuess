package com.example.forest.quickguessv2.DBModule;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CategoriesQuestionDao {
    @Insert
    public void insertQuestionCategory(List<QuestionCategory> categoriesItems);

    @Query("SELECT COUNT(*) from question_category")
    int countCategories();
}
