package com.example.forest.quickguessv2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuitFragment extends Fragment implements View.OnClickListener{


    public QuitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quit, container, false);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    @OnClick({R.id.quit_no,R.id.quit_yes})
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.quit_no :
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;

            case R.id.quit_yes:
                Objects.requireNonNull(getActivity()).finish();
                System.exit(0);
                break;
        }
    }
}
