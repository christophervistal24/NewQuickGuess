package com.example.forest.quickguess.Utilities;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.widget.TextView;

public class TimerUtil {

    public static void changeTextColor(int remainingTime, TextView txtTimer, MediaPlayer clockTick)
    {
        if  (remainingTime <= 5)
        {
            txtTimer.setTextColor(Color.parseColor("#d1395c"));
        }
        else {
            txtTimer.setTextColor(Color.parseColor("#707070"));
        }

        if  (remainingTime == 1)
        {
            clockTick.release();
        }
    }
}
