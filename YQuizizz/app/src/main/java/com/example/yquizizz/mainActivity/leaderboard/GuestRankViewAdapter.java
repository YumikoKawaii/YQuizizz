package com.example.yquizizz.mainActivity.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yquizizz.R;

import java.util.ArrayList;

public class GuestRankViewAdapter extends RecyclerView.Adapter<GuestRankViewAdapter.ViewHolder> {

    private ArrayList<Guest> guestList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_rank_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.guestName.setText(guestList.get(position).getUsername());
        holder.guestRank.setText(guestList.get(position).getUserRank().toString());
        holder.guestExp.setText(guestList.get(position).getUserTotalExp().toString());
        holder.guestLevel.setText(guestList.get(position).getUserLevel().toString());
    }

    @Override
    public int getItemCount() {
        return guestList.size();
    }

    public void setGuestList(ArrayList<Guest> guestList) {
        this.guestList = guestList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView guestName;
        private TextView guestRank;
        private TextView guestExp;
        private TextView guestLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            guestName = (TextView) itemView.findViewById(R.id.guestName);
            guestRank = (TextView) itemView.findViewById(R.id.guestRank);
            guestExp = (TextView) itemView.findViewById(R.id.guestExp);
            guestLevel = (TextView) itemView.findViewById(R.id.guestLevel);
        }

    }

}
