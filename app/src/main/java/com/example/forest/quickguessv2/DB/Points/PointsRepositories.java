package com.example.forest.quickguessv2.DB.Points;

import android.content.Context;

import com.example.forest.quickguessv2.DB.DB;

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

}
