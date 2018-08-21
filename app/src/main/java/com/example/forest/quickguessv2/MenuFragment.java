package com.example.forest.quickguessv2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this,view);
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
                //about
                break;

            case R.id.btnRanks:
                //about
                break;

            case R.id.btnQuit:
                //quit
                break;
        }
    }
}
