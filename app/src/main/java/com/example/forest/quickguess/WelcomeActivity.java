package com.example.forest.quickguess;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;

import android.widget.TextView;

import com.example.forest.quickguess.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.Helpers.FirstLaunchHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Properties;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WelcomeActivity extends AppCompatActivity {

    private FirstLaunchHelper firstLaunchHelper;
    QuestionCategoryRepositories questionCategoryRepositories;
    QuestionRepositories questionRepositories;
    @BindView(R.id.title) TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowHelper.hideNavigationBar(this);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        questionCategoryRepositories = new QuestionCategoryRepositories(this);
        questionRepositories = new QuestionRepositories(getApplicationContext());
        firstLaunchHelper = new FirstLaunchHelper(getApplicationContext());

        if(!firstLaunchHelper.isFirstTimeLaunch()) // check if splash screen passed
        {
            launchHomeScreen();
            finish();
        }

        // making notification bar transparent
        changeStatusBarColor();

        if (DB.getInstance(this).questionsDao().countQuestion() == 0) // checking if all question inserted
        {
            new insertQuestionAsync(this).execute();
        }

    }




    private void launchHomeScreen() //redirect main activity
    {
        firstLaunchHelper.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        finish();
    }


        /**
         * Making notification bar transparent
         */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private  class insertQuestionAsync extends AsyncTask<Void , Integer , Void> { //asyc task insert all questions
        private WeakReference<WelcomeActivity> activityReference;
        ProgressDialog progressDialog;
        // only retain a weak reference to the activity
        insertQuestionAsync(WelcomeActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(WelcomeActivity.this, "Setting up all for you", "Please wait . . . ", true, false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            questionRepositories.insertAllQuestions();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) { // redirect if finish
            firstLaunchHelper.setFirstTimeLaunch(false);
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            finish();
        }

    }
}

