package com.example.forest.quickguessv2.DB.User;

import android.app.Activity;
import android.content.Context;

import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Life.LifeRepositories;
import com.example.forest.quickguessv2.Helpers.LayoutHelper;
import com.example.forest.quickguessv2.R;


public class UserRepositories {


    public static void createUser(Context context, User user) {
        DB.getInstance(context).myDao().addUser(user);
    }

    private static int checkUser(Context context)
    {
        return DB.getInstance(context).myDao().isUserExists();
    }

    public static String username(Context context)
    {
        return DB.getInstance(context).myDao().getUsername();
    }

    public static boolean isUserAlreadyRegister(Activity activity)
    {
        return (checkUser(activity.getApplicationContext())) >= 1;
    }

    public static void defaultLifetoUser(LifeRepositories lifeRepo)
    {
        lifeRepo.setLifeToUser(5);
    }

    public static int getLifeOfUser(LifeRepositories lifeRepo)
    {
       return lifeRepo.getUserLife();
    }


}
