package com.example.forest.quickguess;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.forest.quickguess.Utilities.IOnBackPressed;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionResult extends Fragment implements IOnBackPressed {

    @BindView(R.id.resultIcon) ImageView resultIcon;
    private Unbinder unbinder;

    public QuestionResult() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view =  inflater.inflate(R.layout.fragment_question_result, container, false);
            unbinder = ButterKnife.bind(this,view);
            String result = this.getArguments().getString("result");
            ((AnswerQuestion)getActivity()).choice_a.setClickable(false);
            ((AnswerQuestion)getActivity()).choice_b.setClickable(false);
            ((AnswerQuestion)getActivity()).choice_c.setClickable(false);
            ((AnswerQuestion)getActivity()).choice_d.setClickable(false);
            resultIcon.setImageResource(getResources().getIdentifier(result,"drawable", Objects.requireNonNull(getActivity()).getPackageName()));
        return view;
    }

    @OnClick(R.id.questionResult)
    public void tapScreen()
    {
        FragmentManager fm = getFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; i++) {
            fm.popBackStack();
        }
        ((AnswerQuestion)getActivity()).gotoFunFacts();
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStack();
        }
       Log.d("Instance",getContext().toString());
    }
}
