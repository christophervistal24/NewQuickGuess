package com.example.forest.quickguessv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.forest.quickguessv2.Helpers.WindowHelper;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowHelper.hideNavigationBar(this);
        setContentView(R.layout.activity_about);
    }
}
