package com.example.forest.quickguess.DB.GameOver;

import com.example.forest.quickguess.DB.Life.LifeRepositories;

public class GameoverRepositories {

    public static void gameOver(LifeRepositories lifeRepositories)
    {
        if (lifeRepositories.getUserLife() <= 0)
        {
            lifeRepositories.setLifeToUser(5);
        }
    }

}
