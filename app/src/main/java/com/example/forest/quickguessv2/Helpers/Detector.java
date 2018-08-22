package com.example.forest.quickguessv2.Helpers;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

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
            Toast.makeText(context, "User has internet", Toast.LENGTH_SHORT).show();
        } else
        {
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }
}
