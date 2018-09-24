package com.example.forest.quickguessv2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendsActivity extends AppCompatActivity implements  View.OnClickListener{

    @BindView(R.id.rootLayout) LinearLayout linearLayout;
    @BindView(R.id.keysLayout) LinearLayout keysLayout;
    int categoryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ButterKnife.bind(this);
        int remainingFriendsInCategory = getCategory();
        String[] chooseFriends = collectAllFriendNames();
        createButtons(remainingFriendsInCategory,chooseFriends);
        String player_name = DB.getInstance(getApplicationContext()).userDao().getUsername();
        Toast.makeText(this,player_name + " Please set one of us free!", Toast.LENGTH_SHORT).show();
    }

    private String[] collectAllFriendNames() {
        return DB.getInstance(getApplicationContext()).friendsDao().getFriendNamesByCategoryId(categoryId);
    }

    private int getCategory() {
        SharedPreferenceHelper.PREF_FILE="user_played";
        String category_name = SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"category",null);
        categoryId = DB.getInstance(getApplicationContext()).categoriesQuestionDao().getCategoryIdByName(category_name.toLowerCase());
        return DB.getInstance(getApplicationContext()).friendsDao().countSaveFriendsByCategoryId(categoryId);
    }

    private void createButtons(int remainingFriendsInCategory,String[] friend_names) {
        for(int i = 0; i<remainingFriendsInCategory; i++)
        {
            //buttons
            Button saveFriendsBtn = new Button(this);
            ImageView imageViews = new ImageView(getApplicationContext());
            saveFriendsBtn.setTag(friend_names[i]);
            saveFriendsBtn.setText(friend_names[i]);
            saveFriendsBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            saveFriendsBtn.setBackground(Drawable.createFromPath("@null"));
            saveFriendsBtn.setTextSize(35);
            saveFriendsBtn.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
            saveFriendsBtn.setTextColor(Color.parseColor("#ffffff"));
            saveFriendsBtn.setOnClickListener(this);
            //images
            imageViews.setImageResource(R.drawable.key);
            imageViews.setTag(friend_names[i]);
            imageViews.setLayoutParams(new LinearLayout.LayoutParams(100,100));
            imageViews.setOnClickListener(this);
            if (linearLayout != null) {
                linearLayout.addView(saveFriendsBtn);
                keysLayout.addView(imageViews);
            }
        }
    }

    @Override
    public void onClick(View view) {
        String friend_name = (String) view.getTag();
        DB.getInstance(getApplicationContext()).friendsDao().update(friend_name,1);
        finish();
    }

    @Override
    protected void onDestroy() {
        DB.getInstance(getApplicationContext()).destroyInstance();
        super.onDestroy();
    }
}
