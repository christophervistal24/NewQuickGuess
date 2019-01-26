package com.example.forest.quickguess.DB.Categories;

import android.content.Context;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;

public final class QuestionCategoryRepositories {
    public Context context;
    public QuestionCategoryRepositories(Context context) {
        this.context = context;
    }

    public static int getCountOfAllCategories(Context context) {
        return DB.getInstance(context).categoriesQuestionDao().countCategories();
    }

    public void categoryCreator(String category_name , String category_description)
    {
        QuestionCategory questionCategory = new QuestionCategory();
        questionCategory.setCategory(category_name);
        questionCategory.setCategory_description(category_description);
        DB.getInstance(context).categoriesQuestionDao().insertAll(questionCategory);
    }

    public static int getUserSelectedCategory(Context context)
    {
        SharedPreferenceHelper.PREF_FILE = "user_played";
        String selected_category = SharedPreferenceHelper
                .getSharedPreferenceString(context,"category",null)
                .toLowerCase();
        return DB.getInstance(context).categoriesQuestionDao().getCategoryIdByName(selected_category);
    }
}
