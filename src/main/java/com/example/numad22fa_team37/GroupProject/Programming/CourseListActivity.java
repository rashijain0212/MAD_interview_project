package com.example.numad22fa_team37.GroupProject.Programming;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.GroupProject.Models.TechnicalCourse;
import com.example.numad22fa_team37.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseListActivity extends AppCompatActivity implements OnItemClickListener {

    private CourseListAdapter courseListAdapter;
    private List<TechnicalCourse> courseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_main);
        setTitle("Technical Course List");

        RecyclerView technicalCourseLstRecyclerView = findViewById(R.id.coursesRecyclerView);
        technicalCourseLstRecyclerView.setHasFixedSize(true);
        technicalCourseLstRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mCourseList = mDatabase.child("technical-list");
        courseList = new ArrayList<>();
        courseListAdapter = new CourseListAdapter(courseList, this, this::onItemClick);
        technicalCourseLstRecyclerView.setAdapter(courseListAdapter);

        mCourseList.addValueEventListener(
                new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        courseList.clear();
                        handleTechnicalCourseList(dataSnapshot.getChildren());
                        courseListAdapter.notifyDataSetChanged();
                    }
//i guess
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(CourseListActivity.this, "Something went wrong go back and try again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleTechnicalCourseList(Iterable<DataSnapshot> courses) {
        //iterate through each question, ignoring their UID
        courses.forEach(course -> {
            TechnicalCourse technicalCourse = course.getValue(TechnicalCourse.class);
            if (technicalCourse != null) {
                courseList.add(0, technicalCourse);
            }
        });
    }

    @Override
    public void onItemClick(View view, Integer courseId) {
        TechnicalCourse technicalCourse = courseList.get(courseId);
        Intent intent = new Intent(this, TechnicalCourseActivity.class);
        intent.putExtra("course_ID", technicalCourse.getId() + "");
        intent.putExtra("course_name", technicalCourse.getCourse());
        intent.putExtra("course_content", technicalCourse.getContent());
        startActivity(intent);
    }
}