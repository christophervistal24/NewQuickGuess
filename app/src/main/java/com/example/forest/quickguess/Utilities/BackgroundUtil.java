package com.example.forest.quickguess.Utilities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;


import com.example.forest.quickguess.R;

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

                } else {

                }
                listOfRadioButtons.get(i).setClickable(false);
            }

        }
    }

    public static void changeButtonsBackground(Button[] buttons, int level_id)
    {
       if (level_id == 1)
       {

       } else if (level_id == 2)
       {

       } else if (level_id == 3)
       {

       }
    }

    public static void changeAnswerQuestionBG(RelativeLayout answerQuestionLayout, int level_id)
    {
        if (level_id == 1)
        {

        } else  if (level_id == 2){

        } else if (level_id == 3)
        {

        }
    }
}
