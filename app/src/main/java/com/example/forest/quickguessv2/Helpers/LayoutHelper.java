package com.example.forest.quickguessv2.Helpers;
import android.view.View;
import android.view.ViewGroup;


public class LayoutHelper {



    public static void hideLayout(View v)
    {
        v.setVisibility(View.GONE);
    }

    public static void showLayout(View v)
    {
        v.setVisibility(View.VISIBLE);
    }
}