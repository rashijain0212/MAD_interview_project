package com.example.numad22fa_team37.GroupProject.Behavioral;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import com.example.numad22fa_team37.R;

import java.io.File;


public class BehavioralQuestionActivity extends AppCompatActivity {
    private String username;
    private String sampleAnswer;
    private String questionId;
    private File[] audioFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavioral);
        setTitle("Behavioral Question");

        // Setting the text views
        username = getIntent().getStringExtra("USERNAME");
        String questionText = getIntent().getStringExtra("QUESTION_TEXT");
        sampleAnswer = getIntent().getStringExtra("ANSWER");
        questionId = getIntent().getStringExtra("QUESTION_ID");

        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(questionText);
        Button sampleAnswerButton = findViewById(R.id.sampleAnswerButton);

        sampleAnswerButton.setOnClickListener(v -> {
            String audioFilePath = getExternalFilesDir("/").getAbsolutePath();
            File audioDirectory = new File(audioFilePath + "/" + this.questionId);
            audioFiles = audioDirectory.listFiles();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sample answer")
                    .setMessage(sampleAnswer)
                    .setPositiveButton("Helpful", (dialog, which) -> {

                    }).create();
            builder.show();
        });
    }

    public String getQuestionId() {
        return questionId;
    }
}