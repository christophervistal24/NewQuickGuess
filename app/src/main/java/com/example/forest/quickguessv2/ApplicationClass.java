package com.example.forest.quickguessv2;

import android.app.Application;
import android.app.ProgressDialog;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.forest.quickguessv2.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Points.Points;
import com.example.forest.quickguessv2.DB.Points.PointsRepositories;
import com.example.forest.quickguessv2.DB.Questions.QuestionRepositories;
import com.example.forest.quickguessv2.DB.User.UserRepositories;
import com.example.forest.quickguessv2.Helpers.Connectivity;

public class ApplicationClass extends Application{

    QuestionCategoryRepositories questionCategoryRepositories;
    QuestionRepositories questionRepositories;
    PointsRepositories pointsRepositories;
    Points points;
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
        insertUserPoints();

    }


    private void insertUserPoints()
    {
        if (Connectivity.isConnectedWifi(getApplicationContext()))
        {
            pointsRepositories = new PointsRepositories(getApplicationContext());
            points = new Points();
            pointsRepositories.sendPoints(points);
        }
    }


    private void insertCategories()
    {
        questionCategoryRepositories.categoryCreator("people","All about");
        questionCategoryRepositories.categoryCreator("plants","All about");
        questionCategoryRepositories.categoryCreator("animals","All about");
        questionCategoryRepositories.categoryCreator("geography","All abontrolout");
        questionCategoryRepositories.categoryCreator("sports","All about");
        questionCategoryRepositories.categoryCreator("music","All about");
        questionCategoryRepositories.categoryCreator("technology","All about");
        questionCategoryRepositories.categoryCreator("entertainment","All about");
    }

    private void insertQuestions()
    {
        //insert questions
        if (DB.getInstance(this).questionsDao().countQuestion() == 0)
        {
            questionRepositories.questionCreator("What is people0","halo","halo2","halo3","halo4","halo","This is a sample0","default",1);
            questionRepositories.questionCreator("What is people1","halo","halo2","halo3","halo4","halo","This is a sample1","default",1);
            questionRepositories.questionCreator("What is plants2","halo","halo2","halo3","halo4","halo","This is a sample2","default",2);
            questionRepositories.questionCreator("What is plants3","halo","halo2","halo3","halo4","halo","This is a sample3","default",2);
            questionRepositories.questionCreator("What is animals4","halo","halo2","halo3","halo4","halo","This is a sample4","default",3);
            questionRepositories.questionCreator("What is animals5","halo","halo2","halo3","halo4","halo","This is a sample5","default",3);
            questionRepositories.questionCreator("What is geography6","halo","halo2","halo3","halo4","halo","This is a sample","default",4);
            questionRepositories.questionCreator("What is geography7","halo","halo2","halo3","halo4","halo","This is a sample","default",4);
            questionRepositories.questionCreator("What is sports8","halo","halo2","halo3","halo4","halo","This is a sample","default",5);
            questionRepositories.questionCreator("What is sports9","halo","halo2","halo3","halo4","halo","This is a sample","default",5);
            questionRepositories.questionCreator("What is music10","halo","halo2","halo3","halo4","halo","This is a sample","default",6);
            questionRepositories.questionCreator("What is music11","halo","halo2","halo3","halo4","halo","This is a sample","default",6);
            questionRepositories.questionCreator("What is technology12","halo","halo2","halo3","halo4","halo","This is a sample2","default",7);
            questionRepositories.questionCreator("What is technology13","halo","halo2","halo3","halo4","halo","This is a sample2","default",7);
            questionRepositories.questionCreator("What is entertainment14","halo","halo2","halo3","halo4","halo","This is a sample2","default",8);
            questionRepositories.questionCreator("What is entertainment15","halo","halo2","halo3","halo4","halo","This is a sample2","default",8);
        }



    }


}
