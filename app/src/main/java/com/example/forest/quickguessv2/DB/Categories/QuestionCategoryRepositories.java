package com.example.forest.quickguessv2.DB.Categories;

import android.content.Context;
import com.example.forest.quickguessv2.DB.DB;

public final class QuestionCategoryRepositories {
    public Context context;

    public QuestionCategoryRepositories(Context context) {
        this.context = context;
    }

    public void addCategory(String category_name , String category_description)
    {
        QuestionCategory questionCategory = new QuestionCategory();
        questionCategory.setCategory(category_name);
        questionCategory.setCategory_description(category_description);
        DB.getInstance(context).categoriesQuestionDao().insertAll(questionCategory);
    }

}
