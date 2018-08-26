package com.example.forest.quickguessv2.DB.Life;

import android.content.Context;

import com.example.forest.quickguessv2.DB.DB;

public class LifeRepositories {

    private Context context;

    public LifeRepositories(Context context)
    {
        this.context = context;
    }

    public void setLifeToUser(int defaultLife)
    {
        Life life = new Life();
        //to easily update when the user has the record in the database
        life.setId(1);
        life.setLife(defaultLife);
        DB.getInstance(context).lifeDao().insert(life);
    }


    public int getUserLife()
    {
        return DB.getInstance(context).lifeDao().getUserLife();
    }
}
