package com.example.forest.quickguess.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forest.quickguess.Helpers.SharedPreferenceHelper;
import com.example.forest.quickguess.R;
import com.example.forest.quickguess.RecyclerView.Questions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FunFactsAdapter extends RecyclerView.Adapter<FunFactsAdapter.ViewHolder>{
    private static List<Questions> questionsItems;
    private Context context;

    public FunFactsAdapter(List<Questions> questionsItems,Context context) {
        FunFactsAdapter.questionsItems = questionsItems;
        this.context = context;
     }

    @Override
    public FunFactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fun_facts_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FunFactsAdapter.ViewHolder holder, final int position) {
        SharedPreferenceHelper.PREF_FILE="user_played";
        String category = SharedPreferenceHelper.getSharedPreferenceString(context,"category",null);
        final Questions questionsItem = questionsItems.get(position);
        initFont(holder);
        holder.txtTitle.setText(questionsItem.getTitle());
        holder.txtQuestion.setText(questionsItem.getQuestion_fun_facts());
        Picasso.with(context)
                .load("https://res.cloudinary.com/dpcxcsdiw/image/upload/w_200,h_200,q_auto,fl_lossy/"+category.toLowerCase()+"/"+questionsItem.getImage())
                .placeholder(R.drawable.placeholder)
                .into(holder.funfactsImage);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, questionsItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFont(ViewHolder holder) {
        holder.txtTitle.setTypeface(Typeface.createFromAsset(context.getAssets(),  "fonts/Dimbo_Regular.ttf"));
        holder.txtQuestion.setTypeface(Typeface.createFromAsset(context.getAssets(),  "fonts/Dimbo_Regular.ttf"));
    }

    @Override
    public int getItemCount() {
        return questionsItems.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion;
        TextView txtTitle;
        ImageView funfactsImage;
        LinearLayout parent;

        ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.questionAnswer);
            txtQuestion = itemView.findViewById(R.id.questionFunFacts);
            funfactsImage = itemView.findViewById(R.id.funfactsImage);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}

