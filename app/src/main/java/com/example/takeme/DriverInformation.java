package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DriverInformation extends AppCompatActivity {
    public static final String TAG = "TAG";
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    EditText colortxt,numbertxt,typetxt;
    Button driverButton;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_information);

        colortxt=(EditText)findViewById(R.id.ColorCarText);
        numbertxt=(EditText)findViewById(R.id.NumberCarText);
        typetxt=(EditText)findViewById(R.id.typeCarText);
        driverButton=(Button)findViewById(R.id.buttonDriver1);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("--------------------888888---------------------");
                int StringNumberCar=Integer.parseInt(numbertxt.getText().toString());
                String StringTypeCar=typetxt.getText().toString();
                String StringColor=colortxt.getText().toString();

                if(TextUtils.isEmpty(StringTypeCar))
                {
                    typetxt.setError("נדרש סוג רכב");
                    return;
                }
                if(TextUtils.isEmpty(numbertxt.getText().toString()))
                {
                    numbertxt.setError("נדרש מספר רכב");
                    return;
                }
                if(TextUtils.isEmpty(StringColor))
                {
                    colortxt.setError("נדרש צבע של הרכב");
                    return;
                }

                userID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userID);

                String name = getIntent().getStringExtra("name");
                String lastName = getIntent().getStringExtra("lastName");
                String email = getIntent().getStringExtra("email");
                String phone = getIntent().getStringExtra("phone");
                String id = getIntent().getStringExtra("id");
                Boolean male = getIntent().getBooleanExtra("male", true);
                Driver userDriver = new Driver(name, lastName, email, phone, id, male ,StringNumberCar,StringTypeCar,StringColor);
                documentReference.set(userDriver).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        Log.d(TAG, "onSuccess: user profile is create for" + userID);
                    }
                });
                startActivity(new Intent(DriverInformation.this,MainActivity.class));
            }
        });
    }
}