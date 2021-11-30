package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtPassword;
    EditText txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName=( EditText)findViewById(R.id.txtName);
        txtPassword=( EditText)findViewById(R.id.txtPassword);
        String StringtxtName=txtName.getText().toString();
        String StringtxtPass=txtPassword.getText().toString();

    }

    public void onClickReg(View view) {
        Intent intenet=new Intent(MainActivity.this,Activity2.class);
        Bundle b=new Bundle();
        startActivity(intenet);
    }

    public void onClickSend(View view) {
    }
}