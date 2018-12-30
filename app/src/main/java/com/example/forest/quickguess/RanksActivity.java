package com.example.forest.quickguess;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.forest.quickguess.APIsInterface.APIRanks;
import com.example.forest.quickguess.APIsInterface.APISendStatus;
import com.example.forest.quickguess.Adapters.RanksAdapter;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.DB.Ranks.RanksRepositories;
import com.example.forest.quickguess.DB.UserStatus.UserStatus;
import com.example.forest.quickguess.Helpers.Connectivity;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.RecyclerView.Ranks;
import com.example.forest.quickguess.Services.WebService.RanksRequest;
import com.example.forest.quickguess.Services.WebService.RanksResponse;
import com.example.forest.quickguess.Services.WebService.RanksService;
import com.example.forest.quickguess.Services.WebService.UserStatusRequest;
import com.example.forest.quickguess.Services.WebService.UserStatusResponse;
import com.example.forest.quickguess.Services.WebService.UserStatusService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RanksActivity extends AppCompatActivity {

    private RecyclerView ranksRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<Ranks> userRanks;
    @BindView(R.id.searchPlayer) EditText searchUser;

    RanksRepositories ranksRepositories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranks);

        ButterKnife.bind(this);
        ranksRecyclerView = findViewById(R.id.ranksRecyclerView);
        ranksRecyclerView.setHasFixedSize(true);
        ranksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ranksRepositories = new RanksRepositories(getApplicationContext());

        if (Connectivity.isConnectedFast(getApplicationContext())) //request from the api
        {
            displayAllUserRanks();
        }

      /*else {
            getRanksInDB();
        }*/
        searchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchUserInRanks(editable.toString());
            }
        });
    }


    private void getRanksInDB() {

            userRanks = new ArrayList<>();
            List<com.example.forest.quickguess.DB.Ranks.Ranks> ranksList = DB.getInstance(getApplicationContext())
                    .ranksDao().getAll();
            for(com.example.forest.quickguess.DB.Ranks.Ranks r : ranksList)
            {
                Ranks ranksItem = new Ranks(r.getUsername(),r.getPoints());
                userRanks.add(ranksItem);
            }
            adapter = new RanksAdapter(userRanks,getApplicationContext());
            ranksRecyclerView.setAdapter(adapter);
    }

    private void searchUserInRanks(String data) {
        userRanks = new ArrayList<>();
        List<com.example.forest.quickguess.DB.Ranks.Ranks> ranksList = DB.getInstance(getApplicationContext())
                .ranksDao().getAll();
        for(com.example.forest.quickguess.DB.Ranks.Ranks r : ranksList)
        {
            if (r.getUsername().toLowerCase().contains(data.toLowerCase()))
            {
                Ranks ranksItem = new Ranks(r.getUsername(),r.getPoints());
                userRanks.add(ranksItem);
            }
        }
        adapter = new RanksAdapter(userRanks,getApplicationContext());
        ranksRecyclerView.setAdapter(adapter);
    }




    private void displayAllUserRanks() {
        sendUserStatus(); // send current status
        userRanks = new ArrayList<>();
        Retrofit refrofit = RanksService.RetrofitInstance(getApplicationContext());
        APIRanks services = refrofit.create(APIRanks.class);
        Call<List<RanksResponse>> ranksResponseCall = services.getAllRanks();
        // Set up progress before call
        final AlertDialog dialog = new SpotsDialog.Builder().setContext(this)
                .setTheme(R.style.dialogTheme)
                .build();
        dialog.show();

        // show it
        dialog.show();
        ranksResponseCall.enqueue(new Callback<List<RanksResponse>>() {
            @Override
            public void onResponse(Call<List<RanksResponse>> call, Response<List<RanksResponse>> response) {
                List<RanksResponse> ranksResponseList = response.body();
                    for(RanksResponse r : ranksResponseList)
                    {
                        Ranks ranksItem = new Ranks(r.getUsername(),r.getPoints());
                        userRanks.add(ranksItem);
                        ranksRepositories.rankCreator(r.getUsername(),r.getPoints());
                    }
                adapter = new RanksAdapter(userRanks,getApplicationContext());
                ranksRecyclerView.setAdapter(adapter);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<RanksResponse>> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    private void sendUserStatus() {
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

            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {

            }
        });
    }
/*    private void saveUsersToPref(List<Ranks> userRanks)
    {
        SharedPreferenceHelper.PREF_FILE ="all_users";
        Gson gson = new Gson();
        String json = gson.toJson(userRanks);
        SharedPreferenceHelper.setSharedPreferenceString(getApplicationContext(),"user_list",json);
    }

    private void loadData()
    {
        SharedPreferenceHelper.PREF_FILE ="all_users";
        Gson gson = new Gson();
        String json = SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"user_list",null);
        Type type = new TypeToken<ArrayList<Ranks>>() {}.getType();
        userRanks = gson.fromJson(json,type);
        if (userRanks == null)
        {
            userRanks = new ArrayList<>();
        } else {
            adapter = new RanksAdapter(userRanks,getApplicationContext());
            ranksRecyclerView.setAdapter(adapter);
        }
    }

    private boolean checkUserListValue()
    {
        SharedPreferenceHelper.PREF_FILE ="all_users";
        String json = SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"user_list",null);
        return json == null && Connectivity.isConnectedFast(getApplicationContext());
    }*/

}
