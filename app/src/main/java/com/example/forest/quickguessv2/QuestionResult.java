package com.example.forest.quickguessv2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionResult extends Fragment {

    TextView life;
    TextView question;

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
            life = getActivity().findViewById(R.id.life);
            question = getActivity().findViewById(R.id.question);
            resultIcon.setImageResource(getResources().getIdentifier(result,"drawable", Objects.requireNonNull(getActivity()).getPackageName()));
            question.setVisibility(View.GONE);
            ((AnswerQuestion)getActivity()).onPreExecute();
        return view;
    }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
