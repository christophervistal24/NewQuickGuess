package com.example.forest.quickguessv2.Helpers;

import android.content.Context;
import android.graphics.Typeface;

public class FontHelper {

    public Typeface dimboFont(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"fonts/Dimbo Regular.ttf");
    }
}
