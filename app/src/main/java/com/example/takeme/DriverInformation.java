package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DriverInformation extends AppCompatActivity {
    String userID;
    public static final String TAG = "TAG";
    FirebaseFirestore fStore;
    EditText colortxt,numbertxt,typetxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_information);
        colortxt=(EditText)findViewById(R.id.ColorCarText);
        numbertxt=(EditText)findViewById(R.id.NumberCarText);
        typetxt=(EditText)findViewById(R.id.typeCarText);
        fStore=FirebaseFirestore.getInstance();
    }

    public void onClickCreate(View view) {
        int StringNumberCar=-1;
        if (!TextUtils.isEmpty(numbertxt.getText().toString()))
             StringNumberCar=Integer.parseInt(numbertxt.getText().toString());
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
        User user = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             user = (User) extras.get("userObj");
             userID=(String) extras.get("UID");
        }
        Driver driver =new Driver(user,StringNumberCar,StringTypeCar,StringColor);
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.set(driver).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG,"onSuccess: Driver profile is create for"+ userID);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


    }
}