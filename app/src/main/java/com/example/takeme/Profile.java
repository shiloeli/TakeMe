package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    TextView name,email,phone,gender,id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = (TextView) findViewById(R.id.textViewName);
        email=(TextView)findViewById(R.id.textViewEmail);
        id=(TextView)findViewById(R.id.textViewId);
        phone=(TextView)findViewById(R.id.textViewPhone);
        gender=(TextView)findViewById(R.id.textViewGender);
        DataBase.profile(name,email,gender,id,phone);
    }
}