package com.roodac.slide_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG  = LoginActivity.class.getSimpleName();
    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText    = findViewById(R.id.emailField);
        passwordEditText = findViewById(R.id.passwordField);
    }

    public void login(View view) {
        Map<String, EditText> signInInfo = new HashMap<>();
        signInInfo.put("email", emailEditText);
        signInInfo.put("password", passwordEditText);

        if (TextFieldParser.validate(signInInfo)) {
            Log.d("Log", "HELLOOOOOOO");
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            Log.d(LOG_TAG, "Logged in successfully!");
        }
    }
}
