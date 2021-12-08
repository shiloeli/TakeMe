package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Driver extends AppCompatActivity {

    String typeCar;
    int rate;

    public String getTypeCar(){
        return this.typeCar;
    }

    public int getRate(){
        return this.rate;
    }

    public Driver(){

    }

    public Driver(String typeCar){
        this.typeCar = typeCar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
    }

    public void onClickCreateTremp(View view) {
        Intent intenet=new Intent(Driver.this,Tremp.class);
        Bundle b=new Bundle();
        startActivity(intenet);
    }

    public void onClickMyTremps(View view) {
    }
}