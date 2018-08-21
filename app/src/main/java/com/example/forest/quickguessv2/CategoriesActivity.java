package com.example.forest.quickguessv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoriesActivity extends AppCompatActivity {

    @BindView(R.id.categoriesLayout)LinearLayout categoriesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.people,R.id.plants,R.id.animals,R.id.geography,R.id.sports,R.id.music,R.id.technology,R.id.entertainment})
    public void onClick(View view) {
        Intent intent;
       switch (view.getId())
        {
            case R.id.people:
                gotoActivity("bg_people",this,AnswerQuestion.class);
                break;

            case R.id.plants:
                gotoActivity("bg_plants",this,AnswerQuestion.class);
                break;

            case R.id.animals:
                gotoActivity("bg_animals",this,AnswerQuestion.class);
                break;

            case R.id.geography:
                gotoActivity("bg_geography",this,AnswerQuestion.class);
                break;

            case R.id.sports:
                gotoActivity("bg_sports",this,AnswerQuestion.class);
                break;

            case R.id.music:
                gotoActivity("bg_music",this,AnswerQuestion.class);
                break;

            case R.id.technology:
                gotoActivity("bg_technology",this,AnswerQuestion.class);
                break;

            case R.id.entertainment:
                gotoActivity("bg_entertainment",this,AnswerQuestion.class);
                break;


        }
    }

    private void gotoActivity(String category_name, CategoriesActivity categoriesActivity, Class<AnswerQuestion> categoriesActivityClass) {
        Intent intent = new Intent(categoriesActivity,categoriesActivityClass);
        intent.putExtra("category_name",category_name);
        startActivity(intent);
    }

}
