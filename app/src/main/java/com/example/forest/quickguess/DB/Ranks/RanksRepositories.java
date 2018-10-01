package com.example.forest.quickguess.DB.Ranks;

import android.content.Context;

import com.example.forest.quickguess.DB.DB;

public class RanksRepositories {
    private Context context;
    public RanksRepositories(Context context) {
        this.context = context;
    }

    public void rankCreator(String username , int points)
    {
        Ranks ranks = new Ranks();
        ranks.setUsername(username);
        ranks.setPoints(points);
        DB.getInstance(context).ranksDao().insert(ranks);
    }
}
