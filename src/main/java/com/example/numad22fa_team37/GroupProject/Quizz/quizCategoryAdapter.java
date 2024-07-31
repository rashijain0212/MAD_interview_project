package com.example.numad22fa_team37.GroupProject.Quizz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.GroupProject.Behavioral.OnItemClickListener;
import com.example.numad22fa_team37.R;

import java.util.ArrayList;

public class quizCategoryAdapter extends RecyclerView.Adapter<quizCategoryAdapter.quizCategoryHolder>{

    Context context;
    ArrayList<String> quizCatList;
    OnItemClickListener onItemClickListener;

    public quizCategoryAdapter(ArrayList<String> quizCatList, Context context) {
        this.quizCatList = quizCatList;
        this.context = context;
//        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public quizCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.quiz_cat_item, parent, false);
        return new quizCategoryAdapter.quizCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull quizCategoryHolder holder, int position) {
        String quizCatString = quizCatList.get(position);
        holder.quizCatTitle.setText(quizCatString);
        holder.quizCatTitle.setOnClickListener(v -> {
            Intent quizPage = new Intent(v.getContext(), TechQuiz.class);
            quizPage.putExtra("Category_String", quizCatString);
            context.startActivity(quizPage);
        });
    }

    @Override
    public int getItemCount() {
        return quizCatList.size();
    }

    public class quizCategoryHolder extends RecyclerView.ViewHolder {
        TextView quizCatTitle;

        public quizCategoryHolder(@NonNull View itemView) {
            super(itemView);

            quizCatTitle = itemView.findViewById(R.id.quizCatTitle);
        }
    }
}
