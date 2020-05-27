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

public class Register extends AppCompatActivity {

    private EditText rName, rEmail, rPassword, rCPassword;
    private Button rButton;
    private TextView rLink;

    //firebase
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rName = findViewById(R.id.name_R);
        rEmail = findViewById(R.id.email_R);
        rPassword = findViewById(R.id.password_R);
        rCPassword = findViewById(R.id.cPassword_R);
        rButton = findViewById(R.id.button_R);
        rLink = findViewById(R.id.link_R);
        progressBar = findViewById(R.id.progress_R);

        firebaseAuth = FirebaseAuth.getInstance();


        //user logged in or not
        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MapsActivity.class));
            finish();
        }

        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = rEmail.getText().toString().trim();
                String password = rPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    rEmail.setError("Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    rPassword.setError("Required");
                    return;
                }

                if (password.length() < 6){
                    rPassword.setError("Must be 6 Character or More");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register in firebase
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(Register.this, ""+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        rLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
