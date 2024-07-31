package com.example.numad22fa_team37.FirebaseAssignment.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.numad22fa_team37.FirebaseAssignment.HomeScreen.HomeScreenActivity;
import com.example.numad22fa_team37.FirebaseAssignment.Models.User;
import com.example.numad22fa_team37.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private DatabaseReference mDatabase;
    private DatabaseReference mUsers;

    private static String CLIENT_REGISTRATION_TOKEN;
    private static final String SHARED_PREF_NAME = "username";
    private static final String KEY_NAME = "key_username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        etUsername = findViewById(R.id.et_username);
        Button loginBtn = findViewById(R.id.btn_login);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        generateToken();

        loginBtn.setOnClickListener(view -> {
            String name = etUsername.getText().toString();
            if (name.isEmpty()) {
                etUsername.setError("enter name");
            } else {
                User newUser = new User(name, CLIENT_REGISTRATION_TOKEN);
                //get user reference
                mUsers = mDatabase.child("users");
                //add user to db
                mUsers.child(name).setValue(newUser, (error, ref) -> {
                    if (error != null) {
                        Toast.makeText(view.getContext(), "Failed to login, try again!", Toast.LENGTH_LONG).show();
                    } else {
                        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                        sp.edit().putString(KEY_NAME, name).apply();
                        Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void generateToken() {
        // Generate the token for the first time, then no need to do later
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, "Something is wrong!", Toast.LENGTH_SHORT).show();
            } else {
                if (CLIENT_REGISTRATION_TOKEN == null) {
                    CLIENT_REGISTRATION_TOKEN = task.getResult();
                }
                Log.e("CLIENT_REGISTRATION_TOKEN", CLIENT_REGISTRATION_TOKEN);
            }
        });
    }
}
