package com.example.forest.quickguess.Utilities;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class LaunchUtil {


    public static void animateWolfy(View v , int x , int y)
    {
        YoYo.with(Techniques.Wobble)
                .duration(2000)
                .pivot(x,y)
                .playOn(v);
    }

    public static void animateSnake(View v)
    {
        YoYo.with(Techniques.Swing)
                .duration(2000)
                .playOn(v);
    }
}
