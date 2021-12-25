package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TrempistDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trempist_dashboard);
    }

    public void onClickSearchTremp(View view) {
        Intent intenet=new Intent(TrempistDashboard.this,  Board.class);
        startActivity(intenet);
    }

    public void onClickTrempistTremps(View view) {
        Intent intenet=new Intent(TrempistDashboard.this, TrempistTrempsList.class);
        startActivity(intenet);
    }
}