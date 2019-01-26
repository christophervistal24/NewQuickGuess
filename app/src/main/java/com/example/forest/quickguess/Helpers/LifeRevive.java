package com.example.forest.quickguess.Helpers;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.Utilities.GameOverUtil;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LifeRevive {

    private LifeRepositories lifeRepositories;
    private final int INTERVAL = 10000;
    private final long TWO_MINUTES = 2 * 60 * 1000;
    private CountDownTimer countDownTimer;
    private Context context;


    public LifeRevive(Context context,LifeRepositories lifeRepositories) {
        this.lifeRepositories = lifeRepositories;
        this.context = context;
    }


    //checking if the user game over 2 minutes ago
    public boolean isTwoMinutesAgo()
    {
         long gameOverTime = GameOverUtil.userGameOverTime(context);
         long twoMinutesAgo = System.currentTimeMillis() - gameOverTime;
         return (twoMinutesAgo >= TWO_MINUTES);
    }

    //display time left for life revival
    public CountDownTimer runTimerToView(TextView view)
    {
        //getting the time when user game over
        long gameOverTime = GameOverUtil.userGameOverTime(context);
        //subtracting current time and game over time
        long twoMinutesAgo = System.currentTimeMillis() - gameOverTime;
        //get the remaining seconds
        long remainingTime = TWO_MINUTES - twoMinutesAgo;

        countDownTimer = new CountDownTimer(remainingTime,1000) {
            public void onTick(long mill) {
                view.setText(String.format("%s", String.format(Locale.getDefault(), "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(mill),
                        TimeUnit.MILLISECONDS.toSeconds(mill) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mill))
                        ))
                );
            }
            public void onFinish() {
                UserRepositories.defaultLifetoUser(lifeRepositories);
                GameOverUtil.deleteGameOverTime(context);
                view.setText(String.valueOf(lifeRepositories.getUserLife()));
            }
        };
        return countDownTimer;
    }

    //checking if the user has no life or game over
    public boolean isUserGameOver()
    {
        return this.lifeRepositories.getUserLife() <= 0;
    }

    //watcher for checking if the app need to display timer or not
    public void watchLifeRevive(TextView view)
    {
        //game over time is past 2 minutes
        if (this.isUserGameOver() && this.isTwoMinutesAgo()) {
                UserRepositories.defaultLifetoUser(lifeRepositories);
        } else if (this.isUserGameOver() || !this.isTwoMinutesAgo()){
            //there's a remaining time for user game over time
                this.runTimerToView(view).start();
        }
    }




}
