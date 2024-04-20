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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText logmail,logpassword;
    String strlogmail,strlogpassword;
    TextView forgotpass,signupnow;
    Button loginbutton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        logmail = findViewById(R.id.logmail);
        logpassword = findViewById(R.id.logpass);
        forgotpass = findViewById(R.id.forgottxt);
        signupnow = findViewById(R.id.signupnow);
        loginbutton = findViewById(R.id.btnlogin);
        mAuth = FirebaseAuth.getInstance();

        signupnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
                finish();
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Assistant.class);
                startActivity(intent);
                finish();
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                strlogmail = logmail.getText().toString().trim();
                strlogpassword = logpassword.getText().toString().trim();

                if(!TextUtils.isEmpty(strlogmail))
                {
                    if(strlogmail.matches(emailPattern))
                    {
                        if(!TextUtils.isEmpty(strlogpassword))
                        {
                            SignInUser();
                        }
                        else
                        {
                            logpassword.setError("Password Field Can't be Empty");
                        }
                    }
                    else
                    {
                        logmail.setError("Enter Valid Email Address");
                    }
                }
                else
                {
                    logmail.setError("Email Field Can't be Empty");
                }
            }
        });
    }

    private void SignInUser() {
        loginbutton.setVisibility(View.INVISIBLE);
        mAuth.signInWithEmailAndPassword(strlogmail,strlogpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>()
        {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Login Successful !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Assistant.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(MainActivity.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                loginbutton.setVisibility(View.VISIBLE);
            }
        });
    }
}