package com.example.forest.quickguessv2.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.forest.quickguessv2.AnswerQuestion;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.Helpers.RedirectHelper;
import com.example.forest.quickguessv2.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguessv2.R;

public class SlideAdapter extends PagerAdapter implements View.OnClickListener {
    private Context context;
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




    public SlideAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);
        RelativeLayout layoutslide = view.findViewById(R.id.slidelinearlayout);
        ImageView pageBackground = view.findViewById(R.id.pageBackground);
        TextView description = view.findViewById(R.id.txtdescription);
        layoutslide.setTag(lst_title[position]);
        layoutslide.setOnClickListener(this);
        pageBackground.setImageResource(context.getResources().getIdentifier(list_background[position],"drawable",context.getPackageName()));
        description.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(),  "fonts/Dimbo_Regular.ttf"));
        description.setText(R.string.tap_to_start);
        YoYo.with(Techniques.DropOut)
                .duration(800)
                .delay(500)
                .repeat(-1)
                .playOn(description);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
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
          String background = (String) view.getTag();
          SharedPreferenceHelper.PREF_FILE="user_played";
          SharedPreferenceHelper.setSharedPreferenceString(context,"category",background);
//          new RedirectHelper("bg_".concat(background).toLowerCase(), view.getContext(), AnswerQuestion.class);
            int category_id = DB.getInstance(context).categoriesQuestionDao().getCategoryIdByName(background.toLowerCase());
            int no_of_question = DB.getInstance(context).questionsDao().countQuestionByCategory(category_id);
            Toast.makeText(context, ""+String.valueOf(no_of_question), Toast.LENGTH_LONG).show();
          new RedirectHelper(view.getContext(), AnswerQuestion.class);
    }


}
