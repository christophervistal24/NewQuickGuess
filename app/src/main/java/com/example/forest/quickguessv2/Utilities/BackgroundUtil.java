package com.example.forest.quickguessv2.Utilities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguessv2.R;

import java.util.ArrayList;
import java.util.List;

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

    public static void changeButtonsBackground(Button[] buttons, int level_id)
    {
       if (level_id == 1)
       {
           buttons[0].setBackgroundResource(R.drawable.btn_easy);
           buttons[1].setBackgroundResource(R.drawable.btn_easy);
           buttons[2].setBackgroundResource(R.drawable.btn_easy);
           buttons[3].setBackgroundResource(R.drawable.btn_easy);
       } else if (level_id == 2)
       {
           buttons[0].setBackgroundResource(R.drawable.btn_moderate);
           buttons[1].setBackgroundResource(R.drawable.btn_moderate);
           buttons[2].setBackgroundResource(R.drawable.btn_moderate);
           buttons[3].setBackgroundResource(R.drawable.btn_moderate);
       } else if (level_id == 3)
       {
           buttons[0].setBackgroundResource(R.drawable.btn_difficult);
           buttons[1].setBackgroundResource(R.drawable.btn_difficult);
           buttons[2].setBackgroundResource(R.drawable.btn_difficult);
           buttons[3].setBackgroundResource(R.drawable.btn_difficult);
       }
    }

    public static void changeAnswerQuestionBG(RelativeLayout answerQuestionLayout, int level_id)
    {
        if (level_id == 1)
        {
            answerQuestionLayout.setBackgroundResource(R.drawable.easy_bg);
        } else  if (level_id == 2){
            answerQuestionLayout.setBackgroundResource(R.drawable.moderate_bg);
        } else if (level_id == 3)
        {
            answerQuestionLayout.setBackgroundResource(R.drawable.difficult_bg);
        }
    }
}
