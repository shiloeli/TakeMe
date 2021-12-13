package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText txtPassword, txtName;
    Button buttLog;
    FirebaseFirestore fStore;
    public static final String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName=( EditText)findViewById(R.id.textEmail);
        txtPassword=( EditText)findViewById(R.id.txtPassword);
        buttLog = (Button)findViewById(R.id.btnSend);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
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

                mAuth.signInWithEmailAndPassword(StringtxtEmail, StringtxtPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "התחברות בוצעה בהצלחה", Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(getApplicationContext(), DriverOrTrempist.class);
                            String userID = mAuth.getCurrentUser().getUid();
                            DocumentReference docRef = fStore.collection("users").document(userID);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                            Log.d(TAG, "DocumentSnapshot type: " + document.getData().getClass());
                                            Log.d(TAG, "Object in hash map " + document.getData().get("name"));
                                            startActivity(i);
                                        }
                                        else {
                                            Log.d(TAG, "No such document");
                                        }
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
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
        Intent intenet=new Intent(MainActivity.this, UserRegister.class);
        startActivity(intenet);
    }


}