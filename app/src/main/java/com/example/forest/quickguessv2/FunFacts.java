package com.example.forest.quickguessv2;


import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguessv2.Helpers.FontHelper;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


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

    @BindView(R.id.facebookShare) Button facebookShare;


    LinearLayout questionLayout;
    TextView question;
    ImageView imageBackground;
    RadioGroup RGroup;

    private Context context;
    private Unbinder unbinder;
    public ArrayList<RadioButton> listOfRadioButtons;
    int count = 0;
    CallbackManager callbackManager;
    ShareDialog shareDialog;


    public FunFacts() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fun_facts, container, false);
        unbinder = ButterKnife.bind(this,view);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        context = getContext();
        questionLayout = Objects.requireNonNull(getActivity()).findViewById(R.id.questionLayout);
        question = getActivity().findViewById(R.id.question);
        RGroup = getActivity().findViewById(R.id.RGroup);
        count = RGroup.getChildCount();
        RGroup.clearCheck();
        imageBackground = getActivity().findViewById(R.id.background);
        imageBackground.setImageDrawable(null);
        questionLayout.setVisibility(View.GONE);
        radioBackBackground();
        facebookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("This is a sample")
                        .setContentUrl(Uri.parse("https://youtube.com"))
                        .build();
                if (ShareDialog.canShow(ShareLinkContent.class))
                {
                    shareDialog.show(linkContent, ShareDialog.Mode.WEB);
                }
            }
        });
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Typeface fontHelper = new FontHelper().dimboFont(context);
        title.setTypeface(fontHelper);
        content.setTypeface(fontHelper);
        SharedPreferenceHelper.PREF_FILE="question";
        title.setText(SharedPreferenceHelper.getSharedPreferenceString(context,"title",null));
        content.setText(SharedPreferenceHelper.getSharedPreferenceString(context,"question_content",null));
        radioBackBackground();
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    @OnClick(R.id.btnNext)
    public void onClick(View view) {
        FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        questionLayout.setVisibility(View.VISIBLE);
        question.setVisibility(View.VISIBLE);
        imageBackground.setImageResource(getResources().getIdentifier("bg_people","drawable", Objects.requireNonNull(getActivity()).getPackageName()));
        AnswerQuestion.countDownTimer.start();
        ((AnswerQuestion)getActivity()).getAllQuestions();
    }

    private void radioBackBackground()
    {
        listOfRadioButtons = new ArrayList<RadioButton>();
        for(int i=0; i<count; i++)
        {
            View o = RGroup.getChildAt(i);
            if (o instanceof RadioButton) {
                listOfRadioButtons.add((RadioButton)o);
                listOfRadioButtons.get(i).setBackground(ContextCompat.getDrawable(context, R.drawable.btn_correct));
            }
            listOfRadioButtons.get(i).setClickable(true);
      }

    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
