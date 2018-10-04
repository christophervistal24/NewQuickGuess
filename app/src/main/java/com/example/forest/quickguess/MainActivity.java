package com.example.forest.quickguess;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguess.APIsInterface.APIRegister;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.DB.User.User;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.Helpers.Connectivity;
import com.example.forest.quickguess.Helpers.InputHelpers;
import com.example.forest.quickguess.Helpers.LayoutHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;
import com.example.forest.quickguess.Service.MyService;
import com.example.forest.quickguess.Services.WebService.UserRegisterRequest;
import com.example.forest.quickguess.Services.WebService.UserRegisterResponse;
import com.example.forest.quickguess.Services.WebService.UserRegisterService;
import com.example.forest.quickguess.Utilities.FragmentUtil;
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.example.forest.quickguess.Utilities.SoundUtil;
import com.example.forest.quickguess.Utilities.TypeFaceUtil;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.username) EditText username;
    @BindView(R.id.emailaddress) EditText emailaddress;
    @BindView(R.id.welcomeLayout) RelativeLayout welcomeLayout;
    @BindView(R.id.mainLayout)  RelativeLayout mainLayout;
    @BindView(R.id.title) ImageView title;

    public LifeRepositories lifeRepositories;
    FragmentUtil fragmentUtil;
    public PointsRepositories pointsRepositories;
    private boolean isStop = false;
    YoYo.YoYoString yoYoString = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypeFaceUtil.initDimboFont(this);
        setContentView(R.layout.activity_main);
        disposeAllBackstack();
        startService(new Intent(this,MyService.class));
        init();
        checkUser();
        getUsername();
     //        printKeyHash();
    }

          private void init() {
            // Normal app init code...
            TypeFaceUtil.initDimboFont(this);
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



        public void getUsername() {
            AccountManager manager = AccountManager.get(this);
            Account[] accounts = manager.getAccountsByType("com.google");
            List<String> possibleEmails = new LinkedList<String>();

            for (Account account : accounts) {
                // TODO: Check possibleEmail against an email regex or treat
                // account.name as an email address only for certain account.type values.
                possibleEmails.add(account.name);
            }

            if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
                String email = possibleEmails.get(0);
                String[] parts = email.split("@");

                if (parts.length > 1) {
             /*   username.setText(parts[0]);
                emailaddress.setText(email);*/
                }
            }
        }



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
            checkUser();
            if  (isStop)
            {
                startService(new Intent(this,MyService.class));
            }
            disposeAllBackstack();
            WindowHelper.hideNavigationBar(this);
            super.onResume();
        }


        @OnClick(R.id.createUser)
        public void create() {
            final String player = username.getText().toString();

            if  ( (player.isEmpty()  || InputHelpers.isProperUsername(player) ))
            {
                yoYoString = YoYo.with(Techniques.Shake)
                        .duration(700)
                        .playOn(username);
                SoundUtil.songLoad(getApplicationContext(),R.raw.error)
                        .start();
                username.setError("Please provide a proper name");
            } else {
                signUp(player);
            }

        }

        private void signUp(final String player)
        {
            // Set up progress before call
            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(this);
            Typeface.createFromAsset(getAssets(),"fonts/Dimbo_Regular.ttf");
            progressDialog.setMessage("Please wait . . .");
            TypeFaceUtil.initDimboFont(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // show it
            progressDialog.show();
            Retrofit refrofit = UserRegisterService.RetrofitInstance(getApplicationContext());
            APIRegister services = refrofit.create(APIRegister.class);
            UserRegisterRequest userRegisterRequest = new UserRegisterRequest(player);
            Call<UserRegisterResponse> UserRegisterResponseCall = services.register(userRegisterRequest);
            UserRegisterResponseCall.enqueue(new Callback<UserRegisterResponse>() {
                @Override
                public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                    if  (response.isSuccessful() && response.body().getMessage().contains("Success"))
                    {
                        SoundUtil.songLoad(getApplicationContext(),R.raw.click)
                                .start();
                        UserRepositories.createUser(getApplicationContext(),new User(player));
                        UserRepositories.defaultLifetoUser(lifeRepositories);
                        checkUser();
                    } else {
                        username.setError(response.body().getMessage());
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }


    @Override
        protected void onStop() {
        stopService(new Intent(this,MyService.class));
        isStop = true;
        super.onStop();
    }

    private void checkUser()
    {
        /*if (UserRepositories.isUserAlreadyRegister(this)) {
            LayoutHelper.hideLayout(welcomeLayout);
            fragmentUtil.startMenuFragment(this);
        } else {
            LayoutHelper.showLayout(welcomeLayout);
        }*/
        if (Connectivity.isConnectedWifi(getApplicationContext()) && !UserRepositories.isUserAlreadyRegister(this)) {
            LayoutHelper.showLayout(welcomeLayout);
        } else {
            LayoutHelper.hideLayout(welcomeLayout);
            fragmentUtil.startMenuFragment(this);
        }
    }

    private void disposeAllBackstack()
    {
        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }


    @Override
    protected void onPause() {

        super.onPause();
    }


    @Override
    protected void onDestroy() {
        yoYoString = null;
        DB.getInstance(getApplicationContext()).destroyInstance();
        stopService(new Intent(this,MyService.class));
        super.onDestroy();
    }
}
