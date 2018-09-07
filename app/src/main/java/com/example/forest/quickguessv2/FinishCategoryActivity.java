package com.example.forest.quickguessv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.Helpers.WindowHelper;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinishCategoryActivity extends AppCompatActivity {

    @BindView(R.id.trophy) ImageView trophy;
    @BindView(R.id.message) TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_category);
        WindowHelper.hideNavigationBar(this);
        TypeFaceUtil.initFont(this);
        ButterKnife.bind(this);
        YoYo.with(Techniques.Tada)
                .pivot(200,150)
                .duration(2000)
                .delay(500)
                .repeat(-1)
                .playOn(trophy);
        SharedPreferenceHelper.PREF_FILE="user_played";
        String category = SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"category",null);
        message.setText("Congratulations you finish the "+category.toUpperCase()+" category now you can see all the FUN FACTS");
    }


    @OnClick(R.id.viewAllFunFacts)
    public void onClick()
    {
        Toast.makeText(this, "Display all fun facts!", Toast.LENGTH_SHORT).show();
    }
}
