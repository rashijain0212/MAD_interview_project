package com.example.numad22fa_team37.GroupProject.Quizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.numad22fa_team37.GroupProject.Courses.CourseScreenActivity;
import com.example.numad22fa_team37.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuizCategory extends AppCompatActivity {

    RecyclerView quizCatRv;
    quizCategoryAdapter quizCatAdapter;
    ArrayList<String> quizCatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_category);
        setTitle("Quiz Categories");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mQuizList = mDatabase.child("Technical");

        quizCatList = new ArrayList<>();
//        populateDummyData();

        quizCatRv = findViewById(R.id.quizCatRv);

        setUpViews();

        mQuizList.addValueEventListener(
                new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        quizCatList.clear();
                        handleQuizCategoryList(dataSnapshot.getChildren());
                        quizCatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(QuizCategory.this, "Something went wrong go back and try again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleQuizCategoryList(Iterable<DataSnapshot> quizCats) {
        quizCats.forEach(question -> {
            String quizCatString = question.getKey();
            if (quizCatString != null) {
                quizCatList.add(0, quizCatString);
            }
            Log.d("Test", question.getValue(quizCategoryModel.class).toString());
        });
    }

//    private void populateDummyData() {
//        quizCatList.add(new quizCategoryModel("C programming"));
//        quizCatList.add(new quizCategoryModel("C++ programming"));
//        quizCatList.add(new quizCategoryModel("Java programming"));
//        quizCatList.add(new quizCategoryModel("Python programming"));
//    }

    private void setUpViews() {
        quizCatRv.setHasFixedSize(true);
        quizCatRv.setLayoutManager(new LinearLayoutManager(this));
        quizCatAdapter = (new quizCategoryAdapter(quizCatList, this));
        quizCatRv.setAdapter(quizCatAdapter);
    }

//    @Override
//    public void onItemClick(View view, Integer questionId) {
//        quizCategoryModel qco = quizCatList.get(questionId);
//        Intent intent = new Intent(this, TechQuiz.class);
//        intent.putExtra("Title", qco.getTitle());
//        startActivity(intent);
//    }


}