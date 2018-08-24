package com.example.forest.quickguessv2.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.forest.quickguessv2.DB.Categories.CategoriesQuestionDao;
import com.example.forest.quickguessv2.DB.Categories.QuestionCategory;
import com.example.forest.quickguessv2.DB.Questions.Questions;
import com.example.forest.quickguessv2.DB.Questions.QuestionsDao;
import com.example.forest.quickguessv2.DB.User.User;
import com.example.forest.quickguessv2.DB.User.UserDao;
import com.example.forest.quickguessv2.DB.UserStatus.UserStatus;
import com.example.forest.quickguessv2.DB.UserStatus.UserStatusDao;

@Database(entities = {User.class,QuestionCategory.class,Questions.class,UserStatus.class},version = 1)
public abstract class MyAppDB extends RoomDatabase
{
    private static MyAppDB appDatabase;
    private Context context;

    public abstract UserDao myDao();
    public abstract CategoriesQuestionDao categoriesQuestionDao();
    public abstract QuestionsDao questionsDao();
    public abstract UserStatusDao userStatusDao();

    public static MyAppDB getInstance(Context context){
           if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), MyAppDB.class, "quickguess")
            .allowMainThreadQueries()
            .build();
        }
        return appDatabase;
}

    public static void destroyInstance() {
        appDatabase = null;
    }

}
