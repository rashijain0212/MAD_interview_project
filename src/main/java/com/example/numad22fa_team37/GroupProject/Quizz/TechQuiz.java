package com.example.numad22fa_team37.GroupProject.Quizz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.GroupProject.HomeScreen;
import com.example.numad22fa_team37.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TechQuiz extends AppCompatActivity {

    RecyclerView optionsRecyclerView;
    TextView questionText;
    Button previousBtn, nextBtn, submitBtn;
    ArrayList<Qa> qaList;
    TechQuizAdapter techQuizAdapter;
    Qa question;
    int index = 0;
    final static public String QUESTIONS_KEY = "QUESTIONS";
    final static public String CATEGORY_CLICKED = "CATEGORY_CLICKED";
    String categoryClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_quiz);
        setTitle("Quiz");
        categoryClicked = getIntent().getStringExtra("Category_String");

//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference mQuizQuestions = mDatabase.child("Technical").child(categoryClicked);

        previousBtn = findViewById(R.id.previousBtn);
        nextBtn = findViewById(R.id.nextBtn);
        submitBtn = findViewById(R.id.submitBtn);

        qaList = new ArrayList<>();

        qaList.add(new Qa("Javascript is an _______ language?", "Object-oriented", "object-based","procedural","none of the above","Object-oriented"));
        qaList.add(new Qa("Which of the following keywords is used to define a variable in Javascript?", "var", "let","A & B","none of the above","A & B"));
        qaList.add(new Qa("How can a datatype be declared to be a constant type?", "const", "var","let","constant","const"));
        qaList.add(new Qa("The % operator returns the ___.", "Quotient", "Divisor","Remainder","None of the mentioned above","Remainder"));
        qaList.add(new Qa("Python Dictionary is used to store the data in a ___ format.", "Key value pair", "Group value pair","Select value pair","None of the mentioned above","Key value pair"));
        qaList.add(new Qa("What is the return type of the hashCode() method in the Object class?", "Object", "int","long","void","int"));
        qaList.add(new Qa("In which process, a local variable has the same name as one of the instance variables?", "Serialization", "Variable Shadowing","Abstraction","Multi-threading","Variable Shadowing"));
        qaList.add(new Qa("Which of the following is a reserved keyword in Java?", "object", "strictfp","main","system","strictfp"));
        qaList.add(new Qa("Which of the following is the correct syntax for declaring the array?", "init array []", "int array [5];","Array[5];","None of the above","int array [5];"));
        qaList.add(new Qa("Which one of the following is a loop construct that will always be executed once?", "for", "while","switch","do while","do while"));

        bindData();
        setUpEventListener();
    }

    private void setUpEventListener() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                bindData();
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                bindData();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResultActivity.class);
                intent.putExtra(QUESTIONS_KEY, qaList);
                intent.putExtra(CATEGORY_CLICKED, categoryClicked);
                startActivity(intent);
            }
        });
    }

    public void bindData() {
        previousBtn.setVisibility(View.GONE);
        nextBtn.setVisibility(View.GONE);
        submitBtn.setVisibility(View.GONE);

        if(index == 0) { //first-question
            nextBtn.setVisibility(View.VISIBLE);
        }
        else if (index == qaList.size() - 1) { //last-question
            previousBtn.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.VISIBLE);
        }
        else { //middle-questions
            previousBtn.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.VISIBLE);
        }

        question = qaList.get(index);

        questionText = findViewById(R.id.questionText);
        questionText.setText(question.getQuestion());

        optionsRecyclerView = findViewById(R.id.optionRecycler);

        optionsRecyclerView.setHasFixedSize(true);
        optionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        techQuizAdapter = (new TechQuizAdapter(question, this));
        optionsRecyclerView.setAdapter(techQuizAdapter);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(("Are you sure you want to exit? \nYour progress will be lost."))
                .setCancelable(false)
                .setPositiveButton(Html.fromHtml("<font color='#000000'>Yes</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TechQuiz.super.onBackPressed();
                    }
                })
                .setNegativeButton(Html.fromHtml("<font color='#000000'>No</font>"), null)
                .show();
    }
}