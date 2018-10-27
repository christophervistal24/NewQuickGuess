package com.example.forest.quickguess.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguess.AnswerQuestion;
import com.example.forest.quickguess.DB.Life.LifeRepositories;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.Helpers.RedirectHelper;
import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.R;
import com.example.forest.quickguess.RecyclerView.Categories;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>  implements View.OnClickListener{
    private static List<Categories> categoryItems;
    private LifeRepositories lifeRepositories;
    private Context context;

    private String[] lst_title = {

            "PEOPLE","PLANTS","ANIMALS","GEOGRAPHY", "SPORTS","MUSIC","TECHNOLOGY","ENTERTAINMENT"
    };

    private String[] difficulty_level_id = {
            "1",
            "2",
            "3"
    };

    public CategoryAdapter(List<Categories> categoryItems, Context context) {
        CategoryAdapter.categoryItems = categoryItems;
        this.context = context;
        lifeRepositories = new LifeRepositories(context);
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        final Categories categoriesItem = categoryItems.get(position);
        String capitalizeCategory = categoriesItem.getCategory_name().substring(0,1).toUpperCase() + categoriesItem.getCategory_name().substring(1);
        holder.txtCategory.setText(capitalizeCategory);
        eventHandler(lst_title[position],holder.btnEasy,holder.btnModerate,holder.btnDifficult);
    }


    //add some tags and events
    private void eventHandler(String tag, Button btnEasy, Button btnModerate, Button btnDifficult) {
        btnEasy.setTag(R.id.class_id,difficulty_level_id[0]);
        btnEasy.setTag(R.id.category, tag);
        btnModerate.setTag(R.id.class_id,difficulty_level_id[1]);
        btnModerate.setTag(R.id.category, tag);
        btnDifficult.setTag(R.id.class_id,difficulty_level_id[2]);
        btnDifficult.setTag(R.id.category, tag);
        btnEasy.setOnClickListener(this);
        btnModerate.setOnClickListener(this);
        btnDifficult.setOnClickListener(this);
    }


    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    @Override
    public void onClick(View view) {
        String class_id = String.valueOf(view.getTag(R.id.class_id));
        String category = (String)view.getTag(R.id.category);
        QuestionRepositories.class_id = Integer.parseInt(class_id);
        SharedPreferenceHelper.PREF_FILE="user_played";
        SharedPreferenceHelper.setSharedPreferenceString(context,"category",category);
        if (lifeRepositories.getUserLife() <= 0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("GAME OVER");
            builder.show();
        } else {
            new RedirectHelper(view.getContext(), AnswerQuestion.class);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory;
        TextView txtDescription;
        LinearLayout categoriesLayout;
        Button btnEasy;
        Button btnModerate;
        Button btnDifficult;

        ViewHolder(View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.categoryName);
            txtDescription = itemView.findViewById(R.id.categoryDescription);
            categoriesLayout = itemView.findViewById(R.id.categoriesLayout);
            btnEasy = itemView.findViewById(R.id.btnEasy);
            btnModerate = itemView.findViewById(R.id.btnModerate);
            btnDifficult = itemView.findViewById(R.id.btnDifficult);
        }
    }
}
