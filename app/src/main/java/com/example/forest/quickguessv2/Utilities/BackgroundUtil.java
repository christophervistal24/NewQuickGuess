package com.example.forest.quickguessv2.Utilities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguessv2.R;

import java.util.ArrayList;

public class BackgroundUtil {

    public static int setBackground(Context context, Intent i)
    {
        return context.getResources().getIdentifier(i.getStringExtra("category_name"),"drawable",context.getPackageName());
    }

    public static void radioBackgrounds(Context context, RadioGroup RGroup , String correct_answer)
    {
        ArrayList<RadioButton> listOfRadioButtons = new ArrayList<>();
        int count = RGroup.getChildCount();
        for(int i =0; i<count; i++)
        {
            View o = RGroup.getChildAt(i);
            if (o instanceof RadioButton) {
                listOfRadioButtons.add((RadioButton)o);
                if (listOfRadioButtons.get(i).getText().equals(correct_answer))
                {
//                    listOfRadioButtons.get(i).setBackground(ContextCompat.getDrawable(context, R.drawable.btn_correct));
                    YoYo.with(Techniques.RubberBand)
                            .duration(1500)
                            .repeat(-1)
                            .playOn(listOfRadioButtons.get(i));
                } else {
                    YoYo.with(Techniques.FlipOutX)
                            .duration(1500)
                            .playOn(listOfRadioButtons.get(i));
                }
                listOfRadioButtons.get(i).setClickable(false);
            }

        }
    }
}
