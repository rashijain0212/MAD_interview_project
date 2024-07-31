package com.example.numad22fa_team37.GroupProject.WebDev;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class WebCourseListActivity extends AppCompatActivity implements OnItemClickListener {

    private WebCourseListAdapter courseListAdapter;
    private List<TechnicalCourse> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_dev_main);
        setTitle("Technical Course List");
        RecyclerView technicalCourseLstRecyclerView = findViewById(R.id.coursesRecyclerView);
        technicalCourseLstRecyclerView.setHasFixedSize(true);
        technicalCourseLstRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mCourseList = mDatabase.child("webdev");
        courseList = new ArrayList<>();
        courseListAdapter = new WebCourseListAdapter(courseList, this, this::onItemClick);
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
                        Toast.makeText(WebCourseListActivity.this, "Something went wrong go back and try again!", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, WebTechnicalCourseActivity.class);
        intent.putExtra("course_ID", technicalCourse.getId() + "");
        intent.putExtra("course_name", technicalCourse.getCourse());
        intent.putExtra("course_content", technicalCourse.getContent());
        startActivity(intent);
    }
}