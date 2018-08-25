package com.example.forest.quickguessv2.DB.User;

import android.content.Context;

import com.example.forest.quickguessv2.DB.DB;



public class UserRepositories {



    public static void createUser(Context context, User user) {
        DB.getInstance(context).myDao().addUser(user);
    }


    public static int isAlreadyRegister(Context context)
    {
        return DB.getInstance(context).myDao().isUserExists();
    }
}
