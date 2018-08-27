package com.example.forest.quickguessv2;

import android.app.Application;
import android.view.View;

import com.example.forest.quickguessv2.Helpers.Detector;

public class ApplicationClass extends Application{
   private Detector Netdetector;

    @Override
    public void onCreate() {
        checkInternetConnection();
        super.onCreate();
    }

    public void checkInternetConnection()
    {
        Netdetector = new Detector(this);
        Netdetector.checkConnection();
    }


}
