package com.example.yquizizz.mainActivity.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yquizizz.R;

import java.util.ArrayList;

public class ChallengeAttemptedDisplayAdapter extends RecyclerView.Adapter<ChallengeAttemptedDisplayAdapter.ViewHolder> {

    private ArrayList<ChallengeAttempted> challengeAttemptedList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_header_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.topicBg.setImageResource(getPictureBaseOnTopic(challengeAttemptedList.get(position).getTopic()));
        holder.topicName.setText(challengeAttemptedList.get(position).getTopic());
        holder.topicDifficulty.setText(challengeAttemptedList.get(position).getDifficulty());
        holder.historyResultProgress.setProgress(challengeAttemptedList.get(position).getResult()*10);
        String result = String.format("%d/10",challengeAttemptedList.get(position).getResult());
        holder.historyResultText.setText(result);
    }

    @Override
    public int getItemCount() {
        return challengeAttemptedList.size();
    }

    public void setChallengeAttemptedList(ArrayList<ChallengeAttempted> challengeAttemptedList) {
        this.challengeAttemptedList = challengeAttemptedList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView topicBg;
        private TextView topicName;
        private TextView topicDifficulty;
        private TextView historyResultText;
        private ProgressBar historyResultProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            topicBg = (ImageView) itemView.findViewById(R.id.topicPicture);
            topicName = (TextView) itemView.findViewById(R.id.topicName);
            topicDifficulty = (TextView) itemView.findViewById(R.id.topicDifficulty);
            historyResultText = (TextView) itemView.findViewById(R.id.historyResultText);
            historyResultProgress = (ProgressBar) itemView.findViewById(R.id.historyResultProgress);

        }
    }

    private int getPictureBaseOnTopic(String topic) {
        switch (topic) {
            case "Physics":
                return R.mipmap.physic_landscape;
            case "Chemistry":
                return R.mipmap.chemistry_landscape;
            case "History":
                return R.mipmap.history_landscape;
            case "Geography":
                return R.mipmap.geography_landscape;
            case "Art":
                return R.mipmap.art_landscape;
            default:
                return R.mipmap.general_knowledge_landscape;

        }
    }

}
