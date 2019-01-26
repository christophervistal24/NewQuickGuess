package com.example.forest.quickguess.Models;

import android.content.Context;

import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Utilities.EncryptUtil;

public class FunFactsContent {

    private static String title;
    private static String content;
    private static String image;

    public FunFactsContent(Context context, String title, String content, String image) throws Exception {
        setTitle(title);
        setContent(content);
        setImage(image);
    }

    public static void setTitle(String title) {
        FunFactsContent.title = title;
    }

    public static void setContent(String content) throws Exception {
        FunFactsContent.content = EncryptUtil.decryptMethod(content);
    }

    public static void setImage(String image) throws Exception {
        FunFactsContent.image = EncryptUtil.decryptMethod(image);
    }

    public static String getTitle() {
        return title;
    }

    public static String getContent() {
        return content;
    }

    public static String getImage() {
        return image;
    }
}
