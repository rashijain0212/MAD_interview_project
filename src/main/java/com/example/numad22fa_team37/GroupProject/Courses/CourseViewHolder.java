package com.example.numad22fa_team37.GroupProject.Courses;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.R;

public class CourseViewHolder extends RecyclerView.ViewHolder {
    private final ImageView image;


    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.course);
    }

    public void bindThisData(Integer courseId, OnItemClickListener onItemClickListener) {
        image.setImageResource(courseId);
        image.setOnClickListener(view -> onItemClickListener.onItemClick(view, courseId));
    }
}
