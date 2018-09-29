package com.example.forest.quickguess;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.forest.quickguess.APIsInterface.APIRanks;
import com.example.forest.quickguess.Adapters.RanksAdapter;
import com.example.forest.quickguess.Helpers.Connectivity;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.RecyclerView.Ranks;
import com.example.forest.quickguess.Services.WebService.RanksRequest;
import com.example.forest.quickguess.Services.WebService.RanksResponse;
import com.example.forest.quickguess.Services.WebService.RanksService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RanksActivity extends AppCompatActivity {

    private RecyclerView ranksRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<Ranks> userRanks;
    @BindView(R.id.searchPlayer) EditText searchPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranks);
        ranksRecyclerView = (RecyclerView) findViewById(R.id.ranksRecyclerView);
        ranksRecyclerView.setHasFixedSize(true);
        ranksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        if  (Connectivity.isConnectedFast(getApplicationContext()))
        {
            displayAllUserRanks();
        }  else {  //display message about internet connection
            loadData();
        }

    }

    private void displayAllUserRanks() {
        userRanks = new ArrayList<>();
        Retrofit refrofit = RanksService.RetrofitInstance(getApplicationContext());
        APIRanks services = refrofit.create(APIRanks.class);
        RanksRequest ranksRequest = new RanksRequest();
        Call<List<RanksResponse>> ranksResponseCall = services.getAllRanks(ranksRequest);
        // Set up progress before call
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(RanksActivity.this);
        progressDialog.setMessage("Please wait . . .");
        progressDialog.setTitle("Getting All users");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        ranksResponseCall.enqueue(new Callback<List<RanksResponse>>() {
            @Override
            public void onResponse(Call<List<RanksResponse>> call, Response<List<RanksResponse>> response) {
                List<RanksResponse> ranksResponseList = response.body();
                for(RanksResponse r : ranksResponseList)
                {
                    Ranks ranksItem = new Ranks(r.getUsername(),r.getPoints());
                    userRanks.add(ranksItem);
                }
                adapter = new RanksAdapter(userRanks,getApplicationContext());
                ranksRecyclerView.setAdapter(adapter);
                saveUsersToPref(userRanks);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<RanksResponse>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void saveUsersToPref(List<Ranks> userRanks)
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

}
