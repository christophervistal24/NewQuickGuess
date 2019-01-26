package com.example.forest.quickguess.DB.Points;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguess.APIsInterface.APIPoints;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.Helpers.Connectivity;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Services.WebService.PointsRequest;
import com.example.forest.quickguess.Services.WebService.PointsResponse;
import com.example.forest.quickguess.Services.WebService.PointsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PointsRepositories {

    private Context context;


    public PointsRepositories(Context context)
    {
        this.context = context;
    }

    public int getUserPoints()
    {
        return DB.getInstance(context).pointsDao().getUserPoints();
    }

    public void sendPoints()
    {
        if (Connectivity.isConnectedWifi(context) && UserRepositories.isUserAlreadyRegister(context))
        {

            Retrofit refrofit = PointsService.RetrofitInstance(context);
            APIPoints services = refrofit.create(APIPoints.class);
            PointsRequest pointsRequest = new PointsRequest();
            int fetchPointsByAnswer = DB.getInstance(context).userStatusDao().countAllForPoints();
            int extraPoints = DB.getInstance(context).pointsDao().getUserPoints();
            pointsRequest.setPoints( (fetchPointsByAnswer * 100) + extraPoints);
            pointsRequest.setUsername(UserRepositories.username(context));
            //send an request
            Call<PointsResponse> pointsResponseCall = services.updatePoints(pointsRequest);

            pointsResponseCall.enqueue(new Callback<PointsResponse>() {
                @Override
                public void onResponse(Call<PointsResponse> call, Response<PointsResponse> response) {
                }

                @Override
                public void onFailure(Call<PointsResponse> call, Throwable t) {
                }
            });
        }
    }

    public void initUserPoints(TextView points)
    {
        int extraPoints = DB.getInstance(context).pointsDao().getUserPoints();
        int current_points = (DB.getInstance(context).userStatusDao().countAllForPoints() * 100) + extraPoints ;
        points.setText(String.valueOf(current_points));
    }


}
