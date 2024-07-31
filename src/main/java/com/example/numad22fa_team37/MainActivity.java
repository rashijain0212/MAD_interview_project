package com.example.numad22fa_team37;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.numad22fa_team37.AtYourService.AtYourServiceActivity;
import com.example.numad22fa_team37.GroupProject.LoginActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    String Tag = "MainActivity";
    TextView tvTeamName;
    String[] colorList = new String[]{"#c68e21", "#39f2d7", "#930a4d", "#fdf244", "#1a6baf", "#6fdc38", "#0628b8"};
    Button btnAtYourService, btnFirebaseAssignment, btnGroupProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(Tag, "onCreate Called");

        btnAtYourService = findViewById(R.id.btn_at_your_service);
        btnFirebaseAssignment = findViewById(R.id.btn_firebase_database);
        btnGroupProject = findViewById(R.id.btn_group_project);

        tvTeamName = findViewById(R.id.tv_team_name);

        UpdateTextViewColor();

        btnAtYourService.setOnClickListener(view -> {
            Intent intent = new Intent(this, AtYourServiceActivity.class);
            startActivity(intent);
        });

        btnFirebaseAssignment.setOnClickListener(view -> {
            Intent intent = new Intent(this, com.example.numad22fa_team37.FirebaseAssignment.Login.LoginActivity.class);
            startActivity(intent);
        });

        btnGroupProject.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void UpdateTextViewColor() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> tvTeamName.setTextColor(Color.parseColor(colorList[(int) Math.floor(Math.random() * colorList.length)])), 0, 2000, TimeUnit.MILLISECONDS);
    }
}