package com.example.forest.quickguessv2;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.User.UserRepositories;
import com.example.forest.quickguessv2.Helpers.RedirectHelper;

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
    private int user_life;
    private int user_points;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        RelativeLayout welcomeLayout = (Objects.requireNonNull(getActivity())).findViewById(R.id.welcomeLayout);
        welcomeLayout.setVisibility(View.GONE);
        userLife.setText(null);
        userPoints.setText(null);
        userLife.setText(String.valueOf(user_life));
        userPoints.setText(String.valueOf(user_points));
        super.onResume();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this,view);
        userLife.setText(null);
        userPoints.setText(null);
        userLife.setTypeface(Typeface.createFromAsset(getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        userPoints.setTypeface(Typeface.createFromAsset(getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        user_life = UserRepositories.getLifeOfUser(((MainActivity)getActivity()).lifeRepositories);
        user_points = UserRepositories.getUserPoints(((MainActivity)getActivity()).pointsRepositories);
        userLife.setText(String.valueOf(user_life));
        userPoints.setText(String.valueOf(user_points));
        return view;
    }


    @Override
    @OnClick({R.id.btnCategories,R.id.btnAbout,R.id.btnRanks,R.id.btnQuit})
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
                disposeFragments();
                break;

            case R.id.btnRanks:
                //about
                break;

            case R.id.btnQuit:
                ((MainActivity)getActivity()).fragmentUtil.startQuitFragment();
                break;
        }
        try {
            Class <?> Cref = Class .forName("com.example.forest.quickguessv2."+classname);
            new RedirectHelper(getActivity(),Cref);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
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
