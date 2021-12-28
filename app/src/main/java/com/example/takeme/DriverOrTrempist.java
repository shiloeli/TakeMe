package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent=new Intent(getApplicationContext(), DriverOrTrempist.class);
                startActivity(intent);
                return true;
            case R.id.nav_profile:
                Intent intent2=new Intent(getApplicationContext(), Profile.class);
                startActivity(intent2);
                return true;
            case R.id.nav_find:
                Intent intent3=new Intent(getApplicationContext(), Board.class);
                startActivity(intent3);
            case R.id.nav_logout:

        }
        return super.onOptionsItemSelected(item);
    }

}