package com.example.numad22fa_team37.GroupProject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.numad22fa_team37.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.signUpEmail);
        etPassword = findViewById(R.id.tiePassword);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(view -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            if (!validateInputs(email, password)) return;
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            btnRegister.setEnabled(false);
//                            String id = mAuth.getCurrentUser().getUid();
//                            mAuth = FirebaseAuth.getInstance();
//                            databaseUser = FirebaseDatabase.getInstance().getReference("user_data");
//                            databaseUser = FirebaseDatabase.getInstance().getReference("user_data");
//                            DatabaseReference mRef = databaseUser.child(id);
//                            User user_db = new User(fullName, mid, email, id, "default", "offline", Degree, ClinicNo, cat, verified);
//                            mRef.setValue(user_db);
                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            Intent intent;
                            intent = new Intent(this, HomeScreen.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.email_cannot_empty));
//            Toast.makeText(this, getString(R.string.email_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isValidEmail(email)) {
            etEmail.setError(getString(R.string.email_not_valid));
//            Toast.makeText(this, getString(R.string.email_not_valid), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.password_cannot_empty));
//            Toast.makeText(this, getString(R.string.password_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            etPassword.setError(getString(R.string.password_is_short));
//            Toast.makeText(this, getString(R.string.password_cannot_empty), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}