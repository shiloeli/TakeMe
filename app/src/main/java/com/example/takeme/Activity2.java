package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button button;
    EditText emailAddress, password, name, lastName, phone, cv, carType, password2;
    RadioButton male, female;
    Switch driver, passenger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        emailAddress = (EditText) findViewById(R.id.emailTxt);
        password = (EditText) findViewById(R.id.Passwordtxt);
        password2 = (EditText) findViewById(R.id.reg_pass2);
        name = (EditText) findViewById(R.id.Nametxt);
        lastName = (EditText) findViewById(R.id.reg_lastName);
        phone = (EditText) findViewById(R.id.phoneNumbre);
        cv = (EditText) findViewById(R.id.reg_cv);
        carType = (EditText) findViewById(R.id.reg_car);
        male = (RadioButton) findViewById(R.id.reg_male);
        female = (RadioButton) findViewById(R.id.reg_female);
        driver = (Switch) findViewById(R.id.reg_driver);
        passenger = (Switch) findViewById(R.id.reg_passenger);

        button = (Button) findViewById(R.id.button_connect);

        mAuth = FirebaseAuth.getInstance();

//        if(mAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), DriverOrTrempist.class));
//            finish();
//        }

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String txtEmail = emailAddress.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(txtEmail)){
                    emailAddress.setError("נדרשת כתובת אימייל");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("נדרשת סיסמה");
                }

                mAuth.createUserWithEmailAndPassword(txtEmail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(Activity2.this, "משתמש נוצר", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(getApplicationContext(), DriverOrTrempist.class));
                     }else{
                        Toast.makeText(Activity2.this, "שגיאה!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                     }
                    }
                });
             }
        });
    }
}