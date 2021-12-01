package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailAddress;
    EditText password;
    String txtEmail;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        mAuth = FirebaseAuth.getInstance();

        emailAddress = (EditText) findViewById(R.id.emailTxt);
        txtEmail = emailAddress.getText().toString();

        password = (EditText) findViewById(R.id.Passwordtxt);
        pass = password.getText().toString();


    }

    public void onClickCreate(View view) {
        Intent intenet=new Intent(Activity2.this,DriverOrTrempist.class);
        Bundle b=new Bundle();
        startActivity(intenet);


        mAuth.createUserWithEmailAndPassword(txtEmail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(Activity2.this, board.class));
                } else {
                    Toast.makeText(Activity2.this, "filed", Toast.LENGTH_LONG).show();
                }
            }
        });

        }
    }