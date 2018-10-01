package com.example.forest.quickguess.DB.UserStatus;

import android.content.Context;

import com.example.forest.quickguess.DB.DB;

import java.util.List;

public class UserStatusRepositories {

    public static int answeredByCategory(Context context, String name)
    {
        return DB.getInstance(context).userStatusDao().countAnsweredQuestion(
                DB.getInstance(context).categoriesQuestionDao().getCategoryIdByName(name)
        );
    }

    public static List<UserStatus> getAndSendStatus(Context context)
    {
        if  (DB.getInstance(context).userStatusDao().countAllForPoints() == 0) {
                return null;
        }
        return DB.getInstance(context).userStatusDao().getAllStatusOfUser();
    }
}
