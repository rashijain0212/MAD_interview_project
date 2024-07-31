package com.example.numad22fa_team37.GroupProject.Behavioral;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.AtYourService.RecyclerAdapter;
import com.example.numad22fa_team37.FirebaseAssignment.HistoryScreen.HistoryAdapter;
import com.example.numad22fa_team37.GroupProject.Models.BehavioralQuestion;
import com.example.numad22fa_team37.R;

import java.io.File;
import java.util.List;

public class BehavioralQuestionsListAdapter extends RecyclerView.Adapter<BehavioralQuestionsListAdapter.RecyclerViewHolder> {

    private final List<BehavioralQuestion> questionsLst;
    private final Context context;
    private final OnItemClickListener onItemClickListener;

    public BehavioralQuestionsListAdapter(List<BehavioralQuestion> questionsLst, Context context, OnItemClickListener onItemClickListener) {
        this.questionsLst = questionsLst;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.behavioral_question_card, parent, false);
        return new BehavioralQuestionsListAdapter.RecyclerViewHolder(view, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        BehavioralQuestion question = questionsLst.get(position);
        holder.bindThisData(question, position, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return questionsLst.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView questionTV;
        View itemView;

        public RecyclerViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.itemView = itemView;
            questionTV = itemView.findViewById(R.id.questionTextView);
        }

        public void bindThisData(BehavioralQuestion question, Integer position, OnItemClickListener onItemClickListener) {
            questionTV.setText(question.getQuestion());
            this.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(view, position));
        }
    }
}
