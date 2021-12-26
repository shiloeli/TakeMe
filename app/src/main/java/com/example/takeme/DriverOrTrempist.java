package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DriverOrTrempist extends AppCompatActivity {
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_or_trempist);

        welcome = (TextView) findViewById(R.id.welcomeDriver);
        DataBase.welcomeUser(welcome);
    }

    public void onClickDriver(View view) {
        Intent intent=new Intent(DriverOrTrempist.this, DriverDashboard.class);
        startActivity(intent);

    }


    public void onClickTrempist(View view) {
        Intent intent=new Intent(DriverOrTrempist.this, TrempistDashboard.class);
        startActivity(intent);
    }
}