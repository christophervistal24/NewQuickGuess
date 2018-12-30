package com.example.forest.quickguess;

import android.database.sqlite.SQLiteConstraintException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Points.Points;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.Helpers.RedirectHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinishCategoryActivity extends AppCompatActivity {


    @BindView(R.id.message) TextView message;
    @BindView(R.id.trophyLayout) LinearLayout trophyL;
    String category;
    int category_id;
    int class_id;
    Points points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_category);
        WindowHelper.hideNavigationBar(this);
        ButterKnife.bind(this);
        points = new Points();
        displayMessage();
    }



    private void displayMessage() {

        try {
            SharedPreferenceHelper.PREF_FILE="user_played";
            category = SharedPreferenceHelper
                    .getSharedPreferenceString(getApplicationContext(),"category",null)
                    .toLowerCase();
            category_id = DB.getInstance(getApplicationContext()).categoriesQuestionDao().getCategoryIdByName(category);
            class_id = QuestionRepositories.class_id;
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

        int questionThatAnsweredCorrect = DB.getInstance(getApplicationContext()).userStatusDao().countAllCorrectByClassIdAndCategoryId(category_id,class_id);
        //from points table
        int currentUserPoints = DB.getInstance(getApplicationContext()).pointsDao().getUserPoints();
        //check if the points column field has value
        points.setId(1);
        if (currentUserPoints != 0 ) {
            points.setPoints(currentUserPoints + (questionThatAnsweredCorrect * 100));
            DB.getInstance(getApplicationContext()).pointsDao().update(points);
        } else {
            points.setPoints(questionThatAnsweredCorrect * 100);
            DB.getInstance(getApplicationContext()).pointsDao().insert(points);
        }
        DB.getInstance(getApplicationContext()).userStatusDao().deleteByClassIdAndCategoryId(QuestionRepositories.class_id,category_id);
        finish();
    }
}
