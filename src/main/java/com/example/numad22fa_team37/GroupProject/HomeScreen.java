package com.example.numad22fa_team37.GroupProject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.numad22fa_team37.GroupProject.Behavioral.BehavioralQuestionsListActivity;
import com.example.numad22fa_team37.GroupProject.Courses.CourseScreenActivity;
import com.example.numad22fa_team37.GroupProject.Quizz.ResultActivity;
import com.example.numad22fa_team37.GroupProject.profile.ProfileMainActivity;
import com.example.numad22fa_team37.MainActivity;
import com.example.numad22fa_team37.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreen extends AppCompatActivity {
    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("The Right Prep");
        TextView tx = findViewById(R.id.welcome);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            tx.setText("Welcome, " + user.getEmail());
        } else {
            tx.setText("Welcome");
        }
        CardView technicalCardView = findViewById(R.id.technical);
        technicalCardView.setOnClickListener(view -> {
            Intent intent = new Intent(this, CourseScreenActivity.class);
            startActivity(intent);
        });


        CardView behavioralCardView = findViewById(R.id.behavioral);
        behavioralCardView.setOnClickListener(view -> {
            Intent intent = new Intent(this, BehavioralQuestionsListActivity.class);
            startActivity(intent);
        });

        CardView profileView = findViewById(R.id.profile);
        profileView.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProfileMainActivity.class);
            startActivity(intent);
        });

        CardView logoutCardView = findViewById(R.id.logout);
        logoutCardView.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            finishAffinity();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {

    }
}