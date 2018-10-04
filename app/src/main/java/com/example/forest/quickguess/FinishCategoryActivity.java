package com.example.forest.quickguess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.Helpers.RedirectHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;
import com.example.forest.quickguess.Utilities.TypeFaceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinishCategoryActivity extends AppCompatActivity {

    @BindView(R.id.trophy) ImageView trophy;
    @BindView(R.id.message) TextView message;
    @BindView(R.id.trophyLayout) LinearLayout trophyL;
    String category;
    YoYo.YoYoString yoYoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_category);
        WindowHelper.hideNavigationBar(this);
        TypeFaceUtil.initDimboFont(this);
        ButterKnife.bind(this);
        animteTrophy();
        displayMessage();
    }

    private void animteTrophy() {
        yoYoString = YoYo.with(Techniques.Tada)
                .pivot(200, 150)
                .duration(2000)
                .delay(500)
                .repeat(-1)
                .playOn(trophy);
    }

    private void displayMessage() {

        try {
            SharedPreferenceHelper.PREF_FILE="user_played";
            category = SharedPreferenceHelper
                    .getSharedPreferenceString(getApplicationContext(),"category",null)
                    .toLowerCase();
            message.setText(String.format("Congratulations you finish the %s category now you can see all fun facts", category.toUpperCase()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        DB.getInstance(getApplicationContext()).destroyInstance();
        if (yoYoString != null) {
            yoYoString.stop();
            yoYoString = null;
        }
        super.onDestroy();
    }

    @OnClick(R.id.viewAllFunFacts)
    public void onClick()
    {
        new RedirectHelper(getApplicationContext(),DisplayAllFunFacts.class);
    }
}