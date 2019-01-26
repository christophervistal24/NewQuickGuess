package com.example.forest.quickguess.Utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguess.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.R;
import com.marcouberti.autofitbutton.AutoFitButton;

import java.util.ArrayList;

public class BackgroundUtil {

    private static final String[] list_of_answer_question_bg = {
            "easy_bg",
            "moderate_bg",
            "difficult_bg"
    };

    private static final String[] list_of_btn_background = {
            "btn_easy",
            "btn_moderate",
            "btn_difficult"
    };

    private static int difficulty_level;

    private static int getBackground(Context context, String bg_name)
    {
        return context.getResources().getIdentifier(bg_name,"drawable",context.getPackageName());
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

    public static void changeButtonsBackground(AutoFitButton[] buttons , Context context)
    {
        int backgroundForBtn = BackgroundUtil.getBackground(context,list_of_btn_background[difficulty_level-1]);
        for(AutoFitButton btn : buttons)
            btn.setBackgroundResource(backgroundForBtn);
    }

    public static void changeAnswerQuestionBG(ScrollView answerQuestionLayout , Context context)
    {
        difficulty_level = QuestionRepositories.class_id;
        answerQuestionLayout.setBackgroundResource(BackgroundUtil.getBackground(context,list_of_answer_question_bg[difficulty_level-1]));
    }

    public static void changeColorOfTextView(TextView txtView)
    {
        String colorForTextView = (difficulty_level == 3) ? "#ffffff" : "#707070";
        txtView.setTextColor(Color.parseColor(colorForTextView));
    }
}
