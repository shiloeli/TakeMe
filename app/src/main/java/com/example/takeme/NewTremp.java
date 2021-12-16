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

public class NewTremp extends AppCompatActivity {

    EditText srcCity,destCity, day, hour, date, seatsNum;
    Button trempButton;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tremp);

        srcCity = (EditText) findViewById(R.id.a_srcCity);
        destCity = (EditText) findViewById(R.id.a_destCity);
        day = (EditText) findViewById(R.id.a_day);
        hour = (EditText) findViewById(R.id.a_hour);
        date = (EditText) findViewById(R.id.a_date);
        seatsNum = (EditText) findViewById(R.id.a_numberOfSeats);
        trempButton = (Button) findViewById(R.id.buttonCreateTremp);

//        mAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();

        trempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtSrcCity = srcCity.getText().toString();
                String txtDestCity = destCity.getText().toString();
                String txtDay = day.getText().toString();
                String txtHour = hour.getText().toString();
                String txtDate = date.getText().toString();
                String txtSeatsNum = seatsNum.getText().toString();


                if(TextUtils.isEmpty(txtSrcCity))
                {
                    srcCity.setError("שדה חובה");
                    return;
                }
                if(TextUtils.isEmpty(txtDestCity))
                {
                    destCity.setError("שדה חובה");
                    return;
                }
                if(TextUtils.isEmpty(txtDay))
                {
                    day.setError("שדה חובה");
                    return;
                }
                if(TextUtils.isEmpty(txtHour))
                {
                    hour.setError("שדה חובה");
                    return;
                }
                if(TextUtils.isEmpty(txtDate))
                {
                    date.setError("שדה חובה");
                    return;
                }
                if(TextUtils.isEmpty(txtSeatsNum))
                {
                    seatsNum.setError("שדה חובה");
                    return;
                }

                DataBase.createTremp("tremps",txtSrcCity, txtDestCity, txtDay, txtHour, txtDate, txtSeatsNum);
                startActivity(new Intent(getApplicationContext(), DriverOrTrempist.class));
            }
        });

    }
}