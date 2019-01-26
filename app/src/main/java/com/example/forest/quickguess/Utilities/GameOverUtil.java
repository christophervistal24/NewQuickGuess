package com.example.forest.quickguess.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.User.User;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;

public class GameOverUtil {

    public static boolean isUserCanRebasegameOverCount = false;
    public static void saveTime(Context context,long value)
    {
        SharedPreferenceHelper.PREF_FILE="game_over_time";
        SharedPreferenceHelper.setSharedPreferenceLong(context,"time",value);
    }

    public static long userGameOverTime(Context context)
    {
        SharedPreferenceHelper.PREF_FILE="game_over_time";
        if (isGameOverTimeNegative(context)) {
            return 0;
        } else {
            return  SharedPreferenceHelper.getSharedPreferenceLong(context,"time",0);
        }
    }

    private static boolean isGameOverTimeNegative(Context context)
    {
        SharedPreferenceHelper.PREF_FILE="game_over_time";
        return  SharedPreferenceHelper.getSharedPreferenceLong(context,"time",0) <= -1;
    }

    public static void deleteGameOverTime(Context context)
    {
        SharedPreferenceHelper.PREF_FILE="game_over_time";
        SharedPreferenceHelper.removeSharedPreferenceLong(context,"time");
    }



    public static void setGameOverCount(Context context)
    {
        SharedPreferenceHelper.PREF_FILE="game_over_count";
        int gameOverCount = getGameOverCount(context);
        gameOverCount++;
        String username = DB.getInstance(context).userDao().getUsername();
        if (username != null) {
            SharedPreferenceHelper.setSharedPreferenceInt(context,username.concat("count"),gameOverCount);
        } else {
            SharedPreferenceHelper.setSharedPreferenceInt(context,"count",gameOverCount);
        }

    }

    public static int getGameOverCount(Context context)
    {
        SharedPreferenceHelper.PREF_FILE="game_over_count";
        String username = DB.getInstance(context).userDao().getUsername();
        if (username != null) {
            return SharedPreferenceHelper.getSharedPreferenceInt(context,username.concat("count"),3);
        } else {
            return SharedPreferenceHelper.getSharedPreferenceInt(context,"count",3);
        }
    }



    public static void isGameOverOrNot(Activity activity, LifeRepositories lifeRepositories, TextView life)
    {
        SharedPreferenceHelper.PREF_FILE="game_over_count";
        int decreaseCurrentLife = (lifeRepositories.getUserLife());
        lifeRepositories.setLifeToUser(--decreaseCurrentLife);
        if (lifeRepositories.getUserLife() <= 0 )
        {
            GameOverUtil.saveTime(activity.getApplicationContext(),System.currentTimeMillis());
            activity.finish();
        }
        life.setText(String.valueOf(lifeRepositories.getUserLife()));
    }
}
