package com.example.forest.quickguess;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.DB.User.UserRepositories;
import com.example.forest.quickguess.Helpers.RedirectHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Helpers.WindowHelper;
import com.example.forest.quickguess.Utilities.SoundUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {

    private Unbinder unbinder;
    @BindView(R.id.userLife) TextView userLife;
    @BindView(R.id.userPoints) TextView userPoints;
    @BindView(R.id.btnCategories) ImageButton btnCategories;

    private int user_life;
    private int user_points;
    YoYo.YoYoString animation;


    public MenuFragment() {
        // Required empty public constructor
    }




    @Override
    public void onResume() {
        RelativeLayout welcomeLayout = (Objects.requireNonNull(getActivity())).findViewById(R.id.welcomeLayout);
        userLife.setText(null);
        userPoints.setText(null);
        welcomeLayout.setVisibility(View.GONE);
        initUserPoints();
        userLife.setText(String.valueOf(user_life));
        userPoints.setText(String.valueOf(user_points));
        ((MainActivity)getActivity()).pointsRepositories.sendPoints();
        super.onResume();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferenceHelper.PREF_FILE = "points";
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        WindowHelper.hideNavigationBar(getActivity());
        unbinder = ButterKnife.bind(this,view);
        userLife.setText(null);
        userPoints.setText(null);
        userLife.setTypeface(Typeface.createFromAsset(getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        userPoints.setTypeface(Typeface.createFromAsset(getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        user_life = UserRepositories.getLifeOfUser(((MainActivity)getActivity()).lifeRepositories);
        initUserPoints();
        userLife.setText(String.valueOf(user_life));
        userPoints.setText(String.valueOf(user_points));
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUserPoints();
        animation = YoYo.with(Techniques.Shake)
                .duration(2000)
                .repeat(-1)
                .playOn(btnCategories);
        super.onViewCreated(view, savedInstanceState);
    }

    private void initUserPoints() {
        /*PointsRepositories pointsRepositories = ((MainActivity)getActivity())
                                                .pointsRepositories;*/
      /*  int partial_points = SharedPreferenceHelper.
                                getSharedPreferenceInt(getContext(),"user_points",0) + UserRepositories.getUserPoints(pointsRepositories);
        int fetchPointsByAnswer = DB.getInstance(getContext()).userStatusDao().countAllForPoints();
        if (partial_points > fetchPointsByAnswer)
        {
            user_points = fetchPointsByAnswer * 100;
        } else if (partial_points == fetchPointsByAnswer){
            user_points = partial_points;
        }*/

      user_points = DB.getInstance(getContext()).userStatusDao().countAllForPoints() * 100;

    }



    @Override
    @OnClick({R.id.btnCategories,R.id.btnAbout,R.id.btnRanks})
    public void onClick(View view) {
        String classname = null;
        switch (view.getId())
        {
            case R.id.btnCategories:
                classname = "CategoriesActivity";
                disposeFragments();
                break;

            case R.id.btnAbout:
                classname = "AboutActivity";
                SoundUtil.songLoad(getContext(),R.raw.click)
                    .start();
                disposeFragments();
                break;

            case R.id.btnRanks:
                classname = "RanksActivity";
                SoundUtil.songLoad(getContext(),R.raw.click)
                        .start();
                disposeFragments();
                break;
        }
        try {
            Class <?> Cref = Class .forName("com.example.forest.quickguess."+classname);
            new RedirectHelper(getActivity(),Cref);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        animation.stop();
        animation = null;
        DB.getInstance(getActivity().getApplicationContext()).destroyInstance();
        unbinder.unbind();
        super.onDestroyView();
    }

    private void disposeFragments()
    {
        FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

}
