package com.example.forest.quickguess.Models;

import android.content.Context;

import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Utilities.EncryptUtil;

public class FunFactsContent {

    private static String title;
    private static String content;
    private static String image;

    public FunFactsContent(Context context, String title, String content, String image) {
        FunFactsContent.title = title;
        FunFactsContent.content = content;
        FunFactsContent.image = image;
        setFunFactsContent(context);
    }

    private void setFunFactsContent(Context context)
    {
        try {
            SharedPreferenceHelper.PREF_FILE = "question";
            SharedPreferenceHelper.setSharedPreferenceString(context,"title",title);
            SharedPreferenceHelper.setSharedPreferenceString(context,"question_content", EncryptUtil.decryptMethod(content));
            SharedPreferenceHelper.setSharedPreferenceString(context,"question_image", EncryptUtil.decryptMethod(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
