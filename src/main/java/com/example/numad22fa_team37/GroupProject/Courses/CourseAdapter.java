package com.example.numad22fa_team37.GroupProject.Courses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.R;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder> {

    private final ArrayList<Integer> courseList;
    private final Context context;
    private final OnItemClickListener onItemClickListener;

    public CourseAdapter(ArrayList<Integer> courseList, Context context, OnItemClickListener onItemClickListener) {
        this.courseList = courseList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_topic_list, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Integer courseId = courseList.get(position);
        holder.bindThisData(courseId, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }
}
