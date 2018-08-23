package com.example.forest.quickguessv2;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.forest.quickguessv2.Helpers.FontHelper;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class FunFacts extends Fragment implements View.OnClickListener {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.content)
    TextView content;

    @BindView(R.id.btnNext)
    Button btnNext;

    LinearLayout questionLayout;
    TextView question;
    ImageView imageBackground;

    public FunFacts() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fun_facts, container, false);
        ButterKnife.bind(this,view);
        questionLayout = Objects.requireNonNull(getActivity()).findViewById(R.id.questionLayout);
        question = getActivity().findViewById(R.id.question);
        imageBackground = getActivity().findViewById(R.id.background);
        imageBackground.setImageDrawable(null);
        questionLayout.setVisibility(View.GONE);
        return view;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Typeface fontHelper = new FontHelper().dimboFont(getContext());
        title.setTypeface(fontHelper);
        content.setTypeface(fontHelper);
        SharedPreferenceHelper.PREF_FILE="question";
        title.setText(SharedPreferenceHelper.getSharedPreferenceString(getContext(),"title",null));
        content.setText(SharedPreferenceHelper.getSharedPreferenceString(getContext(),"question_content",null));
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    @OnClick(R.id.btnNext)
    public void onClick(View view) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        questionLayout.setVisibility(View.VISIBLE);
        question.setVisibility(View.VISIBLE);
        imageBackground.setImageResource(getResources().getIdentifier("bg_people","drawable", Objects.requireNonNull(getActivity()).getPackageName()));
    }
}
