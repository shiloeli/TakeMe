package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
}