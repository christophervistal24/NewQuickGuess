package com.example.forest.quickguessv2.Helpers;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.forest.quickguessv2.APIsInterface.IPointsInterface;
import com.example.forest.quickguessv2.DB.DB;
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
            //check if the user points in database
            int user_points = DB.getInstance(context).pointsDao().getUserPoints();
            if (user_points != 0)
            {
                Toast.makeText(context, String.valueOf(user_points), Toast.LENGTH_SHORT).show();
              /*  //send request
                Retrofit refrofit = PointsService.RetrofitInstance(context);
                IPointsInterface services = refrofit.create(IPointsInterface.class);
                PointsRequest pointsRequest = new PointsRequest();
                pointsRequest.setPoints(user_points);
                pointsRequest.setUsername(UserRepositories.username(context));
                Call<PointsResponse> pointsResponseCall = services.updatePoints(pointsRequest);
                pointsResponseCall.enqueue(new Callback<PointsResponse>() {
                    @Override
                    public void onResponse(Call<PointsResponse> call, Response<PointsResponse> response) {
                        if  (response.isSuccessful())
                        {
                            Toast.makeText(context, "Hello World", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PointsResponse> call, Throwable t) {

                    }
                });*/
            }
        } else
        {
            //return
            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }
}
