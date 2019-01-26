package com.example.forest.quickguess;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguess.APIsInterface.APIRanks;
import com.example.forest.quickguess.APIsInterface.APIResetStatus;
import com.example.forest.quickguess.APIsInterface.APISendStatus;
import com.example.forest.quickguess.DB.Categories.QuestionCategory;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Points.Points;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.DB.UserStatus.UserStatus;
import com.example.forest.quickguess.Helpers.Connectivity;
import com.example.forest.quickguess.Helpers.FontHelper;
import com.example.forest.quickguess.Helpers.RedirectHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;
import com.example.forest.quickguess.Services.WebService.RanksResponse;
import com.example.forest.quickguess.Services.WebService.RanksService;
import com.example.forest.quickguess.Services.WebService.UserStatusRequest;
import com.example.forest.quickguess.Services.WebService.UserStatusResponse;
import com.example.forest.quickguess.Services.WebService.UserStatusService;
import com.example.forest.quickguess.Utilities.InternetConnectionUtil;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FinishCategoryActivity extends AppCompatActivity {


    @BindView(R.id.message) TextView message;
    @BindView(R.id.trophyLayout) RelativeLayout trophyL;
    @BindView(R.id.trophy) ImageView trophy;
    @BindView(R.id.viewKonfetti) nl.dionsegijn.konfetti.KonfettiView viewKonfetti;

    String category;
    int category_id;
    int class_id;
    Points points;
    ProgressDialog progressDialog;

    private String[] difficultyLevel = {"easy","moderate","difficult"};
    InternetConnectionUtil internetUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_category);
        WindowHelper.hideNavigationBar(this);
        ButterKnife.bind(this);
        points = new Points();
        displayMessage();
        congratsKonfetti();
    }

    private void congratsKonfetti() {
        viewKonfetti.build()
                .addColors(Color.parseColor("#ff7675"), Color.parseColor("#0984e3"),Color.parseColor("#fdcb6e"))
                .setDirection(50, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(13, 5f))
                .setPosition(-50f, viewKonfetti.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);


    }


    private void displayMessage() {

        try {
            SharedPreferenceHelper.PREF_FILE="user_played";
            category = SharedPreferenceHelper
                    .getSharedPreferenceString(getApplicationContext(),"category",null)
                    .toLowerCase();
            category_id = DB.getInstance(getApplicationContext()).categoriesQuestionDao().getCategoryIdByName(category);
            class_id = QuestionRepositories.class_id;
            message.setText(String.format("Congratulations you finish the %s category now you can see all fun facts", category.toUpperCase()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        DB.getInstance(getApplicationContext()).destroyInstance();
        super.onDestroy();
    }

    @OnClick(R.id.viewAllFunFacts)
    public void onClick()
    {
        new RedirectHelper(getApplicationContext(),DisplayAllFunFacts.class);
    }

    @OnClick(R.id.resetThisLevel)
    public void resetCategory()
    {
       if   (Connectivity.isConnectedFast(getApplicationContext()))
       {
           AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
           builder1.setMessage("Resetting this category may need an internet connection are you sure you want to " +
                   "reset this category?");
           builder1.setCancelable(true);
           builder1.setPositiveButton(
                   "Yes",
                   new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           reset();
                           dialog.cancel();
                       }
                   });

           builder1.setNegativeButton(
                   "No",
                   new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           dialog.cancel();
                       }
                   });

           AlertDialog alert11 = builder1.create();
           alert11.show();
       } else {
           TastyToast.makeText(getApplicationContext(), "Please check your internet connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void reset()
    {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing . . .");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Retrofit refrofit = RanksService.RetrofitInstance(getApplicationContext());
        APIRanks services = refrofit.create(APIRanks.class);
        retrofit2.Call<List<RanksResponse>> ranksResponseCall = services.getAllRanks();
        ranksResponseCall.enqueue(new retrofit2.Callback<List<RanksResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<List<RanksResponse>> call, retrofit2.Response<List<RanksResponse>> response) {
                if (response.isSuccessful())
                {
                    updateUserPoints();
                    resetStatusOfTheUser();
                    DB.getInstance(getApplicationContext()).userStatusDao().deleteByClassIdAndCategoryId(class_id,category_id);
                    finish();
                    TastyToast.makeText(getApplicationContext(),"Successfully reset the " + category.toUpperCase() + " " + difficultyLevel[class_id-1] , TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<List<RanksResponse>> call, Throwable t) {
                TastyToast.makeText(getApplicationContext(), "Please check your internet connection", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                progressDialog.dismiss();
            }
        });
    }

    private void resetStatusOfTheUser() {
        String username = DB.getInstance(getApplicationContext()).userDao().getUsername();
        List<UserStatus> userStatus = DB.getInstance(getApplicationContext())
                                        .userStatusDao()
                                        .getStatusOfUserByClassIdAndCategoryId(class_id,category_id);
        if(Connectivity.isConnectedFast(getApplicationContext()) && !userStatus.isEmpty())
        {
            SharedPreferenceHelper.PREF_FILE = "user_token";
            String token = SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(),"token",null);
            Gson gson = new Gson();
            Retrofit refrofit = UserStatusService.RetrofitInstance(getApplicationContext());
            APIResetStatus services = refrofit.create(APIResetStatus.class);
            UserStatusRequest userStatusRequest = new UserStatusRequest(username,gson.toJson(userStatus),token);
            Call<UserStatusResponse> call = services.resetUserStatus(userStatusRequest);
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

    private void updateUserPoints() {
        int questionThatAnsweredCorrect = DB.getInstance(getApplicationContext()).userStatusDao().countAllCorrectByClassIdAndCategoryId(category_id,class_id);
        int currentPoints = DB.getInstance(getApplicationContext()).pointsDao().getUserPoints();
        DB.getInstance(getApplicationContext()).pointsDao().delete();
        points.setPoints((questionThatAnsweredCorrect * 100) + currentPoints);
        DB.getInstance(getApplicationContext()).pointsDao().insert(points);
        }
}
