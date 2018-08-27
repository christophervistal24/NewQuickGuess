package com.example.forest.quickguessv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Questions.QuestionRepositories;
import com.example.forest.quickguessv2.Helpers.RedirectHelper;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoriesActivity extends AppCompatActivity{

    @BindView(R.id.categoriesLayout)LinearLayout categoriesLayout;
    private QuestionRepositories questionRepositories;
    ApplicationClass applicationClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        questionRepositories = new QuestionRepositories(this);
        insertQuestions();
    }




    @OnClick({R.id.people,R.id.plants,R.id.animals,R.id.geography,R.id.sports,R.id.music,R.id.technology,R.id.entertainment})
    public void onClick(View view) {
      String background;
      switch (view.getId())
        {
            case R.id.people:
                background = "bg_people";
                break;

            case R.id.plants:
                background = "bg_plants";
                break;

            case R.id.animals:
                background = "bg_animals";
                break;

            case R.id.geography:
                background = "bg_geography";
                break;

            case R.id.sports:
                background = "bg_sports";
                break;

            case R.id.music:
                background = "bg_music";
                break;

            case R.id.technology:
                background = "bg_technology";
                break;

            case R.id.entertainment:
                background = "bg_entertainment";
                break;

            default:
                throw new RuntimeException("Unknow button ID");
        }
        new RedirectHelper(background,this,AnswerQuestion.class);
    }




    private void insertQuestions()
    {
         questionRepositories.questionCreator("What is","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
         questionRepositories.questionCreator("What is2","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
         questionRepositories.questionCreator("What is3","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
         questionRepositories.questionCreator("What is4","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
         questionRepositories.questionCreator("What is5","sample","sample2","sample3","sample4","sample","This is a sample","default",1);
         questionRepositories.questionCreator("What is6","sample","sample2","sample3","sample4","sample","This is a sample","default",1);

    }

    @Override
    protected void onDestroy() {
        DB.getInstance(this).destroyInstance();
        super.onDestroy();
    }

}
