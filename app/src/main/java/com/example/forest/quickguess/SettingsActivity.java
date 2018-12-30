package com.example.forest.quickguess;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.forest.quickguess.APIsInterface.APISendStatus;
import com.example.forest.quickguess.APIsInterface.APIUserHistory;
import com.example.forest.quickguess.APIsInterface.ApiLogin;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Life.Life;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.User.User;
import com.example.forest.quickguess.DB.UserStatus.UserStatus;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;
import com.example.forest.quickguess.Models.Status;
import com.example.forest.quickguess.Services.WebService.UserHistoryRequest;
import com.example.forest.quickguess.Services.WebService.UserHistoryResponse;
import com.example.forest.quickguess.Services.WebService.UserHistoryService;
import com.example.forest.quickguess.Services.WebService.UserLoginRequest;
import com.example.forest.quickguess.Services.WebService.UserLoginResponse;
import com.example.forest.quickguess.Services.WebService.UserLoginService;
import com.example.forest.quickguess.Services.WebService.UserStatusRequest;
import com.example.forest.quickguess.Services.WebService.UserStatusResponse;
import com.example.forest.quickguess.Services.WebService.UserStatusService;
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.facebook.share.Share;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SettingsActivity extends AppCompatActivity implements SwitchDialog.SwitchDialogListener {

    private static final String FILENAME = "game_over_time";
    @BindView(R.id.switchAccount) Button switchAccount;
    private String userUsername;
    private String userPassword;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        WindowHelper.hideNavigationBar(this);
        checkIfLoggedIn();
        dialog = new SpotsDialog.Builder().setContext(this)
                .setTheme(R.style.dialogTheme)
                .build();
    }

    private void checkIfLoggedIn()
    {
            switchAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String playerUsername = DB.getInstance(getApplicationContext()).userDao().getUsername();
                    if  (playerUsername != null) {
                            openSwitchAccountDialog();
                    } else {
                        // you're playing Quick Guess as guest do you want to save all your activities?
                        //yes - check internet connection
                        // if connection is active redirect to form
                        // else return and display message that connection is weak
                    }
                }
            });

    }

    private void openSwitchAccountDialog() {
        SwitchDialog switchDialog = new SwitchDialog();
        switchDialog.show(getSupportFragmentManager(),"Switch dialog");
    }

    @Override
    public void applyText(String username, String password) {
        //API Request for login
        userUsername = username;
        userPassword = password;
        SharedPreferenceHelper.PREF_FILE = "user_token";
        if (SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"token",null) != null) {
            sendUserStatus(); //send the user status
        }


    }

    private void loginUser(String username , String password)
    {

        Retrofit refrofit = UserLoginService.RetrofitInstance(getApplicationContext());
        ApiLogin services = refrofit.create(ApiLogin.class);
        UserLoginRequest userLoginRequest = new UserLoginRequest(username,password);
        Call<UserLoginResponse> call = services.login(userLoginRequest);
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                UserLoginResponse userLoginResponse = response.body();
                if  (response.isSuccessful())
                {
                    SharedPreferenceHelper.PREF_FILE = "user";
                    SharedPreferenceHelper.setSharedPreferenceInt(getApplicationContext(),"user_id",userLoginResponse.getId());
                    //deleting all the status of the user after it send to the API
                    DB.getInstance(getApplicationContext()).userStatusDao().deleteAllFromUserStatus();
                    for(Status s: userLoginResponse.getStat())
                    {
                        UserStatus userStatus = new UserStatus();
                        userStatus.setUser_id(Integer.parseInt(s.getUser_id()));
                        userStatus.setQuestion_id(s.getQuestion_id());
                        userStatus.setQuestion_result(s.getQuestion_result());
                        userStatus.setCategory_id(s.getCategory_id());
                        userStatus.setClass_id(s.getClass_id());
                        DB.getInstance(getApplicationContext()).userStatusDao().addUserStatus(userStatus);
                    }
                    DB.getInstance(getApplicationContext()).userDao().updateUser(userLoginResponse.getName());
                    if (userLoginResponse.getUserHistory() != null)
                    {
                        (new LifeRepositories(getApplicationContext())).setLifeToUser(userLoginResponse.getUserHistory().getPrev_user_life());
                        GameOverUtil.saveTime(getApplicationContext(),Long.valueOf(userLoginResponse.getUserHistory().getGame_over_time()));
                    }
                    SharedPreferenceHelper.PREF_FILE = "user_token";
                    SharedPreferenceHelper.setSharedPreferenceString(getApplicationContext(),"token",userLoginResponse.getToken());
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {

            }
        });
    }

    private void sendUserHistory() {
        String username = DB.getInstance(getApplicationContext()).userDao().getUsername();
        LifeRepositories lifeRepositories = new LifeRepositories(getApplicationContext());
        int userLife = (lifeRepositories.getUserLife() <= 0) ? 0 : lifeRepositories.getUserLife();
        long gameOverTime = (GameOverUtil.userGameOverTime(getApplicationContext()) <= 0) ? 0 : GameOverUtil.userGameOverTime(getApplicationContext());
        SharedPreferenceHelper.PREF_FILE ="user_token";
        String token = SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"token",null);
        Retrofit retrofit = UserHistoryService.RetrofitInstance(getApplicationContext());
        APIUserHistory services = retrofit.create(APIUserHistory.class);
        UserHistoryRequest userHistoryRequest = new UserHistoryRequest(username,userLife,String.valueOf(gameOverTime),token);
        Call<UserHistoryResponse> call = services.insertUserHistory(userHistoryRequest);
        call.enqueue(new Callback<UserHistoryResponse>() {
            @Override
            public void onResponse(Call<UserHistoryResponse> call, Response<UserHistoryResponse> response) {
                if (response.body().getCode() == 201 &&  response.isSuccessful())
                {
                    GameOverUtil.deleteGameOverTime(getApplicationContext());
                    loginUser(userUsername,userPassword);
                }
            }

            @Override
            public void onFailure(Call<UserHistoryResponse> call, Throwable t) {
                Toast.makeText(SettingsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendUserStatus() {
        dialog.show();
        List<UserStatus> a = DB.getInstance(getApplicationContext()).userStatusDao().getAllStatusOfUser();
        String username = DB.getInstance(getApplicationContext()).userDao().getUsername();
        SharedPreferenceHelper.PREF_FILE = "user_token";
        String token = SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"token",null);
        Gson gson = new Gson();
        Retrofit refrofit = UserStatusService.RetrofitInstance(getApplicationContext());
        APISendStatus services = refrofit.create(APISendStatus.class);
        UserStatusRequest userStatusRequest = new UserStatusRequest(username,gson.toJson(a),token);
        Call<UserStatusResponse> call = services.sendUserStatus(userStatusRequest);
        call.enqueue(new Callback<UserStatusResponse>() {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response) {
                    SharedPreferenceHelper.PREF_FILE = "user_token";
                    if (SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"token",null) != null) {
                        sendUserHistory();
                    }

            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {

            }
        });
    }


}
