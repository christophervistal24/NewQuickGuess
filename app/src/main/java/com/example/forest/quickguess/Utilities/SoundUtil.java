package com.example.forest.quickguess.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundUtil {

    public static MediaPlayer songLoad(Context context, int uri)
    {
        return MediaPlayer.create(context,uri);
    }

}
