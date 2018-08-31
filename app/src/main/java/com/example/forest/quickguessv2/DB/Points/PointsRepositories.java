package com.example.forest.quickguessv2.DB.Points;

import android.content.Context;

import com.example.forest.quickguessv2.APIsInterface.IPointsInterface;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.User.UserRepositories;
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

    public void sendPoints(final Points points)
    {
        if (getUserPoints() != 0)
        {
            //need to set this to 1 in order to replace the old score
            points.setId(1);

            Retrofit refrofit = PointsService.RetrofitInstance(context);
            IPointsInterface services = refrofit.create(IPointsInterface.class);

            PointsRequest pointsRequest = new PointsRequest();
            pointsRequest.setPoints(getUserPoints());
            pointsRequest.setUsername(UserRepositories.username(context));

            Call<PointsResponse> pointsResponseCall = services.updatePoints(pointsRequest);
            pointsResponseCall.enqueue(new Callback<PointsResponse>() {
                @Override
                public void onResponse(Call<PointsResponse> call, Response<PointsResponse> response) {
                    if  (response.isSuccessful())
                    {
                        DB.getInstance(context).pointsDao().delete(points);
                    }
                }

                @Override
                public void onFailure(Call<PointsResponse> call, Throwable t) {

                }
            });
        }

    }

}
