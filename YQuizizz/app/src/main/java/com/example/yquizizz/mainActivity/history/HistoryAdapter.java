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
import com.example.yquizizz.database.HistoryModel;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    private ArrayList<HistoryModel> historyList = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_header_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.topicPicture.setImageResource(getPictureBaseOnTopic(historyList.get(position).getTopic()));
        holder.topic.setText(historyList.get(position).getTopic());
        holder.difficulty.setText(historyList.get(position).getDifficulty());

        Double progress = historyList.get(position).getUserPoint().doubleValue()/historyList.get(position).getTotalPoint().doubleValue()*100;

        holder.historyResultProgress.setProgress(progress.intValue());
        holder.percentCompleted.setText(String.format("%.2f", progress));

        String result = String.format("%d/%d",historyList.get(position).getNumberOfRightAnswer(), historyList.get(position).getNumberOfQuiz());
        holder.resultText.setText(result);
        holder.dateAttempt.setText(historyList.get(position).getDateAttempt());

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void setHistoryList(ArrayList<HistoryModel> list) {
        this.historyList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView topicPicture;
        private TextView topic;
        private TextView difficulty;
        private ProgressBar historyResultProgress;
        private TextView percentCompleted;
        private TextView resultText;
        private TextView dateAttempt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            topicPicture = itemView.findViewById(R.id.topicPicture);
            topic = itemView.findViewById(R.id.topicName);
            difficulty = itemView.findViewById(R.id.topicDifficulty);
            historyResultProgress = itemView.findViewById(R.id.historyResultProgress);
            percentCompleted = itemView.findViewById(R.id.percentCompleted);
            resultText = itemView.findViewById(R.id.historyResultText);
            dateAttempt = itemView.findViewById(R.id.dateAttempt);

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
