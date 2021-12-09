package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class DriverInformation extends AppCompatActivity {

    EditText colortxt,numbertxt,typetxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_information);
        colortxt=(EditText)findViewById(R.id.ColorCarText);
        numbertxt=(EditText)findViewById(R.id.NumberCarText);
        typetxt=(EditText)findViewById(R.id.typeCarText);
    }

    public void onClickCreate(View view) {
        int StringNumberCar=-1;
        if (!TextUtils.isEmpty(numbertxt.getText().toString()))
             StringNumberCar=Integer.parseInt(numbertxt.getText().toString());
        String StringTypeCar=typetxt.getText().toString();
        String StringColor=colortxt.getText().toString();
        if(TextUtils.isEmpty(StringTypeCar))
        {
            typetxt.setError("נדרש סוג רכב");
            return;
        }
        if(TextUtils.isEmpty(numbertxt.getText().toString()))
        {
            numbertxt.setError("נדרש מספר רכב");
            return;
        }
        if(TextUtils.isEmpty(StringColor))
        {
            colortxt.setError("נדרש צבע של הרכב");
            return;
        }
        startActivity(new Intent(DriverInformation.this,MainActivity.class));
    }
}