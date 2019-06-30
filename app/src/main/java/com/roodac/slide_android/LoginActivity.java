package com.roodac.slide_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG  = LoginActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailEditText    = findViewById(R.id.emailField);
        passwordEditText = findViewById(R.id.passwordField);
    }

    // Handle when Login button is clicked.
    public void login(View view) {

        Map<String, EditText> signInInfo = new HashMap<>();
        signInInfo.put("email", emailEditText);
        signInInfo.put("password", passwordEditText);

        // Verify that user information is correct
        if (TextFieldParser.validate(signInInfo)) {
            mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(),
                    passwordEditText.getText().toString()).addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        Log.d(LOG_TAG, "Logged in successfully!");
                    }
                    else {
                        Log.w(LOG_TAG, "Failed to log in", task.getException());
                        // TODO: Toast is probably not the best error notification method as user can easily miss it.
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }
}
