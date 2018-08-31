package com.example.forest.quickguessv2;

import android.app.Application;

import com.example.forest.quickguessv2.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Questions.QuestionRepositories;
import com.example.forest.quickguessv2.Helpers.Detector;

public class ApplicationClass extends Application{

    QuestionCategoryRepositories questionCategoryRepositories;
    QuestionRepositories questionRepositories;
  /*  private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher (Context context)
    {
        ApplicationClass app = (ApplicationClass) context.getApplicationContext();
        return app.refWatcher;
    }*/
    //        refWatcher = LeakCanary.install(this);


    @Override
    public void onCreate() {
        super.onCreate();
        questionCategoryRepositories = new QuestionCategoryRepositories(this);
        questionRepositories = new QuestionRepositories(this);
        insertCategories();
        insertQuestions();
        checkInternetConnection();
    }




    public void checkInternetConnection()
    {
        Detector netDetector = new Detector(this);
        netDetector.checkConnection();
    }

    private void insertCategories()
    {
        //insert categories
        questionCategoryRepositories.categoryCreator("people","All about");
        questionCategoryRepositories.categoryCreator("plants","All about");
        questionCategoryRepositories.categoryCreator("animals","All about");
        questionCategoryRepositories.categoryCreator("geography","All abontrolout");
        questionCategoryRepositories.categoryCreator("sports","All about");
        questionCategoryRepositories.categoryCreator("music","All about");
        questionCategoryRepositories.categoryCreator("technology","All about");
        questionCategoryRepositories.categoryCreator("entertainment","All about");
        questionCategoryRepositories.categoryCreator("entertainment","All about");
        questionCategoryRepositories.categoryCreator("entertainment","All about");
    }

    private void insertQuestions()
    {
        //insert questions
        if (DB.getInstance(this).questionsDao().countQuestion() == 0)
        {
            questionRepositories.questionCreator("What is","testing","testing2","testing3","testing4","testing4","This is a sample","default",1);
            questionRepositories.questionCreator("What is2","halo","halo2","halo3","halo4","halo","This is a sample","default",1);
            questionRepositories.questionCreator("What is3","halo","halo2","halo3","halo4","halo","This is a sample","default",1);
            questionRepositories.questionCreator("What is4","halo","halo2","halo3","halo4","halo","This is a sample","default",1);
            questionRepositories.questionCreator("What is5","halo","halo2","halo3","halo4","halo","This is a sample","default",1);
            questionRepositories.questionCreator("What is6","halo","halo2","halo3","halo4","halo","This is a sample","default",1);
            questionRepositories.questionCreator("What is7","halo","halo2","halo3","halo4","halo","This is a sample","default",1);
            questionRepositories.questionCreator("What is8","halo","halo2","halo3","halo4","halo","This is a sample","default",1);
            questionRepositories.questionCreator("What is9","halo","halo2","halo3","halo4","halo","This is a sample","default",1);
            questionRepositories.questionCreator("What is10","halo","halo2","halo3","halo4","halo","This is a sample","default",1);
        }



    }


}
