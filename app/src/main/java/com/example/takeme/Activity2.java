package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Activity2 extends AppCompatActivity {
    public static final String TAG = "TAG";
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    Button button;
    EditText emailAddress, password, name, lastName, phone, cv, carType, password2;
    RadioButton male, female;
    Switch driver, passenger;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        emailAddress = (EditText) findViewById(R.id.emailTxt);
        password = (EditText) findViewById(R.id.Passwordtxt);
        password2 = (EditText) findViewById(R.id.reg_pass2);
        name = (EditText) findViewById(R.id.Nametxt);
        lastName = (EditText) findViewById(R.id.reg_lastName);
        phone = (EditText) findViewById(R.id.phoneNumbre);
        cv = (EditText) findViewById(R.id.reg_cv);
        carType = (EditText) findViewById(R.id.reg_car);
        male = (RadioButton) findViewById(R.id.reg_male);
        female = (RadioButton) findViewById(R.id.reg_female);
        driver = (Switch) findViewById(R.id.reg_driver);
        passenger = (Switch) findViewById(R.id.reg_passenger);
        button = (Button) findViewById(R.id.button_connect);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

//        if(mAuth.getCurrentUser() != null){
////            startActivity(new Intent(getApplicationContext(), DriverOrTrempist.class));
////            finish();
////        }

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String txtEmail = emailAddress.getText().toString();
                String pass = password.getText().toString();
                String txtID = cv.getText().toString();
                String txtName = name.getText().toString();
                String txtLastName = lastName.getText().toString();
                String txtPhone = phone.getText().toString();
                String txtCarType = carType.getText().toString();
                boolean boolDriver = driver.isChecked();
                boolean boolPassenger = passenger.isChecked();

                System.out.println("------------===------------"+boolDriver+"--------------------====-------------");


                if(TextUtils.isEmpty(txtEmail)){
                    emailAddress.setError("נדרשת כתובת אימייל");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("נדרשת סיסמה");
                    return;
                }

                if(TextUtils.isEmpty(txtName))
                {
                    name.setError("נדרש שם");
                    return;
                }
                if(TextUtils.isEmpty(txtLastName))
                {
                    lastName.setError("נדרש שם משפחה");
                    return;
                }
                if(TextUtils.isEmpty(txtPhone))
                {
                    phone.setError("נדרש מספר פלאפון");
                    return;
                }
                if(TextUtils.isEmpty(txtCarType)&&driver.isChecked())
                {
                    carType.setError("נדרש סוג רכב");
                    return;
                }
                if(TextUtils.isEmpty(txtID))
                {
                    cv.setError("נדרש מספר תעודת זהות");
                    return;
                }
                if(!driver.isChecked() && !passenger.isChecked())
                {
                    driver.setError("נא לבחור אחת מהאופציות");
                    passenger.setError("נא לבחור אחת מהאופציות");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(txtEmail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(Activity2.this, "משתמש נוצר", Toast.LENGTH_SHORT).show();

                         userID = mAuth.getCurrentUser().getUid();
                         DocumentReference documentReference = fStore.collection("users").document(userID);
                         User user = new User(txtName, txtLastName, txtEmail,txtPhone, txtID, txtCarType, boolDriver, boolPassenger);

                         documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void avoid) {
                                 Log.d(TAG,"onSuccess: user profile is create for"+ userID);
                             }
                         });
                         startActivity(new Intent(getApplicationContext(), DriverOrTrempist.class));
                     }else{
                        Toast.makeText(Activity2.this, "שגיאה!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                     }
                    }
                });
             }
        });
    }
}