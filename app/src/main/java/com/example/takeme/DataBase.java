package com.example.takeme;

import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DataBase {
    public static final String TAG = "TAG";
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private static DocumentReference documentReference;
    public static String getID(){
        return mAuth.getCurrentUser().getUid();
    }
    public static Task<AuthResult> CreateUser(String email, String password){
        return mAuth.createUserWithEmailAndPassword(email,password);
    }
    public static Task<AuthResult> SignIn(String email, String password){
        return mAuth.signInWithEmailAndPassword(email,password);
    }
    public static void createUser(String collection, String txtName,String txtLastName,String txtEmail,String txtPhone,String txtID, boolean male){
        String ID = getID();
        documentReference = fStore.collection(collection).document(ID);
        User user = new User(txtName, txtLastName, txtEmail, txtPhone, txtID, male);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG, "onSuccess: user profile is create for" + ID);
            }
        });
    }

    public static void createTremp(String collection, String txtSrcCity,String txtDestCity,String txtDay,String txtHour,String txtDate,int txtSeatsNum){
        String userDbId = getID();
        documentReference = fStore.collection(collection).document(userDbId);
        Tremp tremp = new Tremp(txtSrcCity, txtDestCity, txtDay, txtHour, txtDate, txtSeatsNum);
        documentReference.set(tremp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG, "onSuccess: Tremp is create for" + userDbId);
            }
        });
    }
    public static void createDriver(String collection, String name,String lastName,String email,String phone,String id,int StringNumberCar,String StringTypeCar,String StringColor,boolean male){
        String ID = getID();
        documentReference = fStore.collection(collection).document(ID);
        Driver userDriver = new Driver(name, lastName, email, phone, id, male ,StringNumberCar,StringTypeCar,StringColor);
        documentReference.set(userDriver).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG, "onSuccess: user profile is create for" + ID);
            }
        });
    }
    public static  void trempistJoinsTremp(String trempID)
    {
        fStore.collection("tremps").document(trempID).update("seats", FieldValue.increment(-1)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: Tremp seats update for tremp id"+trempID);
            }
        });
    }
    public static FirestoreRecyclerOptions<Tremp> Board(String collection, String name){
        String ID = getID();
        Query query = fStore.collection(collection).whereGreaterThan("seats",0);
        FirestoreRecyclerOptions<Tremp> options = new FirestoreRecyclerOptions.Builder<Tremp>()
                .setQuery(query, Tremp.class)
                .build();
        return options;

    }

}

