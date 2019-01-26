package com.example.forest.quickguess;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.Models.FunFactsContent;
import com.example.forest.quickguess.Utilities.FragmentUtil;
import com.example.forest.quickguess.Utilities.GameOverUtil;
import com.example.forest.quickguess.Utilities.IOnBackPressed;
import com.example.forest.quickguess.Utilities.UserUtil;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

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

    AnswerQuestion AnswerQuestionActivity;


    TextView question;


    RelativeLayout timerLayout;

    private Context context;
    private Unbinder unbinder;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    ShareLinkContent linkContent;
    protected int items[] = new int[]{30, 60, 90};


    @Override
    public void onResume()
    {
        ((AnswerQuestion)getActivity()).clockTick.release();
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

        //variables for facebook share
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        context = getContext();

        question = getActivity().findViewById(R.id.question);
        timerLayout = getActivity().findViewById(R.id.timerLayout);

        return view;
    }



    @OnClick(R.id.facebookShare)
    public void shareLink()
    {
        linkContent = new ShareLinkContent.Builder()
                .setQuote("Always seek KNOWLEDGE")
                .setContentUrl(Uri.parse("https://playquickguess.herokuapp.com/"))
                .build();
        if (ShareDialog.canShow(ShareLinkContent.class))
        {
            shareDialog.show(linkContent, ShareDialog.Mode.WEB);
        }
    }


    private void loadQuestionImage(String image) {
        //get the category that user play
        String category = UserUtil.getSelectedCategoryOfUser(context);

        //get the image name
        int imgResId = getResources().getIdentifier(image,"drawable",context.getPackageName());

        //check if the image is in the drawable files
        if  (imgResId != 0) {
            Picasso.with(getContext()).load(imgResId).into(imageFunfacts);
        } else {  // otherwise make an request to API server
            Picasso.with(getContext())
                    .load("https://res.cloudinary.com/dpcxcsdiw/image/upload/w_200,h_200,q_auto,fl_lossy/"+category.toLowerCase()+"/"+image)
                    .into(imageFunfacts);
        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // question title
        title.setText(FunFactsContent.getTitle());

        // question content
        content.setText(FunFactsContent.getContent());

        //get the image of question and load to image view
        loadQuestionImage(FunFactsContent.getImage());

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    @OnClick(R.id.btnNext)
    public void onClick(View view) {
        isGameOverOrNot();
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (FragmentUtil.sDisableFragmentAnimations) {
            Animation animation = new Animation() {};
            animation.setDuration(0);
            return animation;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    public void isGameOverOrNot()
    {
        AnswerQuestionActivity = (AnswerQuestion)getActivity();
        if (AnswerQuestionActivity.lifeRepositories != null && AnswerQuestionActivity.lifeRepositories.getUserLife() < 0) {

                //remove this fragment
                this.disposeFragments();
                //save game over time of user
                GameOverUtil.saveTime(getContext(),System.currentTimeMillis());

                AnswerQuestionActivity.life.setText(
                                         String.valueOf(AnswerQuestionActivity.lifeRepositories.getUserLife())
                                 );

                //display the game over fragment
                AnswerQuestionActivity.displayGameOverFragment();
            } else {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentUtil.sDisableFragmentAnimations = true;
                fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentUtil.sDisableFragmentAnimations = false;
                continueLayout();
            }
    }

    private void disposeFragments() {
        for (Fragment fragment:getFragmentManager().getFragments()) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    protected void continueLayout() {
        AnswerQuestionActivity.getQuestion();
        AnswerQuestionActivity.displayQuestionLayout();
        AnswerQuestionActivity.displayTimer();
        AnswerQuestionActivity.userPoints = 0;
        AnswerQuestionActivity.clockTick =  ((AnswerQuestion)getActivity()).soundTick(R.raw.clock_tick);
        AnswerQuestionActivity.clockTick.start();
    }



    @Override
    public void onDestroyView() {
        unbinder.unbind();
        linkContent = null;
        super.onDestroyView();
    }


    @Override
    public void onBackPressed() {
        timerLayout.removeAllViews();
        FragmentManager fm = getFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
}
