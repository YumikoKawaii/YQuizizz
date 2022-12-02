package com.example.yquizizz.challenge;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yquizizz.R;

import java.util.ArrayList;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ViewHolder> {

    private ArrayList<Challenge> challengeList = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.system_challenge, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.topicName.setText(challengeList.get(position).getTopic());
            holder.topicExp.setText(challengeList.get(position).getExp().toString());
            holder.constraintLayout.setBackground(getBackgroundBaseOnTopic(challengeList.get(position).getTopic()));
            holder.topicPicture.setImageResource(getIconBaseOnTopic(challengeList.get(position).getTopic()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }

    public void setChallengeList(ArrayList<Challenge> challengeList) {
        this.challengeList = challengeList;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Drawable getBackgroundBaseOnTopic(String topic) {
        switch (topic) {
            case "Computer Science":
                return AppCompatResources.getDrawable(context, R.drawable.computer_science_bg);
            case "Mathematics":
                return AppCompatResources.getDrawable(context, R.drawable.mathematics_bg);
            case "Physics":
                return AppCompatResources.getDrawable(context, R.drawable.physics_bg);
            case "Chemistry":
                return AppCompatResources.getDrawable(context, R.drawable.chemistry_bg);
            case "History":
                return AppCompatResources.getDrawable(context, R.drawable.history_bg);
            case "Geography":
                return AppCompatResources.getDrawable(context, R.drawable.geography_bg);
            case "Art":
                return AppCompatResources.getDrawable(context, R.drawable.art_bg);
            case "Electrical Engineering":
                return AppCompatResources.getDrawable(context, R.drawable.electrical_engineering_bg);
            case "Robotics":
                return AppCompatResources.getDrawable(context, R.drawable.robotics_bg);
            case "Biology":
                return AppCompatResources.getDrawable(context, R.drawable.biology_bg);
            case "Literature":
                return AppCompatResources.getDrawable(context, R.drawable.literature_bg);
            default:
                return AppCompatResources.getDrawable(context, R.drawable.computer_science_bg);
        }
    }

    private int getIconBaseOnTopic(String topic) {
        switch (topic) {
            case "Computer Science":
                return R.mipmap.computer_science;
            case "Mathematics":
                return R.mipmap.mathematics;
            case "Physics":
                return R.mipmap.physics;
            case "Chemistry":
                return R.mipmap.chemistry;
            case "History":
                return R.mipmap.history_book;
            case "Geography":
                return R.mipmap.geography;
            case "Art":
                return R.mipmap.art;
            case "Electrical Engineering":
                return R.mipmap.electrical_engineering;
            case "Robotics":
                return R.mipmap.robotics;
            case "Biology":
                return R.mipmap.biology;
            case "Literature":
                return R.mipmap.literature;
            default:
                return R.mipmap.madoka;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView topicName;
        private TextView topicExp;
        private ImageView topicPicture;
        private ConstraintLayout constraintLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topicName = (TextView) itemView.findViewById(R.id.systemChallengeTopicName);
            topicExp = (TextView) itemView.findViewById(R.id.systemChallengeExp);
            topicPicture = (ImageView) itemView.findViewById(R.id.systemChallengeTopicPicture);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.systemChallengeLayout);
        }
    }
}
