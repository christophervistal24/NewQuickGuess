package com.example.forest.quickguess;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.forest.quickguess.APIsInterface.APIRanks;
import com.example.forest.quickguess.APIsInterface.APISendStatus;
import com.example.forest.quickguess.DB.Categories.QuestionCategory;
import com.example.forest.quickguess.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Friends.FriendsRepositories;
import com.example.forest.quickguess.DB.Points.Points;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.DB.UserStatus.UserStatus;
import com.example.forest.quickguess.Helpers.Connectivity;
import com.example.forest.quickguess.Service.backgroundMusicService;
import com.example.forest.quickguess.Services.WebService.RanksRequest;
import com.example.forest.quickguess.Services.WebService.RanksResponse;
import com.example.forest.quickguess.Services.WebService.RanksService;
import com.example.forest.quickguess.Services.WebService.UserRegisterResponse;
import com.example.forest.quickguess.Services.WebService.UserStatusRequest;
import com.example.forest.quickguess.Services.WebService.UserStatusResponse;
import com.example.forest.quickguess.Services.WebService.UserStatusService;
import com.example.forest.quickguess.Utilities.EncryptUtil;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApplicationClass extends Application  implements Application.ActivityLifecycleCallbacks {

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";
    private QuestionCategoryRepositories questionCategoryRepositories;
    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;



    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        createNotificationChannels();
        questionCategoryRepositories = new QuestionCategoryRepositories(this);
        insertCategories();
    }

    private void createNotificationChannels()
    {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "channel1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "channel2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }*/



    }



    private void insertCategories()
    {
        if  (QuestionCategoryRepositories.getCountOfAllCategories(this) == 0)
        {
            questionCategoryRepositories.categoryCreator("people","All about peoples"); //1
            questionCategoryRepositories.categoryCreator("plants","All about plants"); //2
            questionCategoryRepositories.categoryCreator("animals","All about animals"); //3
            questionCategoryRepositories.categoryCreator("geography","All about places"); //4
            questionCategoryRepositories.categoryCreator("sports","All about sports"); //5
            questionCategoryRepositories.categoryCreator("music","All about music"); //6
            questionCategoryRepositories.categoryCreator("technology","All about technologies"); //7
            questionCategoryRepositories.categoryCreator("entertainment","All about entertainment"); //8
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            // App enters foreground
//            Toast.makeText(activity, "App goes foreground", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations();
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            // App enters background
            stopService(new Intent(this,backgroundMusicService.class));
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

}

