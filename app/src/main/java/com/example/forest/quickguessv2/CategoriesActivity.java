package com.example.forest.quickguessv2;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguessv2.Adapters.SlideAdapter;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.Helpers.RedirectHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoriesActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SlideAdapter myadapter;
    ImageView imgslide;

    @BindView(R.id.categoriesLayout) LinearLayout categoriesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        myadapter = new SlideAdapter(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                        if  (i != -1 && i != 8)
                        {
                            if (v == 0.0 && i1 == 0)
                            {
                                imgslide = myadapter.getImg();
                                YoYo.with(Techniques.Bounce)
                                        .duration(700)
                                        .playOn(imgslide);
                            }
                        }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        viewPager.setAdapter(myadapter);
    }

   /* @OnClick({R.id.people, R.id.plants, R.id.animals, R.id.geography, R.id.sports, R.id.music, R.id.technology, R.id.entertainment})
    public void onClick(View view) {
        String background;
        switch (view.getId()) {
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
        new RedirectHelper(background, this, AnswerQuestion.class);
    }*/


    @Override
    protected void onDestroy() {
        DB.getInstance(this).destroyInstance();
        super.onDestroy();
    }

}
