package com.example.forest.quickguessv2;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Life.LifeRepositories;
import com.example.forest.quickguessv2.DB.Points.Points;
import com.example.forest.quickguessv2.DB.Points.PointsRepositories;
import com.example.forest.quickguessv2.DB.User.User;
import com.example.forest.quickguessv2.DB.User.UserRepositories;
import com.example.forest.quickguessv2.Helpers.InputHelpers;
import com.example.forest.quickguessv2.Helpers.LayoutHelper;
import com.example.forest.quickguessv2.Helpers.WindowHelper;
import com.example.forest.quickguessv2.Utilities.FragmentUtil;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.username) EditText username;
    @BindView(R.id.welcomeLayout) RelativeLayout welcomeLayout;
    @BindView(R.id.mainLayout)  RelativeLayout mainLayout;

    public LifeRepositories lifeRepositories;
    FragmentUtil fragmentUtil;
    public PointsRepositories pointsRepositories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disposeAllBackstack();
        MediaPlayer bgSong = MediaPlayer.create(MainActivity.this, R.raw.bgmusic);
        bgSong.start();
        init();
        checkUser();
     //        printKeyHash();
    }

        private void init() {
//        ApplicationClass.getRefWatcher(this);
        TypeFaceUtil.initFont(this);
        WindowHelper.hideNavigationBar(this);
        ButterKnife.bind(this);
        lifeRepositories = new LifeRepositories(this);
        fragmentUtil = new FragmentUtil();
        pointsRepositories = new PointsRepositories(getApplicationContext());
    }

    /*private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.forest.quickguessv2",
                    PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
*/


    @Override
    public void onBackPressed() {
        Fragment fragmentManager = getSupportFragmentManager().findFragmentById(R.id.fragment_one);
        if  (fragmentManager != null)
        {
            fragmentUtil.disposeBackStack();
        } else  {
            System.exit(0);
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        disposeAllBackstack();
        WindowHelper.hideNavigationBar(this);
        checkUser();
        super.onResume();
    }


    @OnClick(R.id.createUser)
    public void create() {
        String player = username.getText().toString();
        if  (player.isEmpty()  || InputHelpers.isProperUsername(player) )
        {
            username.setError("Please provide a proper username");
        } else {
            UserRepositories.createUser(getApplicationContext(),new User(player));
            UserRepositories.defaultLifetoUser(lifeRepositories);
            username.setText(null);
        }
        checkUser();
    }

    private void checkUser()
    {
        if (UserRepositories.isUserAlreadyRegister(this)) {
            LayoutHelper.hideLayout(welcomeLayout);
            fragmentUtil.startMenuFragment(this);
        } else {
            LayoutHelper.showLayout(welcomeLayout);
        }
    }

    private void disposeAllBackstack()
    {
        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    protected void onDestroy() {
        DB.getInstance(getApplicationContext()).destroyInstance();

        super.onDestroy();
    }

}
