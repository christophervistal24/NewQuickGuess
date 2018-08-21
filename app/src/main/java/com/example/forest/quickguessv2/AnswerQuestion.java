package com.example.forest.quickguessv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnswerQuestion extends AppCompatActivity {

    @BindView(R.id.background) ImageView background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        ButterKnife.bind(this);
        Intent i = getIntent();
        String category = i.getStringExtra("category_name");
        background.setImageResource(getResources().getIdentifier(category,"drawable",getPackageName()));
    }




}
