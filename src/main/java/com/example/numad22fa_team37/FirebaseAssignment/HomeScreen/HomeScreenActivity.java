package com.example.numad22fa_team37.FirebaseAssignment.HomeScreen;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.FirebaseAssignment.Models.User;
import com.example.numad22fa_team37.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenActivity extends AppCompatActivity {
    private final String Tag = "FirebaseAssignmentActivity";
    private String username;
    private List<User> usersLst;
    private UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        setTitle("Users");
        Log.v(Tag, "onCreate Called");
        username = getSharedPreferences("username", MODE_PRIVATE).getString("key_username", null);
        usersLst = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerMovieView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference mUsers = mDatabase.child("users");

        mAdapter = new UserAdapter(usersLst, this, this);
        recyclerView.setAdapter(mAdapter);
        mUsers.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usersLst.clear();
                        handleUsers(dataSnapshot.getChildren());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(HomeScreenActivity.this, "Something went wrong try login again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public String getCurrentUser() {
        return this.username;
    }

    private void handleUsers(Iterable<DataSnapshot> users) {
        //iterate through each user, ignoring their UID
        users.forEach(user -> {
            User newUser = user.getValue(User.class);
            if (newUser != null && !newUser.getUsername().equals(username)) {
                usersLst.add(0, newUser);
            }
        });
    }
}