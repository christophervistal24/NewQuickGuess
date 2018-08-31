package com.example.forest.quickguessv2.DB.User;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Life.LifeRepositories;
import com.example.forest.quickguessv2.DB.Points.Points;
import com.example.forest.quickguessv2.DB.Points.PointsRepositories;
import com.example.forest.quickguessv2.Utilities.PointsUtil;


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

    public static void isUserHasPoints(Context context , int userPoints , PointsRepositories pointsRepositories)
    {
        int currect_points = DB.getInstance(context).pointsDao().getUserPoints();
        userPoints++;
        Points points = new Points();
        points.setId(1);
        try {
            points.setPoints(PointsUtil.plusExtra(userPoints));
            DB.getInstance(context).pointsDao().insert(points);
        } catch (SQLiteConstraintException exception) {
            userPoints = (PointsUtil.plusExtra(userPoints)) + currect_points;
            points.setPoints(0);
            points.setPoints(userPoints);
            DB.getInstance(context).pointsDao().update(points);
        }
        pointsRepositories.sendPoints(points);
    }

    public static int getUserPoints(PointsRepositories pointsRepositories)
    {
        return pointsRepositories.getUserPoints();
    }


}
