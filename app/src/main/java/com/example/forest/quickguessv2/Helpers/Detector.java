package com.example.forest.quickguessv2.Helpers;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.example.forest.quickguessv2.DB.Points.Points;
import com.example.forest.quickguessv2.DB.Points.PointsRepositories;



public class Detector {
    Context context;

    public Detector(Context context) {
        this.context = context;
    }

    private boolean isConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if(info != null)
            {
                return info.getState() == NetworkInfo.State.CONNECTED;
            }
        }
        return false;
    }

    public void checkConnection()
    {
        if(this.isConnected())
        {
            final Points points = new Points();
            PointsRepositories pointsRepositories = new PointsRepositories(context);
            pointsRepositories.sendPoints(points);
        } else
        {
            //return
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }
}
