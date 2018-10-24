package com.example.forest.quickguess.DB.Categories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CategoriesQuestionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(QuestionCategory... questionCategories);

    @Query("SELECT COUNT(*) from question_category")
    int countCategories();

    @Query("SELECT id FROM question_category WHERE category = :category")
    int getCategoryIdByName(String category);

    @Query("SELECT * FROM question_category")
    List<QuestionCategory> getAllCategories();


    @Query("SELECT id FROM question_category")
    List<Integer> getAllIds();

}
