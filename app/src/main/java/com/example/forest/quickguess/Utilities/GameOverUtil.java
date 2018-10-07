package com.example.forest.quickguess.Utilities;

import android.content.Context;

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
}
