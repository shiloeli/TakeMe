package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MessageToDriver extends AppCompatActivity {

    private TextView number;
    private EditText message;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_to_driver);
        Bundle bundle = getIntent().getExtras();

        number = findViewById(R.id.driverNumber);
        message = findViewById(R.id.bodyMessage);
        send = findViewById(R.id.sendMessage);

        DataBase.setNumberDriver(bundle.getString("driverId"),number);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                        sendSMS();
                    }else{
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }
            }
        });
    }

    private void sendSMS(){
        String pNumber = number.getText().toString();
        String SMS = message.getText().toString();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(pNumber,null,SMS,null, null);
            Toast.makeText(this, "ההודעה נשלחה", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"אירעה שגיאה",Toast.LENGTH_SHORT).show();
        }
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