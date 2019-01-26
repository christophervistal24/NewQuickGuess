package com.example.forest.quickguess.Utilities;

import android.app.Activity;
import android.content.Context;

import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;

public class BGMusicUtil {
    public static boolean isUserWantToPlayBGMusic(Context context)
    {
        SharedPreferenceHelper.PREF_FILE = "bg_music";
        return SharedPreferenceHelper
                    .getSharedPreferenceBoolean(context,"sound",true);
    }

    public static void setBGMusicToOn(Context context)
    {
        SharedPreferenceHelper.PREF_FILE = "bg_music";
        SharedPreferenceHelper.setSharedPreferenceBoolean(context,"sound",true);
    }

    public static void setBGMusicToOff(Context context)
    {
        SharedPreferenceHelper.PREF_FILE = "bg_music";
        SharedPreferenceHelper.setSharedPreferenceBoolean(context,"sound",false);
    }


}
