package com.example.forest.quickguess.Utilities;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;

public class GameOverUtil {

    public static void saveTime(Context context,long value)
    {
        SharedPreferenceHelper.PREF_FILE="game_over_time";
        SharedPreferenceHelper.setSharedPreferenceLong(context,"time",value);
    }

    public static long userGameOverTime(Context context)
    {
        SharedPreferenceHelper.PREF_FILE="game_over_time";
        return  SharedPreferenceHelper.getSharedPreferenceLong(context,"time",0);
    }

    public static void deleteGameOverTime(Context context)
    {
        SharedPreferenceHelper.PREF_FILE="game_over_time";
        SharedPreferenceHelper.removeSharedPreferenceLong(context,"time");
    }

    public static void isGameOverOrNot(Activity activity, LifeRepositories lifeRepositories, TextView life)
    {
        int decreaseCurrentLife = (lifeRepositories.getUserLife());
        lifeRepositories.setLifeToUser(--decreaseCurrentLife);
            if (lifeRepositories.getUserLife() <= 0)
            {
                GameOverUtil.saveTime(activity.getApplicationContext(),System.currentTimeMillis());
                activity.finish();
            }
        life.setText(String.valueOf(lifeRepositories.getUserLife()));
    }
}
