package com.example.forest.quickguess;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.forest.quickguess.Helpers.WindowHelper;
import com.example.forest.quickguess.Utilities.TypeFaceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.contactUs) Button contactus;
    @BindView(R.id.gwyn) ImageView Gwyn;
    @BindView(R.id.scrollView) ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowHelper.hideNavigationBar(this);
        setContentView(R.layout.activity_about);
        TypeFaceUtil.initDimboFont(this);
        ButterKnife.bind(this);
        initAnimation();
        scroll.setSmoothScrollingEnabled(true);
    }

    private void initAnimation() {

    }

    @OnClick(R.id.contactUs)
    public void contactUs()
    {
        try{
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "christophervistal24@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            intent.putExtra(Intent.EXTRA_TEXT, "Your message here");
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            //TODO
        }
    }
}
