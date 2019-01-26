package com.example.forest.quickguess;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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
import com.sdsmdg.tastytoast.TastyToast;

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
    @BindView(R.id.noInternetConnectionImage)
    ImageView noInternetConnection;

    RanksRepositories ranksRepositories;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranks);

        ButterKnife.bind(this);


        checkInternetConnection();

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

    private void checkInternetConnection() {
        if (Connectivity.isConnectedFast(getApplicationContext())) //request from the api
        {
            ranksRecyclerView = findViewById(R.id.ranksRecyclerView);
            ranksRecyclerView.setVisibility(View.VISIBLE);
            ranksRecyclerView.setHasFixedSize(true);
            ranksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            ranksRepositories = new RanksRepositories(getApplicationContext());
            searchUser.setVisibility(View.VISIBLE);
            displayAllUserRanks();
        } else {
            noInternetConnection.setVisibility(View.VISIBLE);
        }
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
        // Set up progress before call
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing your request please wait . . .");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        sendUserStatus(); // send current status
        userRanks = new ArrayList<>();
        Retrofit refrofit = RanksService.RetrofitInstance(getApplicationContext());
        APIRanks services = refrofit.create(APIRanks.class);
        Call<List<RanksResponse>> ranksResponseCall = services.getAllRanks();


        // show it
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
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<RanksResponse>> call, Throwable t) {
                progressDialog.dismiss();
                finish();
                TastyToast.makeText(getApplicationContext(),"Please check your internet connection",TastyToast.LENGTH_LONG,TastyToast.INFO);
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


}
