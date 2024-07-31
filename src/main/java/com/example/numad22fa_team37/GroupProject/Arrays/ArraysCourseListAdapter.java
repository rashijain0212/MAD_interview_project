package com.example.numad22fa_team37.GroupProject.Arrays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.GroupProject.Models.TechnicalCourse;
import com.example.numad22fa_team37.R;

import java.util.List;

public class ArraysCourseListAdapter extends RecyclerView.Adapter<ArraysCourseListAdapter.RecyclerViewHolder> {

    public final List<TechnicalCourse> courseLst;
    public final Context context;
    public final OnItemClickListener onItemClickListener;

    public ArraysCourseListAdapter(List<TechnicalCourse> courseLst, Context context, OnItemClickListener onItemClickListener) {
        this.courseLst = courseLst;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.arrays_course_card, parent, false);
        return new RecyclerViewHolder(view, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        TechnicalCourse question = courseLst.get(position);
        holder.bindThisData(question, position, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return courseLst.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView courseContent,courseName;
        View itemView;

        public RecyclerViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.itemView = itemView;
            courseContent = itemView.findViewById(R.id.courseTextView);
//            courseName= itemView.findViewById(R.id.TechnicalCourseTextView);
        }

        public void bindThisData(TechnicalCourse course, Integer position, OnItemClickListener onItemClickListener) {
            courseContent.setText(course.getCourse());
//            courseName.setText(course.getCourse());
            this.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(view, position));
        }
    }
}
