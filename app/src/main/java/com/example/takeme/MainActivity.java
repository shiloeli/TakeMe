package com.example.takeme;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText txtPassword, txtName;
    Button buttLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName=( EditText)findViewById(R.id.textEmail);
        txtPassword=( EditText)findViewById(R.id.txtPassword);
        buttLog = (Button)findViewById(R.id.btnSend);

        buttLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String StringtxtEmail=txtName.getText().toString();
                String StringtxtPass=txtPassword.getText().toString();

                if(TextUtils.isEmpty(StringtxtEmail)){
                    txtName.setError("נדרשת כתובת אימייל");
                    return;
                }
                if(TextUtils.isEmpty(StringtxtPass)){
                    txtPassword.setError("נדרשת סיסמה");
                    return;
                }

                DataBase.SignIn(StringtxtEmail, StringtxtPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "התחברות בוצעה בהצלחה", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), DriverOrTrempist.class).putExtra("UID",DataBase.getID()));
                        }else{
                            Toast.makeText(MainActivity.this, "שגיאה!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
    public void onClickReg(View view) {
        Intent intenet = new Intent(MainActivity.this, UserRegister.class).putExtra("UID",DataBase.getID());
        startActivity(intenet);
    }


}