package com.example.forest.quickguessv2;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionResult extends Fragment {



    public QuestionResult() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view =  inflater.inflate(R.layout.fragment_question_result, container, false);
            ButterKnife.bind(this,view);
            String result = this.getArguments().getString("result");
            ImageView resultIcon = (ImageView) view.findViewById(R.id.resultIcon);
            TextView life = (TextView) view.findViewById(R.id.life);
            resultIcon.setImageResource(getResources().getIdentifier(result,"drawable", Objects.requireNonNull(getActivity()).getPackageName()));
            TextView question = getActivity().findViewById(R.id.question);
            //hide the question layout
            question.setVisibility(View.GONE);
          ((AnswerQuestion)getActivity()).onPreExecute();
        return view;
    }




}
