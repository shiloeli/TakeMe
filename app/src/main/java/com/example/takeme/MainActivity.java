package com.example.takeme;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText txtPassword, txtName;
    TextView txtForgotPass;
    Button buttLog;
    public static final String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

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
                            fStore.collection("users").document(DataBase.getID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                //Checks if the user is also a Driver.
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    //Driver
                                    if (documentSnapshot.contains("myCar"))
                                    {
                                        Log.d(TAG, "Its a Driver");
                                        startActivity(new Intent(getApplicationContext(), DriverOrTrempist.class));
                                    }
                                    //Only user
                                    else {
                                        Log.d(TAG, "Its a Trempist");
                                        startActivity(new Intent(getApplicationContext(), TrempistDashboard.class));
                                    }

                                }
                            });
                        }else{
                            Toast.makeText(MainActivity.this, "שגיאה!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }
    public void onClickReg(View view) {

        Intent intenet = new Intent(MainActivity.this, UserRegister.class);
        startActivity(intenet);
    }
    public void onClickForgot(View view) {
        Intent intenet = new Intent(MainActivity.this, ForgotPassword.class);
        startActivity(intenet);
    }

}