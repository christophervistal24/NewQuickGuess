package com.example.forest.quickguess.Utilities;

import android.app.Activity;

import me.anwarshahriar.calligrapher.Calligrapher;

public class TypeFaceUtil {

   public static void initDimboFont(Activity activity)
   {
       Calligrapher calligrapher = new Calligrapher(activity.getApplicationContext());
       calligrapher.setFont(activity, "fonts/Dimbo_Regular.ttf", true);
   }

    public static void iniDokboFont(Activity activity)
    {
        Calligrapher calligrapher = new Calligrapher(activity.getApplicationContext());
        calligrapher.setFont(activity, "fonts/Dokdo_Regular.ttf", true);
    }


}
