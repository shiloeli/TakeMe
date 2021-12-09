package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TrempsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
    }

    public void onClickCreateTremp(View view) {
        Intent intenet=new Intent(TrempsList.this, MyTremp.class);
        startActivity(intenet);
    }

    public void onClickMyTremps(View view) {
    }
}