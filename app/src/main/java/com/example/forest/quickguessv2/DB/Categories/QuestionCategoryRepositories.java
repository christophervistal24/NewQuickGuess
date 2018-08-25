package com.example.forest.quickguessv2.DB.Categories;

import android.content.Context;
import android.util.Log;

import com.example.forest.quickguessv2.DB.DB;

public final class QuestionCategoryRepositories {
    private String[][] categories = {
            {"people","a"},
            {"animals","b"},
            {"geography","c"},
            {"sports","d"},
            {"music","e"},
            {"technology","f"},
            {"entertainment","g"},
    };

    public void addCategory(Context context)
    {
        if (isAlreadyInsert(context) == 0)
        for (String[] innerArray: categories) {
            QuestionCategory questionCategory = new QuestionCategory();
            questionCategory.setCategory(innerArray[0]);
            questionCategory.setCategory_description(innerArray[1]);
            DB.getInstance(context).categoriesQuestionDao().insertAll(questionCategory);
        }
    }

    private int isAlreadyInsert(Context context)
    {
         return DB.getInstance(context).categoriesQuestionDao().countCategories();
    }

}
