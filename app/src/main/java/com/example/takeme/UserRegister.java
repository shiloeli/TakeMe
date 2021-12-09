package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class UserRegister extends AppCompatActivity {
    public static final String TAG = "TAG";
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    Button button;
    EditText emailAddress, password, name, lastName, phone, cv, password2;
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
        male = (RadioButton) findViewById(R.id.reg_male);
        female = (RadioButton) findViewById(R.id.reg_female);
        driver = (Switch) findViewById(R.id.reg_driver);
        passenger = (Switch) findViewById(R.id.reg_passenger);

        button = (Button) findViewById(R.id.button_connect);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String txtEmail = emailAddress.getText().toString();
                String pass = password.getText().toString();
                String pass2 = password2.getText().toString();
                String txtID = cv.getText().toString();
                String txtName = name.getText().toString();
                String txtLastName = lastName.getText().toString();
                String txtPhone = phone.getText().toString();
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

                if(TextUtils.isEmpty(txtEmail)){
                    emailAddress.setError("נדרשת כתובת אימייל");
                    return;
                }
                if(TextUtils.isEmpty(txtPhone))
                {
                    phone.setError("נדרש מספר פלאפון");
                    return;
                }

                if(TextUtils.isEmpty(txtID))
                {
                    cv.setError("נדרש מספר תעודת זהות");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("נדרשת סיסמה");
                    return;
                }
                if(TextUtils.isEmpty(pass2)){
                    password2.setError("נדרש אימות סיסמה");
                    return;
                }
                if(!pass.equals(pass2))
                {
                    password2.setError("הסיסמאות אינן תואמות!");
                    return;
                }
                if(!driver.isChecked() && !passenger.isChecked())
                {
                    driver.setError("");
                    passenger.setError("");
                    return;
                }
                if(!female.isChecked() && !male.isChecked())
                {
                    male.setError("");
                    female.setError("");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(txtEmail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(UserRegister.this, "משתמש נוצר", Toast.LENGTH_SHORT).show();
                         userID = mAuth.getCurrentUser().getUid();
                         DocumentReference documentReference = fStore.collection("users").document(userID);
                         User user = new User(txtName, txtLastName, txtEmail,txtPhone, txtID,male.isChecked());
                         documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void avoid) {
                                 Log.d(TAG,"onSuccess: user profile is create for"+ userID);
                             }
                         });
                         if(!driver.isChecked())
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                         else startActivity(new Intent(getApplicationContext(), DriverInformation.class));
                     }else{
                        Toast.makeText(UserRegister.this, "שגיאה!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                     }
                    }
                });
             }
        });
    }
}