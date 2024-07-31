package com.example.numad22fa_team37.GroupProject.Courses;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.GroupProject.Arrays.ArraysCourseListActivity;
import com.example.numad22fa_team37.GroupProject.HomeScreen;
import com.example.numad22fa_team37.GroupProject.Programming.CourseListActivity;
import com.example.numad22fa_team37.GroupProject.Quizz.QuizCategory;
import com.example.numad22fa_team37.GroupProject.WebDev.WebCourseListActivity;
import com.example.numad22fa_team37.GroupProject.profile.ProfileMainActivity;
import com.example.numad22fa_team37.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CourseScreenActivity extends AppCompatActivity implements OnItemClickListener {
    private String userTo;
    private String userFrom;
    private final static String SERVER_KEY = "key=AAAA0brce0U:APA91bFcc08xzfxdjsrvtwElK56E6fuT_42VG3bVfr6KqNHcWezA5FBUWxNiMh8TbByuU2Ou5ArFRo0zT-1wsT7oGEHxpaPgZ1fKijQ5rtc2KDw25nLveTcVHKiavoy73xCO6wxbiFBx";
    private DatabaseReference messages;
    private DatabaseReference mDatabase;
    private ArrayList<Integer> mImageIds;
    private Button quizzbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_course);
        setTitle("Courses");
        mImageIds = CourseList.getCourseList();
        quizzbtn=findViewById(R.id.quizz);
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userTo = this.getIntent().getStringExtra("to");
        userFrom = this.getIntent().getStringExtra("from");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        messages = mDatabase.child("technical-list");
        CourseAdapter mAdapter = new CourseAdapter(mImageIds, this, this);
        recyclerView.setAdapter(mAdapter);

        quizzbtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, QuizCategory.class);
            startActivity(intent);
        });


    }

    @Override
    public void onItemClick(View view, Integer courseId) {
//categories
        String c_programming = "c";
        String web_dev = "web_dev";
        String arrays = "data_structures";
        String network="network";
        String algorithm="algo";
        int cId = getResources().getIdentifier(c_programming , "drawable", getPackageName());
        int webId = getResources().getIdentifier(web_dev , "drawable", getPackageName());
        int arrayId = getResources().getIdentifier(arrays , "drawable", getPackageName());
        int algoId = getResources().getIdentifier(algorithm , "drawable", getPackageName());
        int netId = getResources().getIdentifier(network , "drawable", getPackageName());

        if (cId == courseId){
            Intent intent = new Intent(this, CourseListActivity.class);
            startActivity(intent);
        }
        if (webId == courseId){
            //Toast.makeText(CourseScreenActivity.this, courseId, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, WebCourseListActivity.class);
            startActivity(intent);
        }
        if (arrayId == courseId){
           // Toast.makeText(CourseScreenActivity.this, courseId, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ArraysCourseListActivity.class);
            startActivity(intent);
        }

        if (algoId == courseId){
             Toast.makeText(CourseScreenActivity.this, "Data has not been updated Please refer to first three categories", Toast.LENGTH_SHORT).show();
        }

        if (netId == courseId){
            Toast.makeText(CourseScreenActivity.this, "Data has not been updated Please refer to first three categories", Toast.LENGTH_SHORT).show();
        }
 }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageIds.clear();
    }
}