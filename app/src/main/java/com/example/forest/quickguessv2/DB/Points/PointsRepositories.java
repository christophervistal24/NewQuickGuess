package com.example.forest.quickguessv2.DB.Points;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.forest.quickguessv2.APIsInterface.IPointsInterface;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.User.UserRepositories;
import com.example.forest.quickguessv2.Helpers.Connectivity;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.R;
import com.example.forest.quickguessv2.Services.Points.PointsRequest;
import com.example.forest.quickguessv2.Services.Points.PointsResponse;
import com.example.forest.quickguessv2.Services.Points.PointsService;

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

    public int sendPoints(final Points points)
    {
        SharedPreferenceHelper.PREF_FILE = "points";
        if (Connectivity.isConnectedWifi(context))
        {
            //need to set this to 1 in order to replace the old points
            points.setId(1);
            Retrofit refrofit = PointsService.RetrofitInstance(context);
            IPointsInterface services = refrofit.create(IPointsInterface.class);
            PointsRequest pointsRequest = new PointsRequest();
            pointsRequest.setPoints(getUserPoints());
            pointsRequest.setUsername(UserRepositories.username(context));
            //send an request
            Call<PointsResponse> pointsResponseCall = services.updatePoints(pointsRequest);

            pointsResponseCall.enqueue(new Callback<PointsResponse>() {
                @Override
                public void onResponse(Call<PointsResponse> call, Response<PointsResponse> response) {
                    if  (response.isSuccessful())
                    {
                        SharedPreferenceHelper.PREF_FILE = "points";
                        SharedPreferenceHelper.setSharedPreferenceInt(context,"user_points",response.body().getPoints());
                        DB.getInstance(context).pointsDao().delete(points);
                    } else {
                        //for detecting bugs purpose
                        SharedPreferenceHelper.PREF_FILE = "points";
                        SharedPreferenceHelper.setSharedPreferenceInt(context,"user_points",getUserPoints());
                    }
                }

                @Override
                public void onFailure(Call<PointsResponse> call, Throwable t) {
//                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

//        Toast.makeText(context, ""+String.valueOf(SharedPreferenceHelper.getSharedPreferenceInt(context,"user_points",0)+getUserPoints()), Toast.LENGTH_SHORT).show();
        return SharedPreferenceHelper.getSharedPreferenceInt(context,"user_points",0) + getUserPoints();
    }


}
