package com.example.numad22fa_team37.GroupProject.profile;

import androidx.appcompat.app.AppCompatActivity;

import com.example.numad22fa_team37.GroupProject.Behavioral.BehavioralQuestionsListActivity;
import com.example.numad22fa_team37.GroupProject.Courses.CourseScreenActivity;
import com.example.numad22fa_team37.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileMainActivity extends AppCompatActivity {
    Button progress;
    TextView setup_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        setTitle("Profile");
        progress = findViewById(R.id.setup_btn);
        setup_name = findViewById(R.id.setup_name);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            setup_name.setText(user.getEmail());
        }
        progress.setOnClickListener(view -> {
            //Intent intent = new Intent(this, BehavioralQuestionsListActivity.class);
            // startActivity(intent);
            Toast.makeText(ProfileMainActivity.this, "Future scope. Coming up soon!!", Toast.LENGTH_SHORT).show();
        });
    }
}