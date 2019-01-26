package com.example.forest.quickguess.Utilities;

import android.app.Activity;
import android.content.Context;

import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.Helpers.Connectivity;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.R;

public class UserUtil {

    //checking if the user has internet connection and already register
    public static boolean isUserNotFinishToCreateAnAccount(Activity activity)
    {
        return Connectivity.isConnectedWifi(activity.getApplicationContext())
                                    && !UserRepositories.isUserAlreadyRegister(activity);
    }

    public static int intiUserPoints(Context context)
    {
        return (DB.getInstance(context).userStatusDao().countAllForPoints() * 100) + DB.getInstance(context).pointsDao().getUserPoints();
    }

    public static String isUserGuestOrNot(Context context)
    {
        String username = DB.getInstance(context).userDao().getUsername();
        if (username != null) {
                return username.substring(0,1).toUpperCase() + username.substring(1);
        } else {
                return context.getString(R.string.user_guest);
        }
    }

    //get the user token in shared pref
    public static boolean isUserHasAToken(Context context)
    {
       return getUserToken(context) != null;
    }

    public static String getUserToken(Context context)
    {
        SharedPreferenceHelper.PREF_FILE ="user_token";
        return SharedPreferenceHelper.getSharedPreferenceString(context,"token",null);
    }

    public static void setUserToken(Context context, String token)
    {
        SharedPreferenceHelper.PREF_FILE ="user_token";
        SharedPreferenceHelper.setSharedPreferenceString(context,"token",token);
    }

    public static void setUserIdForSharePref(Context context , int userId)
    {
        SharedPreferenceHelper.PREF_FILE = "user";
        SharedPreferenceHelper.setSharedPreferenceInt(context,"user_id",userId);
    }

    public static int getUserIdInSharedPref(Context context)
    {
        SharedPreferenceHelper.PREF_FILE = "user";
        return SharedPreferenceHelper.getSharedPreferenceInt(context,"user_id",0);
    }

    public static String getSelectedCategoryOfUser(Context context)
    {
        SharedPreferenceHelper.PREF_FILE = "user_played";
        return SharedPreferenceHelper
                .getSharedPreferenceString(context,"category",null);
    }

    public static int getSelectedDifficultyOfUser(Context context)
    {
        SharedPreferenceHelper.PREF_FILE="user_played";
        return Integer.parseInt(SharedPreferenceHelper.getSharedPreferenceString(context,"class_id",null));
    }

    public static void setUserCategoryPlayed(Context context,String category)
    {
        SharedPreferenceHelper.PREF_FILE="user_played";
        SharedPreferenceHelper.setSharedPreferenceString(context,"category",category);
    }

    public static void setUserCategoryDifficultPlayed(Context context, String level)
    {
        SharedPreferenceHelper.PREF_FILE="user_played";
        SharedPreferenceHelper.setSharedPreferenceString(context,"class_id",level);
    }
}
