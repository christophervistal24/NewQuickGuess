package com.example.forest.quickguessv2.Utilities;

import android.app.Activity;

import me.anwarshahriar.calligrapher.Calligrapher;

public class TypeFaceUtil {

   public static void initFont(Activity activity)
   {
       Calligrapher calligrapher = new Calligrapher(activity.getApplicationContext());
       calligrapher.setFont(activity, "fonts/Dimbo_Regular.ttf", true);
   }

}
