package com.example.forest.quickguessv2;

import android.app.Application;

import com.example.forest.quickguessv2.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguessv2.DB.Questions.QuestionRepositories;
import com.example.forest.quickguessv2.Helpers.Detector;

public class ApplicationClass extends Application{
    QuestionCategoryRepositories questionCategoryRepositories;
    QuestionRepositories questionRepositories;

    @Override
    public void onCreate() {
        questionCategoryRepositories = new QuestionCategoryRepositories(this);
        questionRepositories = new QuestionRepositories(this);
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
        questionCategoryRepositories.addCategory("geography","All about");
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
        questionRepositories.questionCreator("What is","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
        questionRepositories.questionCreator("What is2","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
        questionRepositories.questionCreator("What is3","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
        questionRepositories.questionCreator("What is4","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
        questionRepositories.questionCreator("What is5","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
        questionRepositories.questionCreator("What is6","sample","sample2","sample3","sample4","sample","This is a sample","default",1);

    }


}
