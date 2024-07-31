package com.example.numad22fa_team37.GroupProject.Quizz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.numad22fa_team37.GroupProject.HomeScreen;
import com.example.numad22fa_team37.R;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    ArrayList<Qa> qaListT;
    TextView quizScore;
    int totalScore = 0;
    String categoryClicked;
    Button returnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        returnHome = findViewById(R.id.returnBtn);

        qaListT = (ArrayList<Qa>) getIntent().getSerializableExtra(TechQuiz.QUESTIONS_KEY);
        categoryClicked = getIntent().getStringExtra("CATEGORY_CLICKED");

        quizScore = findViewById(R.id.quizScore);

        calculateScore();

        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), QuizCategory.class);
                startActivity(i);
            }
        });
    }

    private void calculateScore() {
        int score = 0;
        for (Qa q : qaListT) {
            if (q.getAnswer().equals(q.getChosenAnswer())) {
                score += 10;
            }
        }
        totalScore = score;
        quizScore.setText("" + score);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ResultActivity.this, HomeScreen.class));
        finish();

    }
}