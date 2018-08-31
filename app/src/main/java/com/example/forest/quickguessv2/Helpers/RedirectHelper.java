package com.example.forest.quickguessv2.Helpers;

import android.content.Context;
import android.content.Intent;
public class RedirectHelper {



    public RedirectHelper(Context packageContext, Class<?> cls)
    {
        Intent intent = new Intent(packageContext,cls);
        packageContext.startActivity(intent);
    }

    public RedirectHelper(String category_name, Context packageContext, Class<?> cls)
    {
        Intent intent = new Intent(packageContext,cls);
        intent.putExtra("category_name",category_name);
        packageContext.startActivity(intent);
    }
}
