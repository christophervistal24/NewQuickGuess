package com.example.forest.quickguessv2;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Life.LifeRepositories;
import com.example.forest.quickguessv2.DB.User.User;
import com.example.forest.quickguessv2.DB.User.UserRepositories;
import com.example.forest.quickguessv2.Helpers.InputHelpers;
import com.example.forest.quickguessv2.Helpers.LayoutHelper;
import com.example.forest.quickguessv2.Helpers.WindowHelper;
import com.example.forest.quickguessv2.Utilities.FragmentUtil;
import com.example.forest.quickguessv2.Utilities.TypeFaceUtil;
import com.facebook.FacebookSdk;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.username) EditText username;
    @BindView(R.id.welcomeLayout) RelativeLayout welcomeLayout;
    @BindView(R.id.mainLayout)  RelativeLayout mainLayout;

    public LifeRepositories lifeRepositories;
    FragmentUtil fragmentUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        ApplicationClass.getRefWatcher(this);
        TypeFaceUtil.initFont(this);
        WindowHelper.hideNavigationBar(this);
        ButterKnife.bind(this);
        lifeRepositories = new LifeRepositories(this);
        fragmentUtil = new FragmentUtil();
        checkUser();
//        printKeyHash();
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
        if (fragmentUtil.disposeBackStack())
        {
            System.exit(0);
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        WindowHelper.hideNavigationBar(this);
        checkUser();
        super.onResume();
    }


    @OnClick(R.id.createUser)
    public void create() {
        String player = username.getText().toString();
        if  (player.isEmpty()  || InputHelpers.isProperUsername(player))
        {
            username.setError("Please provide a proper username");
        } else {
            UserRepositories.createUser(this,new User(player));
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


    @Override
    protected void onDestroy() {
        DB.getInstance(this).destroyInstance();
        super.onDestroy();
    }

}
