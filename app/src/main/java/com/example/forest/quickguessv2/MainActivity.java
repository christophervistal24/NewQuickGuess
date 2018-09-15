package com.example.forest.quickguessv2;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Life.LifeRepositories;
import com.example.forest.quickguessv2.DB.Points.Points;
import com.example.forest.quickguessv2.DB.Points.PointsRepositories;
import com.example.forest.quickguessv2.DB.User.User;
import com.example.forest.quickguessv2.DB.User.UserRepositories;
import com.example.forest.quickguessv2.Helpers.InputHelpers;
import com.example.forest.quickguessv2.Helpers.LayoutHelper;
import com.example.forest.quickguessv2.Helpers.WindowHelper;
import com.example.forest.quickguessv2.Service.MyService;
import com.example.forest.quickguessv2.Utilities.FragmentUtil;
import com.example.forest.quickguessv2.Utilities.SoundUtil;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.username) EditText username;
    @BindView(R.id.welcomeLayout) RelativeLayout welcomeLayout;
    @BindView(R.id.mainLayout)  RelativeLayout mainLayout;
    @BindView(R.id.title) ImageView title;

    public LifeRepositories lifeRepositories;
    FragmentUtil fragmentUtil;
    public PointsRepositories pointsRepositories;
    private boolean isStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disposeAllBackstack();
        startService(new Intent(this,MyService.class));
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
        YoYo.with(Techniques.DropOut)
                    .duration(2000)
                    .delay(1000)
                    .repeat(-1)
                    .playOn(title);
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
            stopService(new Intent(this,MyService.class));
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        if  (isStop)
        {
            startService(new Intent(this,MyService.class));
        }
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
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(username);
            SoundUtil.songLoad(getApplicationContext(),R.raw.error)
                     .start();
            username.setError("Please provide a proper name");
        } else {
            SoundUtil.songLoad(getApplicationContext(),R.raw.click)
                      .start();
            UserRepositories.createUser(getApplicationContext(),new User(player));
            UserRepositories.defaultLifetoUser(lifeRepositories);
            username.setText(null);
        }
        checkUser();
    }

    @Override
    protected void onStop() {
        stopService(new Intent(this,MyService.class));
        isStop = true;
        super.onStop();
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
        stopService(new Intent(this,MyService.class));
        super.onDestroy();
    }

}
