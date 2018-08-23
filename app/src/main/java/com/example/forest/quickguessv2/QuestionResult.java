package com.example.forest.quickguessv2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionResult extends Fragment {


    public QuestionResult() {
        // Required empty public constructor
    }


    //saved instance is null
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String result = this.getArguments() != null ? this.getArguments().getString("result") : null;
        View view =  inflater.inflate(R.layout.fragment_question_result, container, false);
        ButterKnife.bind(this,view);
        ImageView resultIcon = (ImageView) view.findViewById(R.id.resultIcon);
        resultIcon.setImageResource(getResources().getIdentifier(result,"drawable", Objects.requireNonNull(getActivity()).getPackageName()));
        TextView question = view.findViewById(R.id.question);
        question.setVisibility(View.GONE);
        return view;
    }

}
