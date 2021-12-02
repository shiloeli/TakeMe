package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button button;
    EditText emailAddress;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        emailAddress = (EditText) findViewById(R.id.emailTxt);

        password = (EditText) findViewById(R.id.Passwordtxt);

        button = (Button) findViewById(R.id.button_connect);

        mAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                String txtEmail = emailAddress.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(txtEmail)){
                    emailAddress.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("Password is required.");
                }

                mAuth.createUserWithEmailAndPassword(txtEmail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                     if(task.isSuccessful()){
                         Toast.makeText(Activity2.this, "User Created.", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(getApplicationContext(), DriverOrTrempist.class));
                     }else{
                        Toast.makeText(Activity2.this, "Error ! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                     }
                    }
                });
             }
        });
    }
}