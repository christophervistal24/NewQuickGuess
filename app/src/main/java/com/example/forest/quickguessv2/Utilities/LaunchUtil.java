package com.example.forest.quickguessv2.Utilities;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dragankrstic.autotypetextview.AutoTypeTextView;
import com.example.forest.quickguessv2.R;

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
