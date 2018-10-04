package com.example.forest.quickguess.DB.Categories;

import android.content.Context;
import com.example.forest.quickguess.DB.DB;

public final class QuestionCategoryRepositories {
    public Context context;

    public QuestionCategoryRepositories(Context context) {
        this.context = context;
    }

    public void categoryCreator(String category_name , String category_description)
    {
        QuestionCategory questionCategory = new QuestionCategory();
        questionCategory.setCategory(category_name);
        questionCategory.setCategory_description(category_description);
        DB.getInstance(context).categoriesQuestionDao().insertAll(questionCategory);
    }

}