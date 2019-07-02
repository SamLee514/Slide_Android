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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG  = LoginActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText emailEditText;
    EditText passwordEditText;
    EditText fullnameEditText;
    // Commented out because the Apple version doesn't have this yet
    // EditText usernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        emailEditText    = findViewById(R.id.field_email);
        passwordEditText = findViewById(R.id.field_password);
        fullnameEditText = findViewById(R.id.field_fullname);
        // Commented out because the Apple version doesn't have this yet
        // usernameEditText = findViewById(R.id.field_username);
    }


    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Map<String, EditText> signUpInfo = new HashMap<>();
        signUpInfo.put("email", emailEditText);
        signUpInfo.put("password", passwordEditText);

        // Verify that user information is correct
        if (TextFieldParser.validate(signUpInfo)) {
            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(),
                    passwordEditText.getText().toString()).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(intent);
                                Log.d(LOG_TAG, "Registered successfully!");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String uID     = user.getUid();
                                setDocInfo(uID);
                            }
                            else {
                                Log.w(LOG_TAG, "Failed to register", task.getException());
                                Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private void setDocInfo(String UserID) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("Name", fullnameEditText.getText().toString());
        fields.put("ID", UserID);
        fields.put("Email", emailEditText.getText().toString().trim());
        // Commented out because the Apple version doesn't have this yet
        // fields.put("Username", usernameEditText.getText().toString());

        db.collection("users").document(UserID)
                .set(fields)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(LOG_TAG, "Successfully wrote user data!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(LOG_TAG, "Error writing user data", e);
                    }
                });

    }
}
