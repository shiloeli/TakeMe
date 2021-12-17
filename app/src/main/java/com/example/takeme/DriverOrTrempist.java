package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DriverOrTrempist extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_or_trempist);
    }

    public void onClickDriver(View view) {
        Intent intent=new Intent(DriverOrTrempist.this, DriverDashboard.class);
        startActivity(intent);

    }


    public void onClickTrempist(View view) {
        Intent intent=new Intent(DriverOrTrempist.this, Board.class);
        startActivity(intent);
    }
}