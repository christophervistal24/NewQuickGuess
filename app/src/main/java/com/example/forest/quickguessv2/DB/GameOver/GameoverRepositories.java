package com.example.forest.quickguessv2.DB.GameOver;

import com.example.forest.quickguessv2.DB.Life.LifeRepositories;

public class GameoverRepositories {

    public static void gameOver(LifeRepositories lifeRepositories)
    {
        if (lifeRepositories.getUserLife() <= 0)
        {
            lifeRepositories.setLifeToUser(5);
        }
    }

}
