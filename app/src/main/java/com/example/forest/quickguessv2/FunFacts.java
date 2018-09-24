package com.example.forest.quickguessv2;


import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.forest.quickguessv2.DB.GameOver.GameoverRepositories;
import com.example.forest.quickguessv2.DB.Life.LifeRepositories;
import com.example.forest.quickguessv2.Helpers.FontHelper;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.Utilities.IOnBackPressed;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class FunFacts extends Fragment implements View.OnClickListener , IOnBackPressed {

    @BindView(R.id.title) TextView title;
    @BindView(R.id.content) TextView content;
    @BindView(R.id.btnNext) Button btnNext;
    @BindView(R.id.facebookShare) Button facebookShare;
    @BindView(R.id.imageFunfacts) ImageView imageFunfacts;

    LinearLayout questionLayout;
    TextView question;
    ImageView imageBackground;
    RadioGroup RGroup;
    LinearLayout timerLayout;

    private Context context;
    private Unbinder unbinder;
    public ArrayList<RadioButton> listOfRadioButtons;
    int count = 0;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    protected int items[] = new int[]{30, 60, 90};


    @Override
    public void onResume()
    {
        ((AnswerQuestion)getActivity()).sample.release();
        ((AnswerQuestion)getActivity()).countDownTimer.cancel();
        super.onResume();
    }

    public FunFacts() {
        // Required empty public constructor
    }

    //TODO REFACTOR
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
        timerLayout = getActivity().findViewById(R.id.timerLayout);
        RGroup.clearCheck();
        imageBackground = getActivity().findViewById(R.id.background);
        questionLayout.setVisibility(questionLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE );
        radioBackBackground();
        ((AnswerQuestion)getActivity()).removeCallback();
        return view;
    }


    @OnClick(R.id.facebookShare)
    public void shareLink()
    {
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setQuote("Always seek KNOWLEDGE")
                .setContentUrl(Uri.parse("https://playquickguess.herokuapp.com/"))
                .build();
        if (ShareDialog.canShow(ShareLinkContent.class))
        {
            shareDialog.show(linkContent, ShareDialog.Mode.WEB);
        }
    }



    //TODO REFACTOR
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Typeface fontHelper = new FontHelper().dimboFont(context);
        title.setTypeface(fontHelper);
        content.setTypeface(fontHelper);
        btnNext.setTypeface(fontHelper);
        facebookShare.setTypeface(fontHelper);
        SharedPreferenceHelper.PREF_FILE="question";
        title.setText(SharedPreferenceHelper.getSharedPreferenceString(context,"title",null));
        content.setText(SharedPreferenceHelper.getSharedPreferenceString(context,"question_content",null));
        String image = SharedPreferenceHelper.getSharedPreferenceString(context,"question_image",null);
        Picasso.with(getContext())
                .load("https://res.cloudinary.com/dpcxcsdiw/image/upload/w_200,h_200,q_auto,fl_lossy/animals/"+image)
                .placeholder(R.drawable.placeholder)
                .into(imageFunfacts);
        radioBackBackground();

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    @OnClick(R.id.btnNext)
    public void onClick(View view) {
        isGameOverOrNot();
    }


    public void isGameOverOrNot()
    {
        AnswerQuestion answerQuestionActivity = (AnswerQuestion)getActivity();
        LifeRepositories lifeRepositories = ((AnswerQuestion) getActivity()).lifeRepositories;

        if  (lifeRepositories.getUserLife() <= 0)
        {
            GameoverRepositories.gameOver(lifeRepositories);
            answerQuestionActivity.life.setText(String.valueOf(lifeRepositories.getUserLife()));
            answerQuestionActivity.displayGameOverFragment();
        } else {
            FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
            continueLayout();
            isUserCanSaveFriends();
        }
    }

    protected void continueLayout() {
        questionLayout.setVisibility(View.VISIBLE);
        question.setVisibility(View.VISIBLE);
        ((AnswerQuestion)getActivity()).timerLayoutDisplayOrHide();
        ((AnswerQuestion)getActivity()).radioGroupdisplayOrHide();
        ((AnswerQuestion)getActivity()).countDownTimer.start();
        ((AnswerQuestion)getActivity()).getQuestion();
        ((AnswerQuestion)getActivity()).userPoints = 0;
        ((AnswerQuestion)getActivity()).sample =  ((AnswerQuestion)getActivity()).soundTick(R.raw.clock_tick);
        ((AnswerQuestion)getActivity()).sample.start();
    }

    protected void isUserCanSaveFriends() {
       try {
           int category_id = ((AnswerQuestion)getActivity()).q.getCategory_id();
           ((AnswerQuestion)getActivity()).friendsRepositories.checkAnsweredQuestion(category_id, items);
       } catch  (NullPointerException e) {}
    }

    private void radioBackBackground()
    {
        listOfRadioButtons = new ArrayList<>();
        for(int i=0; i<count; i++)
        {
            View o = RGroup.getChildAt(i);
            if (o instanceof RadioButton) {
                listOfRadioButtons.add((RadioButton)o);
            }
            listOfRadioButtons.get(i).setClickable(true);
      }

    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    //TODO REFACTOR
    @Override
    public void onBackPressed() {
        timerLayout.removeAllViews();
        FragmentManager fm = getFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            fm.popBackStack();
        }
    }
}
