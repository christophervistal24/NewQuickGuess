package com.example.forest.quickguess.DB.User;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.widget.Toast;

import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.Points.Points;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.example.forest.quickguess.Utilities.PointsUtil;



public class UserRepositories {


    //create new user
    public static void createUser(Context context, User user) {
        DB.getInstance(context).userDao().addUser(user);
    }

    /*//check if user already exists
    private static int checkUser(Context context)
    {
        return DB.getInstance(context).userDao().isUserExists();
    }*/

    //get player username
    public static String username(Context context)
    {
        return DB.getInstance(context).userDao().getUsername();
    }

    //check if already exists
    public static boolean isUserAlreadyRegister(Context context)
    {
        return (DB.getInstance(context).userDao().isUserExists()) >= 1;
    }

    //default life to user
    public static void defaultLifetoUser(LifeRepositories lifeRepo)
    {
        lifeRepo.setLifeToUser(10);
    }

    //get the current life of the user
    public static int getLifeOfUser(LifeRepositories lifeRepo)
    {
       return lifeRepo.getUserLife();
    }

    //get the points of the user
    public static int getUserPoints(PointsRepositories pointsRepositories)
    {
        return pointsRepositories.getUserPoints();
    }




    //check if user has points
    public static void isUserHasPoints(Context context , int userPoints , PointsRepositories pointsRepositories)
    {
        //get the user points
        Points points = new Points();
        int current_points = (DB.getInstance(context).userStatusDao().countAllForPoints()) * 100;

        //set the points id to 1 to easily replace the existing point
        points.setId(1);
        try { // if user no points
            points.setPoints(current_points);
            DB.getInstance(context).pointsDao().insert(points);
        } catch (SQLiteConstraintException exception) { //update the existing points
            userPoints = current_points;
            points.setPoints(0);
            points.setPoints(userPoints);
            DB.getInstance(context).pointsDao().update(points);
        }
        //sending to rest api the points
        pointsRepositories.sendPoints();
    }




}
