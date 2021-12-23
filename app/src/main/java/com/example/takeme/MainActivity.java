package com.example.takeme;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    EditText txtPassword, txtName;
    TextView txtForgotPass;
    Button buttLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
//        String name = fStore.collection("users").document("0rI81VAz6UQZlXAqzBwEyyST0tg2");
//        System.out.println("------------------------"+name+"----------------------------");


        txtName=( EditText)findViewById(R.id.textEmail);
        txtPassword=( EditText)findViewById(R.id.txtPassword);
        buttLog = (Button)findViewById(R.id.ForgotPassButton);
        txtForgotPass=(TextView)findViewById(R.id.forgotPass);

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
//                            DatabaseReference rootRef = DataBase.getInstance();
//                            DatabaseReference usersRef = rootRef.child("Users");
//                            if(usersRef.child(DataBase.getID()).child(isDriver)==false)

                            startActivity(new Intent(getApplicationContext(), DriverOrTrempist.class).putExtra("UID",DataBase.getID()));
//                            else startActivity(new Intent(getApplicationContext(), DriverOrTrempist.class).putExtra("UID",DataBase.getID()));
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
    public void onClickForgot(View view) {
        Intent intenet = new Intent(MainActivity.this, ForgotPassword.class).putExtra("UID",DataBase.getID());
        startActivity(intenet);
    }



}