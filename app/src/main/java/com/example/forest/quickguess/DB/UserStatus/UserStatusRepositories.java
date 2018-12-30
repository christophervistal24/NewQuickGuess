package com.example.forest.quickguess.DB.UserStatus;

import android.content.Context;

import com.example.forest.quickguess.APIsInterface.APISendStatus;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Services.WebService.UserStatusRequest;
import com.example.forest.quickguess.Services.WebService.UserStatusResponse;
import com.example.forest.quickguess.Services.WebService.UserStatusService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserStatusRepositories {

    public static int answeredByCategory(Context context, String name)
    {
        return DB.getInstance(context).userStatusDao().countAnsweredQuestion(
                DB.getInstance(context).categoriesQuestionDao().getCategoryIdByName(name)
        );
    }

    public static List<UserStatus> getAndSendStatus(Context context)
    {
        if  (DB.getInstance(context).userStatusDao().countAllForPoints() == 0) {
                return null;
        }
        return DB.getInstance(context).userStatusDao().getAllStatusOfUser();
    }

    public static void sendStatus(Context context , String username)
    {
        List<UserStatus> userStatus = DB.getInstance(context).userStatusDao().getAllStatusOfUser();
        SharedPreferenceHelper.PREF_FILE = "user_token";
        String token = SharedPreferenceHelper.getSharedPreferenceString(context,"token",null);
        Gson gson = new Gson();
        Retrofit refrofit = UserStatusService.RetrofitInstance(context);
        APISendStatus services = refrofit.create(APISendStatus.class);
        UserStatusRequest userStatusRequest = new UserStatusRequest(username,gson.toJson(userStatus),token);
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
