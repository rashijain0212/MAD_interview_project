package com.example.numad22fa_team37.GroupProject.Arrays;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.numad22fa_team37.R;


public class ArraysTechnicalCourseActivity extends AppCompatActivity {
    public String username;
    public String courseContent;
    public String courseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrays_content);
        setTitle("Technical Courses");

        // Setting the text views
        username = getIntent().getStringExtra("USERNAME");
        String courseName = getIntent().getStringExtra("course_name");
        courseContent = getIntent().getStringExtra("course_content");
        courseId = getIntent().getStringExtra("course_ID");

        TextView courseTextView = findViewById(R.id.courseTextView);
        TextView courseNameView = findViewById(R.id.technicalTopicTextView);
        courseTextView.setText(courseContent);
        courseNameView.setText(courseName);

    }

//    public String getCourseId() {
//        return courseId;
//    }
}