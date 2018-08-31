package com.example.forest.quickguessv2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.forest.quickguessv2.Helpers.WindowHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.contactUs) Button contactus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowHelper.hideNavigationBar(this);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.contactUs)
    public void contactUs()
    {
        try{
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "testing@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            //TODO
        }
    }
}
