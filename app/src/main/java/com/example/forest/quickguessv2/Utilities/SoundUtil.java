package com.example.forest.quickguessv2.Utilities;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class SoundUtil {

    public static MediaPlayer songLoad(Context context, int uri)
    {
        return MediaPlayer.create(context,uri);
    }

}
