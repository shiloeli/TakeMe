package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TrempistDashboard extends AppCompatActivity {
    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trempist_dashboard);

        welcome = (TextView) findViewById(R.id.welcomeTrempist);
        DataBase.welcomeUser(welcome);
    }

    public void onClickSearchTremp(View view) {
        Intent intenet=new Intent(TrempistDashboard.this,  Board.class);
        startActivity(intenet);
    }

    public void onClickTrempistTremps(View view) {
        Intent intenet=new Intent(TrempistDashboard.this, TrempistTrempsList.class);
        startActivity(intenet);
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