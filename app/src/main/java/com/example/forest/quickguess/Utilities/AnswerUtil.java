package com.example.forest.quickguess.Utilities;

import android.os.Bundle;


public class AnswerUtil {

    public static boolean checkAnswer(String answer , Bundle bundle , String correct_answer)
    {
        if (answer.trim().equalsIgnoreCase(correct_answer.trim()))
        {
            GameBundleUitl.setQuestionResult(bundle,"result","correct_icon");
            return true;
        }
            GameBundleUitl.setQuestionResult(bundle,"result","wrong_icon");
            return false;
    }
}
