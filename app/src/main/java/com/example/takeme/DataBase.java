package com.example.takeme;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataBase {
    public static final String TAG = "TAG";
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private static DocumentReference documentReference;



    public static Task<AuthResult> CreateUser(String email, String password){
        return mAuth.createUserWithEmailAndPassword(email,password);

    }
    public static void createUser(String collection, String txtName,String txtLastName,String txtEmail,String txtPhone,String txtID, boolean male){
        String ID = mAuth.getCurrentUser().getUid();
        documentReference = fStore.collection(collection).document(ID);
        User user = new User(txtName, txtLastName, txtEmail, txtPhone, txtID, male);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG, "onSuccess: user profile is create for" + ID);
            }
        });

    }
    public static void createTremp(String collection, String txtSrcCity,String txtDestCity,String txtDay,String txtHour,String txtDate,String txtSeatsNum){
        String userDbId = mAuth.getCurrentUser().getUid();
        documentReference = fStore.collection(collection).document();
        Tremp tremp = new Tremp(txtSrcCity, txtDestCity, txtDay, txtHour, txtDate, txtSeatsNum);
        documentReference.set(tremp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG, "onSuccess: Tremp is create for" + userDbId);
                String trempId = documentReference.getId();
                fStore.collection("users").document(userDbId).update("trempsIds", FieldValue.arrayUnion(trempId));
            }
        });


    }
    public static void createDriver(String collection, String name,String lastName,String email,String phone,String id,int StringNumberCar,String StringTypeCar,String StringColor,boolean male){
        String ID = mAuth.getCurrentUser().getUid();
        documentReference = fStore.collection(collection).document(ID);
        Driver userDriver = new Driver(name, lastName, email, phone, id, male ,StringNumberCar,StringTypeCar,StringColor);
        documentReference.set(userDriver).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG, "onSuccess: user profile is create for" + ID);
            }
        });

    }

}

