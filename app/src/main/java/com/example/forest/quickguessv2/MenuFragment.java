package com.example.forest.quickguessv2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.forest.quickguessv2.Helpers.Detector;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {
    Detector Netdetector;
    private Unbinder unbinder;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        RelativeLayout welcomeLayout = (getActivity()).findViewById(R.id.welcomeLayout);
        welcomeLayout.setVisibility(View.GONE);
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Netdetector = new Detector(getContext());
        Netdetector.checkConnection();
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }



    @Override
    @OnClick({R.id.btnCategories,R.id.btnAbout,R.id.btnRanks,R.id.btnQuit})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btnCategories:
                intent  = new Intent(getActivity(),CategoriesActivity.class);
                startActivity(intent);
                break;

            case R.id.btnAbout:
                intent  = new Intent(getActivity(),AboutActivity.class);
                startActivity(intent);
                break;

            case R.id.btnRanks:
                //about
                break;

            case R.id.btnQuit:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                QuitFragment QuitFragment = new QuitFragment();
                fragmentTransaction.add(R.id.fragment_quit,QuitFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
