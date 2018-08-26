package com.example.forest.quickguessv2.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.forest.quickguessv2.DB.Categories.CategoriesQuestionDao;
import com.example.forest.quickguessv2.DB.Categories.QuestionCategory;
import com.example.forest.quickguessv2.DB.Life.Life;
import com.example.forest.quickguessv2.DB.Life.LifeDao;
import com.example.forest.quickguessv2.DB.Points.Points;
import com.example.forest.quickguessv2.DB.Points.PointsDao;
import com.example.forest.quickguessv2.DB.Questions.Questions;
import com.example.forest.quickguessv2.DB.Questions.QuestionsDao;
import com.example.forest.quickguessv2.DB.User.User;
import com.example.forest.quickguessv2.DB.User.UserDao;
import com.example.forest.quickguessv2.DB.UserStatus.UserStatus;
import com.example.forest.quickguessv2.DB.UserStatus.UserStatusDao;

@Database(entities = {User.class,QuestionCategory.class,Questions.class,UserStatus.class,Points.class , Life.class},version = 1)
public abstract class DB extends RoomDatabase
{
    private static DB appDatabase;
    private Context context;


    public abstract UserDao myDao();
    public abstract CategoriesQuestionDao categoriesQuestionDao();
    public abstract QuestionsDao questionsDao();
    public abstract UserStatusDao userStatusDao();
    public abstract PointsDao pointsDao();
    public abstract LifeDao lifeDao();

    public synchronized  static DB getInstance(Context context){
       if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), DB.class, "quickguess")
            .allowMainThreadQueries()
            .build();
        }
        return appDatabase;
}

    public void destroyInstance() {
            appDatabase = null;
    }

}
