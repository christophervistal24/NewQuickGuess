package com.example.forest.quickguessv2.Helpers;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.forest.quickguessv2.APIsInterface.IPointsInterface;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Points.Points;
import com.example.forest.quickguessv2.DB.Points.PointsRepositories;
import com.example.forest.quickguessv2.DB.User.UserRepositories;
import com.example.forest.quickguessv2.Services.Points.PointsRequest;
import com.example.forest.quickguessv2.Services.Points.PointsResponse;
import com.example.forest.quickguessv2.Services.Points.PointsService;

import java.util.function.BinaryOperator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
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
