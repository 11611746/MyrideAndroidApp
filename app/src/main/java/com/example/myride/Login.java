package com.example.myride;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText lEmail, lPassword;
    private Button lButton;
    private TextView lLink;
    private ProgressBar progressBar;

    FirebaseAuth  firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lEmail = findViewById(R.id.email_L);
        lPassword = findViewById(R.id.password_L);
        lButton = findViewById(R.id.button_L);
        lLink = findViewById(R.id.link_L);
        progressBar = findViewById(R.id.progress_L);

        firebaseAuth = FirebaseAuth.getInstance();

        lButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lEmail.getText().toString().trim();
                String password = lPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    lEmail.setError("Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    lPassword.setError("Required");
                    return;
                }

                if (password.length() < 6){
                    lPassword.setError("Must be 6 Character or More");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                            finish();
                        }else {
                            Toast.makeText(Login.this, ""+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });

        lLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

    }
}
