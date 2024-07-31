package com.example.numad22fa_team37.GroupProject.Behavioral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.numad22fa_team37.GroupProject.Models.BehavioralQuestion;
import com.example.numad22fa_team37.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BehavioralQuestionsListActivity extends AppCompatActivity implements OnItemClickListener {

    private BehavioralQuestionsListAdapter behavioralQuestionsListAdapter;
    private List<BehavioralQuestion> behavioralList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavioral_question_list);
        setTitle("Behavioral Questions List");
        RecyclerView behavioralQuestionLstRecyclerView = findViewById(R.id.behavioralQuestionLstRecyclerView);
        behavioralQuestionLstRecyclerView.setHasFixedSize(true);
        behavioralQuestionLstRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mBehavioralList = mDatabase.child("behavioral-list");
        behavioralList = new ArrayList<>();
        behavioralQuestionsListAdapter = new BehavioralQuestionsListAdapter(behavioralList, this, this);
        behavioralQuestionLstRecyclerView.setAdapter(behavioralQuestionsListAdapter);

        mBehavioralList.addValueEventListener(
                new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        behavioralList.clear();
                        handleBehavioralQuestionList(dataSnapshot.getChildren());
                        behavioralQuestionsListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(BehavioralQuestionsListActivity.this, "Something went wrong go back and try again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleBehavioralQuestionList(Iterable<DataSnapshot> questions) {
        //iterate through each question, ignoring their UID
        questions.forEach(question -> {
            BehavioralQuestion behavioralQuestion = question.getValue(BehavioralQuestion.class);
            if (behavioralQuestion != null) {
                behavioralList.add(0, behavioralQuestion);
            }
        });
    }

    @Override
    public void onItemClick(View view, Integer questionId) {
        BehavioralQuestion behavioralQuestion = behavioralList.get(questionId);
        Intent intent = new Intent(this, BehavioralQuestionActivity.class);
        intent.putExtra("QUESTION_ID", behavioralQuestion.getId() + "");
        intent.putExtra("QUESTION_TEXT", behavioralQuestion.getQuestion());
        intent.putExtra("ANSWER", behavioralQuestion.getSample());
        startActivity(intent);
    }
}