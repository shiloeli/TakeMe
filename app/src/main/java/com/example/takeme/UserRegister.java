package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class UserRegister extends AppCompatActivity {
    public static final String TAG = "TAG";
    Button button;
    EditText emailAddress, password, name, lastName, phone, cv, password2;
    RadioButton male, female;
    Switch driver;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        emailAddress = (EditText) findViewById(R.id.emailTxt);
        password = (EditText) findViewById(R.id.Passwordtxt);
        password2 = (EditText) findViewById(R.id.reg_pass2);
        name = (EditText) findViewById(R.id.Nametxt);
        lastName = (EditText) findViewById(R.id.reg_lastName);
        phone = (EditText) findViewById(R.id.phoneNumbre);
        cv = (EditText) findViewById(R.id.reg_cv);
        male = (RadioButton) findViewById(R.id.reg_male);
        female = (RadioButton) findViewById(R.id.reg_female);
        driver = (Switch) findViewById(R.id.reg_driver);
        button = (Button) findViewById(R.id.button_connect);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String txtEmail = emailAddress.getText().toString();
                String pass = password.getText().toString();
                String pass2 = password2.getText().toString();
                String txtID = cv.getText().toString();
                String txtName = name.getText().toString();
                String txtLastName = lastName.getText().toString();
                String txtPhone = phone.getText().toString();

                if(TextUtils.isEmpty(txtName))
                {
                    name.setError("נדרש שם");
                    return;
                }
                if(TextUtils.isEmpty(txtLastName))
                {
                    lastName.setError("נדרש שם משפחה");
                    return;
                }

                if(TextUtils.isEmpty(txtEmail)){
                    emailAddress.setError("נדרשת כתובת אימייל");
                    return;
                }
                if(TextUtils.isEmpty(txtPhone))
                {
                    phone.setError("נדרש מספר פלאפון");
                    return;
                }
                int phoneLen=txtPhone.length();
                if(phoneLen!=10)
                {
                    phone.setError("מספר טלפון לא חוקי");
                    return;
                }

                if(TextUtils.isEmpty(txtID))
                {
                    cv.setError("נדרש מספר תעודת זהות");
                    return;
                }
                int idLen=txtID.length();
                if(idLen!=9)
                {
                    cv.setError("מספר תעודת זהות לא תקין");
                    return;
                }

                if(TextUtils.isEmpty(pass)){
                    password.setError("נדרשת סיסמה");
                    return;
                }
                if(TextUtils.isEmpty(pass2)){
                    password2.setError("נדרש אימות סיסמה");
                    return;
                }
                if(!pass.equals(pass2))
                {
                    password2.setError("הסיסמאות אינן תואמות!");
                    return;
                }


                DataBase.CreateUser(txtEmail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserRegister.this, "משתמש נוצר", Toast.LENGTH_SHORT).show();
                            if (driver.isChecked()) {
                                Intent intent = new Intent(UserRegister.this, DriverInformation.class);
                                intent.putExtra("name", txtName).putExtra("lastName", txtLastName).putExtra("email", txtEmail)
                                        .putExtra("phone", txtPhone).putExtra("id", txtID).putExtra("male", male.isChecked());
                                startActivity(intent);
                            } else {
                                DataBase.createUser("users",txtName, txtLastName, txtEmail, txtPhone, txtID, male.isChecked());
                                startActivity(new Intent(getApplicationContext(), Login.class));
                            }
                        } else {
                            Toast.makeText(UserRegister.this, "שגיאה!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }
}