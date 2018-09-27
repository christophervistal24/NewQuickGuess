package com.example.forest.quickguessv2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.forest.quickguessv2.R;
import com.example.forest.quickguessv2.RecyclerView.Ranks;

import java.util.List;

public class RanksAdapter extends RecyclerView.Adapter<RanksAdapter.ViewHolder> {
    private static List<Ranks> rankItems;
    private Context context;

    public RanksAdapter(List<Ranks> ranksList,Context context) {
        RanksAdapter.rankItems = ranksList;
        this.context = context;
    }

    @Override
    public RanksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ranks_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RanksAdapter.ViewHolder holder, int position) {
        final Ranks rankItem = rankItems.get(position);
        holder.playerName.setText(rankItem.getUsername());
        holder.playerPoints.setText(String.valueOf(rankItem.getPoints()));
    }

    @Override
    public int getItemCount() {
        return rankItems.size();
    }
    //id ,username ,points

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        TextView playerPoints;

        ViewHolder(View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
            playerPoints = itemView.findViewById(R.id.playerPoints);
        }
    }
}
