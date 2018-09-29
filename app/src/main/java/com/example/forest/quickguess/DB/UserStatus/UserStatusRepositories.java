package com.example.forest.quickguess.DB.UserStatus;

import android.content.Context;

import com.example.forest.quickguess.DB.DB;

public class UserStatusRepositories {

    public static int answeredByCategory(Context context, String name)
    {
        return DB.getInstance(context).userStatusDao().countAnsweredQuestion(
                DB.getInstance(context).categoriesQuestionDao().getCategoryIdByName(name)
        );
    }
}
