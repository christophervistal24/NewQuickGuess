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
       switch (view.getId())
        {
            case R.id.people:
                break;

            case R.id.plants:
                //about
                break;

            case R.id.animals:
                //about
                break;

            case R.id.geography:
                //quit
                break;

            case R.id.sports:
                //quit
                break;

            case R.id.music:
                //quit
                break;

            case R.id.technology:
                //quit
                break;

            case R.id.entertainment:
                //quit
                break;


        }
    }
}
