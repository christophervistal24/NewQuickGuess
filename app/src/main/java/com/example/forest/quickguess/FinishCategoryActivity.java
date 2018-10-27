package com.example.forest.quickguess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.Helpers.RedirectHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinishCategoryActivity extends AppCompatActivity {

    @BindView(R.id.trophy) ImageView trophy;
    @BindView(R.id.message) TextView message;
    @BindView(R.id.trophyLayout) LinearLayout trophyL;
    String category;
    int category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_category);
        WindowHelper.hideNavigationBar(this);

        ButterKnife.bind(this);
        displayMessage();
    }



    private void displayMessage() {

        try {
            SharedPreferenceHelper.PREF_FILE="user_played";
            category = SharedPreferenceHelper
                    .getSharedPreferenceString(getApplicationContext(),"category",null)
                    .toLowerCase();
            category_id = DB.getInstance(getApplicationContext()).categoriesQuestionDao().getCategoryIdByName(category);
            message.setText(String.format("Congratulations you finish the %s category now you can see all fun facts", category.toUpperCase()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        DB.getInstance(getApplicationContext()).destroyInstance();

        super.onDestroy();
    }

    @OnClick(R.id.viewAllFunFacts)
    public void onClick()
    {
        new RedirectHelper(getApplicationContext(),DisplayAllFunFacts.class);
    }

    @OnClick(R.id.resetThisLevel)
    public void resetCategory()
    {
        //delete all
        DB.getInstance(getApplicationContext()).userStatusDao().deleteByClassIdAndCategoryId(QuestionRepositories.class_id,category_id);
    }
}
