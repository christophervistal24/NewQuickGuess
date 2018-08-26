package com.example.forest.quickguessv2.DB.GameOver;

import com.example.forest.quickguessv2.DB.Life.LifeRepositories;

public class Gameover {

    public static void isGameOver(LifeRepositories lifeRepositories)
    {
        if (lifeRepositories.getUserLife() <= 0)
        {
            lifeRepositories.setLifeToUser(5);
        }
    }

}
