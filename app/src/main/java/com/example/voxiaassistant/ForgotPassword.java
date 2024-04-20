package com.example.voxiaassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    EditText forgotemail;
    String strforgotemail;

    Button back,reset;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        forgotemail = findViewById(R.id.mailforgot);
        back = findViewById(R.id.backbtn);
        reset = findViewById(R.id.resetbtn);
        mAuth = FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strforgotemail = forgotemail.getText().toString().trim();

                if(!TextUtils.isEmpty(strforgotemail))
                {
                    ResetPassword();
                }
                else
                {
                    forgotemail.setError("Email Field Can't Be Empty");
                }
            }
        });

    }

    private void ResetPassword() {
        reset.setVisibility(View.INVISIBLE);

        mAuth.sendPasswordResetEmail(strforgotemail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgotPassword.this, "Reset Password Link Has Been Sent To Your Email.!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPassword.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                reset.setVisibility(View.VISIBLE);
            }
        });
    }
}