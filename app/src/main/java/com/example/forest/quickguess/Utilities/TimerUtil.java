package com.example.forest.quickguess.Utilities;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.example.forest.quickguess.DB.Questions.QuestionRepositories;

public class TimerUtil {


    public static void changeTextColor(int remainingTime, TextView txtTimer, MediaPlayer clockTick)
    {
        if  (remainingTime == 1)
        {
            clockTick.release();
        } else {
            TimerUtil.changeColorAccordingToTimeRemain(remainingTime,txtTimer);
        }
    }

    private static void changeColorAccordingToTimeRemain(int time , TextView txtTimer)
    {
        if  (time <= 5)
        {
            txtTimer.setTextColor(Color.parseColor("#d1395c"));
        } else {
            String color = (QuestionRepositories.class_id == 3) ? "#ffffff" : "#ffffff";
            txtTimer.setTextColor(Color.parseColor(color));
        }


    }





}
