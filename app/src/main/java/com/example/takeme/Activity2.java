package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Activity2 extends AppCompatActivity {

//    EditText Nametxt;
//    EditText txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
//        Nametxt=(EditText)findViewById(R.id.Nametxt);
//        txtPassword=( EditText)findViewById(R.id.txtPassword);
//        String txtNameString=Nametxt.getText().toString();
//        String txtPassString=txtPassword.getText().toString();
    }

    public void onClickCreate(View view) {
        Intent intenet=new Intent(Activity2.this,board.class);
        Bundle b=new Bundle();
        startActivity(intenet);
    }
}