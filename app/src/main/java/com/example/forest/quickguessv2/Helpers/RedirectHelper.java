package com.example.forest.quickguessv2.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.forest.quickguessv2.AnswerQuestion;
import com.example.forest.quickguessv2.CategoriesActivity;

public class RedirectHelper {



    public RedirectHelper(Context packageContext, Class<?> cls)
    {
        Intent intent = new Intent(packageContext,cls);
        packageContext.startActivity(intent);
    }

    public RedirectHelper(String category_name, Context packageContext, Class<?> cls)
    {
        Intent intent = new Intent(packageContext,cls);
        intent.putExtra("category_name",category_name);
        packageContext.startActivity(intent);
    }

  /*  private void gotoActivity(String category_name, CategoriesActivity categoriesActivity, Class<AnswerQuestion> categoriesActivityClass) {
        Intent intent = new Intent(categoriesActivity,categoriesActivityClass);
        intent.putExtra("category_name",category_name);
        Intent myIntent = new Intent(this, NewActivityClassName.class);
        startActivity(intent);
    }*/
}
