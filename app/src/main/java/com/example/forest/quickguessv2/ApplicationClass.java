package com.example.forest.quickguessv2;

import android.app.Application;
import android.content.Context;

import com.example.forest.quickguessv2.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Questions.QuestionRepositories;
import com.example.forest.quickguessv2.Helpers.Detector;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class ApplicationClass extends Application{

    QuestionCategoryRepositories questionCategoryRepositories;
    QuestionRepositories questionRepositories;
    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher (Context context)
    {
        ApplicationClass app = (ApplicationClass) context.getApplicationContext();
        return app.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        questionCategoryRepositories = new QuestionCategoryRepositories(this);
        questionRepositories = new QuestionRepositories(this);
        refWatcher = LeakCanary.install(this);
        insertCategories();
        insertQuestions();
        checkInternetConnection();
        super.onCreate();
    }




    public void checkInternetConnection()
    {
        Detector netDetector = new Detector(this);
        netDetector.checkConnection();
    }

    private void insertCategories()
    {
        //insert categories
        questionCategoryRepositories.addCategory("people","All about");
        questionCategoryRepositories.addCategory("plants","All about");
        questionCategoryRepositories.addCategory("animals","All about");
        questionCategoryRepositories.addCategory("geography","All abontrolout");
        questionCategoryRepositories.addCategory("sports","All about");
        questionCategoryRepositories.addCategory("music","All about");
        questionCategoryRepositories.addCategory("technology","All about");
        questionCategoryRepositories.addCategory("entertainment","All about");
        questionCategoryRepositories.addCategory("entertainment","All about");
        questionCategoryRepositories.addCategory("entertainment","All about");
    }

    private void insertQuestions()
    {
        //insert questions
        if (DB.getInstance(this).questionsDao().countQuestion() == 0)
        {
            questionRepositories.questionCreator("What is","testing","testing2","testing3","testing4","testing4","This is a sample","default",1);
            questionRepositories.questionCreator("What is2","halo","halo2","halo3","halo4","halo","This is a sample","default",1);
        }



    }


}
