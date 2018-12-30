package com.example.forest.quickguess;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.forest.quickguess.APIsInterface.APIRegister;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.DB.User.User;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.Helpers.Connectivity;
import com.example.forest.quickguess.Helpers.InputHelpers;
import com.example.forest.quickguess.Helpers.LayoutHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;
import com.example.forest.quickguess.Service.backgroundMusicService;
import com.example.forest.quickguess.Services.WebService.UserRegisterRequest;
import com.example.forest.quickguess.Services.WebService.UserRegisterResponse;
import com.example.forest.quickguess.Services.WebService.UserRegisterService;
import com.example.forest.quickguess.Utilities.FragmentUtil;
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.example.forest.quickguess.Utilities.SoundUtil;


import java.util.LinkedList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.username) EditText username;
    @BindView(R.id.userPin) EditText userPin;
    @BindView(R.id.welcomeLayout) RelativeLayout welcomeLayout;
    @BindView(R.id.mainLayout)  RelativeLayout mainLayout;


    private static final int MAX_LENGTH = 8;
    private static final int MIN_LENGTH = 4;

    public LifeRepositories lifeRepositories;
    FragmentUtil fragmentUtil;
    public PointsRepositories pointsRepositories;
    private boolean isStop = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disposeAllBackstack();
        startService(new Intent(this,backgroundMusicService.class));
        init();
        checkUser();

    }

     private void init() {
            WindowHelper.hideNavigationBar(this);
            ButterKnife.bind(this);
            lifeRepositories = new LifeRepositories(this);
            fragmentUtil = new FragmentUtil();
            pointsRepositories = new PointsRepositories(getApplicationContext());
//            userPin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
     }





        @Override
        public void onBackPressed() {
            Fragment fragmentManager = getSupportFragmentManager().findFragmentById(R.id.fragment_one);
            if  (fragmentManager != null)
            {
                fragmentUtil.disposeBackStack();
            } else  {
                System.exit(0);
                stopService(new Intent(this,backgroundMusicService.class));
            }
            super.onBackPressed();
        }


    @Override
        protected void onResume() {
            checkUser();
            if  (isStop)
            {
                startService(new Intent(this,backgroundMusicService.class));
            }
            disposeAllBackstack();
            WindowHelper.hideNavigationBar(this);
            super.onResume();
        }


        @OnClick(R.id.createUser)
        public void create() {
            final String playerUsername = username.getText().toString();
            final String playerPin = userPin.getText().toString();

            if  ( playerUsername.isEmpty()  || InputHelpers.isProperUsername(playerUsername) )
            {
                SoundUtil.songLoad(getApplicationContext(),R.raw.error)
                        .start();
                username.setError("Please provide a proper name");

             } else if ( playerPin.length() > MAX_LENGTH  || playerPin.length() < MIN_LENGTH ) {
                SoundUtil.songLoad(getApplicationContext(),R.raw.error)
                        .start();
                userPin.setError("Pin must be minimum of 4 characters");
            }  else {
                signUp(playerUsername,playerPin);
            }

        }

        private void signUp(final String userName , final String userPin)
        {

            final AlertDialog dialog = new SpotsDialog.Builder().setContext(this)
                    .setTheme(R.style.dialogTheme)
                    .build();
            dialog.show();

            Retrofit refrofit = UserRegisterService.RetrofitInstance(getApplicationContext());
            APIRegister services = refrofit.create(APIRegister.class);
            UserRegisterRequest userRegisterRequest = new UserRegisterRequest(userName,userPin);
            Call<UserRegisterResponse> UserRegisterResponseCall = services.register(userRegisterRequest);
            UserRegisterResponseCall.enqueue(new Callback<UserRegisterResponse>() {
                @Override
                public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                    if  (response.code() != 422)
                    {
                        //store to database
                        UserRepositories.createUser(getApplicationContext(),new User(userName));

                        //check if the user already play
                        SharedPreferenceHelper.PREF_FILE = "game_over_time";
                        if (SharedPreferenceHelper.getSharedPreferenceLong(getApplicationContext(), "time", 0) == 0) {
                                if (UserRepositories.getLifeOfUser(lifeRepositories) <= 0 ) // if the user has no remaning life
                                {
                                    UserRepositories.defaultLifetoUser(lifeRepositories);
                                }
                        }
                        //set the user id of the user
                        SharedPreferenceHelper.PREF_FILE="user";
                        SharedPreferenceHelper.setSharedPreferenceInt(getApplicationContext(),"user_id",response.body().getId());
                        checkUser();
                        //set the JWT token for user
                        SharedPreferenceHelper.PREF_FILE = "user_token";
                        SharedPreferenceHelper.setSharedPreferenceString(getApplicationContext(),"token",response.body().getToken());
                    } else {
                        username.setError("Username already exists");
                    }
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                }
            });
        }


    @Override
        protected void onStop() {
        stopService(new Intent(this,backgroundMusicService.class));
        isStop = true;
        super.onStop();
    }

    private void checkUser()
    {
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
    protected void onDestroy() {
        DB.getInstance(getApplicationContext()).destroyInstance();
        stopService(new Intent(this,backgroundMusicService.class));
        super.onDestroy();
    }
}
