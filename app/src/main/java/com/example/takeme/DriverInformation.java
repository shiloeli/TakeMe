package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DriverInformation extends AppCompatActivity {
    public static final String TAG = "TAG";
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
        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("--------------------888888---------------------");
                int StringNumberCar=Integer.parseInt(numbertxt.getText().toString());
                String StringTypeCar=typetxt.getText().toString();
                String StringColor=colortxt.getText().toString();
                String StringNumCar=numbertxt.getText().toString();
                if(TextUtils.isEmpty(StringTypeCar))
                {
                    typetxt.setError("נדרש סוג רכב");
                    return;
                }
                if(TextUtils.isEmpty(StringNumCar))
                {
                    numbertxt.setError("נדרש מספר רכב");
                    return;
                }
                int carNumLen=StringNumCar.length();
                if(carNumLen!=7)
                {
                    numbertxt.setError("מספר רכב לא תקין");
                    return;
                }
                if(TextUtils.isEmpty(StringColor))
                {
                    colortxt.setError("נדרש צבע של הרכב");
                    return;
                }


                String name = getIntent().getStringExtra("name");
                String lastName = getIntent().getStringExtra("lastName");
                String email = getIntent().getStringExtra("email");
                String phone = getIntent().getStringExtra("phone");
                String id = getIntent().getStringExtra("id");
                Boolean male = getIntent().getBooleanExtra("male", true);
                DataBase.createDriver("users",name, lastName, email, phone, id ,StringNumberCar,StringTypeCar,StringColor, male,true);
                startActivity(new Intent(DriverInformation.this,MainActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent=new Intent(getApplicationContext(), DriverOrTrempist.class);
                startActivity(intent);
                return true;
            case R.id.nav_profile:
                fStore.collection("users").document(DataBase.getID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.contains("myCar"))
                        {
                            startActivity(new Intent(getApplicationContext(), DriverProfile.class));
                        }
                        else {
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        }

                    }
                });
                return true;
            case R.id.nav_find:
                Intent intent3=new Intent(getApplicationContext(), Board.class);
                startActivity(intent3);
            case R.id.nav_logout:
                DataBase.logout();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

        }
        return super.onOptionsItemSelected(item);
    }
}