package com.example.forest.quickguessv2;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Questions.Questions;
import com.example.forest.quickguessv2.Helpers.RedirectHelper;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.Helpers.WindowHelper;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinishCategoryActivity extends AppCompatActivity {

    @BindView(R.id.trophy) ImageView trophy;
    @BindView(R.id.message) TextView message;
    @BindView(R.id.trophyLayout) LinearLayout trophyL;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_category);
        WindowHelper.hideNavigationBar(this);
        TypeFaceUtil.initFont(this);
        ButterKnife.bind(this);
        animteTrophy();
        displayMessage();
    }

    private void animteTrophy() {
        YoYo.with(Techniques.Tada)
                .pivot(200,150)
                .duration(2000)
                .delay(500)
                .repeat(-1)
                .playOn(trophy);
    }

    private void displayMessage() {
        SharedPreferenceHelper.PREF_FILE="user_played";
        category = SharedPreferenceHelper
                        .getSharedPreferenceString(getApplicationContext(),"category",null)
                        .toLowerCase();
        message.setText(String.format("Congratulations you finish the %s category now you can see all the FUN FACTS", category.toUpperCase()));
    }


    @Override
    protected void onDestroy() {
        DB.getInstance(getApplicationContext()).destroyInstance();
        super.onDestroy();
    }

    @OnClick(R.id.viewAllFunFacts)
    public void onClick()
    {
        //get the id of category

        new RedirectHelper(getApplicationContext(),DisplayAllFunFacts.class);
    }
}
