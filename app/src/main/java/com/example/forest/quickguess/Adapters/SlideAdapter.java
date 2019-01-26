package com.example.forest.quickguess.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguess.AnswerQuestion;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.Helpers.LifeRevive;
import com.example.forest.quickguess.Helpers.RedirectHelper;
import com.example.forest.quickguess.R;
import com.example.forest.quickguess.Utilities.UserUtil;
import com.sdsmdg.tastytoast.TastyToast;

public class SlideAdapter extends PagerAdapter implements View.OnClickListener {
    private Context context;

    private LifeRepositories lifeRepositories;
    private LifeRevive lifeRevive;

    // list of titles
    private String[] lst_title = {
         "PEOPLE","PLANTS","ANIMALS","GEOGRAPHY", "SPORTS","MUSIC","TECHNOLOGY","ENTERTAINMENT"
    };

    // list of background colors
    private String[]  list_background = {
            "cat_people_2",
            "cat_plants_2",
            "cat_animals_2",
            "cat_geo_2",
            "cat_sports_2",
            "cat_music_2",
            "cat_tech_2",
            "cat_enter_2"
    };

    private String[] difficulty_level_id = {
        "1",
        "2",
        "3"
    };

    int category_id;



    public SlideAdapter(Context context) {
        this.context = context;
        lifeRepositories = new LifeRepositories(context);
        lifeRevive = new LifeRevive(context,lifeRepositories);


    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.category_item,container,false);

        setArrows(position , view);
        ImageView pageBackground = view.findViewById(R.id.pageBackground);

        Button btnEasy = view.findViewById(R.id.easyLevel);
        Button btnModerate = view.findViewById(R.id.moderateLevel);
        Button btnDifficult = view.findViewById(R.id.difficultLevel);

        this.eventHandler(lst_title[position], btnEasy, btnModerate, btnDifficult);

        //set bacgkground
        this.setBacgkground(list_background[position], pageBackground);

        //set category id
        this.setCategoryIdByName(lst_title[position]);

        container.addView(view);
        return view;
    }

    private void setCategoryIdByName(String s) {
        category_id = DB.getInstance(context).categoriesQuestionDao().getCategoryIdByName(s.toLowerCase());
    }

    //set background dynamically
    private void setBacgkground(String name, ImageView pageBackground) {
        pageBackground.setImageResource(context.getResources().getIdentifier(name,"drawable",context.getPackageName()));
    }

    //add onclick listener for level of difficulty buttons
    private void eventHandler(String tag, Button btnEasy, Button btnModerate, Button btnDifficult) {
        buttonSetTags(tag, btnEasy, btnModerate, btnDifficult);
        btnEasy.setOnClickListener(this);
        btnModerate.setOnClickListener(this);
        btnDifficult.setOnClickListener(this);
    }

    //set tags for button
    //this will help to easily determine what kind of category and level user wants to play
    private void buttonSetTags(String tag, Button easyButton, Button moderateButton, Button difficultButton) {
        easyButton.setTag(R.id.class_id,difficulty_level_id[0]);
        easyButton.setTag(R.id.category, tag);

        moderateButton.setTag(R.id.class_id,difficulty_level_id[1]);
        moderateButton.setTag(R.id.category, tag);

        difficultButton.setTag(R.id.class_id,difficulty_level_id[2]);
        difficultButton.setTag(R.id.category, tag);

    }


    //set arrow for the view
    private void setArrows(int position , View v) {
        ImageView leftArrow = v.findViewById(R.id.leftArrow);
        ImageView rightArrow = v.findViewById(R.id.rightArrow);
        if (position >= 1 && position <= 6) {
            //at first category the left arrow doesn't have a background
            //so in second category we need to add a background or left arrow
           leftArrow.setImageResource(R.drawable.left);

            this.animate(leftArrow,-200);
            this.animate(rightArrow,200);

        } else if (position == 0) {
            this.animate(rightArrow,200);
        } else if (position == 7) {
            //if the user reach the last category hide the right arrow
            rightArrow.setVisibility(View.GONE);

            leftArrow.setImageResource(R.drawable.left);
            this.animate(leftArrow,200);
        }
    }


    private void animate(ImageView v, int to) {
        YoYo.with(Techniques.Pulse)
                .duration(800)
                .delay(500)
                .pivotX(to)
                .repeat(-1)
                .playOn(v);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {}

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public int getCount() {
        return lst_title.length;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(RelativeLayout)object);
    }


    @Override
    public void onClick(View view) {
        //get the difficult level that user choose
          String class_id = String.valueOf(view.getTag(R.id.class_id));

        //get the category that user choose
          String category = (String)view.getTag(R.id.category);

        //set the class_id or difficult level
          QuestionRepositories.class_id = Integer.parseInt(class_id);

          //setters
          UserUtil.setUserCategoryPlayed(context,category);
          UserUtil.setUserCategoryDifficultPlayed(context,class_id);

          //check if the user is game over otherwise proceed to answer question
         if (lifeRevive.isUserGameOver()) {
             TastyToast.makeText(context,"Please wait for the time before you can play again.\ngoto home screen to see the time left",TastyToast.LENGTH_LONG,TastyToast.ERROR);
         } else {
             new RedirectHelper(context, AnswerQuestion.class);
         }


    }
}
