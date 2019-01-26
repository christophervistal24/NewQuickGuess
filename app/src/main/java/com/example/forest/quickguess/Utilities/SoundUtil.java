package com.example.forest.quickguess.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;

public class SoundUtil {

   public static MediaPlayer sfx;

    public static MediaPlayer songLoad(Context context, int uri)
    {
        return MediaPlayer.create(context,uri);
    }

    private static boolean checkUserOption(Context context)
    {
        SharedPreferenceHelper.PREF_FILE = "sound_fx";
        return SharedPreferenceHelper.getSharedPreferenceBoolean(context,"fx",true);
    }

    public static void playMusic(Context context, int music)
    {
       /* if (checkUserOption(context))
        {*/
          sfx = MediaPlayer.create(context,music);
            sfx.start();
            sfx.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer sfx) {
                    sfx.release();
                }
            });
//        }

    }



}
