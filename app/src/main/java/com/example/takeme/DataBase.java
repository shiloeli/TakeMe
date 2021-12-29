package com.example.takeme;

import android.app.Notification;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
        DocumentReference dF;
        String ID = getID();
        dF = fStore.collection(collection).document(ID);
        User user = new User(txtName, txtLastName, txtEmail, txtPhone, txtID, male,false);
        dF.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG, "onSuccess: user profile is create for" + ID);
            }
        });
    }

    public static void createTremp(String collection, String txtSrcCity,String txtDestCity,String txtHour,String txtDate,int txtSeatsNum){
        DocumentReference dF;
        String userDbId = getID();
        dF = fStore.collection(collection).document();

        Tremp tremp = new Tremp(txtSrcCity, txtDestCity, txtHour, txtDate, txtSeatsNum,userDbId);
        dF.set(tremp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG, "onSuccess: Tremp is create for" + userDbId);

            }
        });
    }
    public static void createDriver(String collection, String name,String lastName,String email,String phone,String id,int StringNumberCar,String StringTypeCar,String StringColor,boolean male,boolean isDriver){
        DocumentReference dF;
        String ID = getID();
        dF = fStore.collection(collection).document(ID);
        Driver userDriver = new Driver(name, lastName, email, phone, id, male ,StringNumberCar,StringTypeCar,StringColor,true);
        dF.set(userDriver).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG, "onSuccess: user profile is create for" + ID);
            }
        });
    }
    public static void trempistLeaveTremp (String trempId)
    {
        DocumentReference dF;
        dF = fStore.collection("tremps").document(trempId);
        dF.update("passengersIds",FieldValue.arrayRemove(getID())).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dF.update("emptySeats", FieldValue.increment(+1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Trempsist " + getID() + "left tremp " +trempId + "Successfully ");
                    }
                });

            }
        });

    }
    public static void trempistJoinsTremp (String trempId)
    {
        DocumentReference dF;
        dF = fStore.collection("tremps").document(trempId);
        dF.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArrayList<String> passengers =(ArrayList<String>)documentSnapshot.get("passengersIds");
                Log.d(TAG, "test array of trempeists : " + passengers.toString());
                if (passengers.contains(getID())) {
                    Log.d(TAG, "User " + getID() + " is already in the tremp" + passengers.contains(getID()));
                    return;
                }
                else {
                    dF.update("emptySeats", FieldValue.increment(-1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "Tremps details updated successfully for user : " + getID() );
                            dF.update("passengersIds",FieldValue.arrayUnion(getID())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: Trempsist " + getID() + "joined tremp " +trempId + "Successfully ");
                                }
                            });
                        }
                    });
                }
            }
        });



            }

    public static FirestoreRecyclerOptions<Tremp> Search(String Dest , String src){
        Query query = fStore.collection("tremps").whereEqualTo("src",src).whereEqualTo("dest",Dest).whereGreaterThan("emptySeats",0).orderBy("emptySeats");
        FirestoreRecyclerOptions<Tremp> options = new FirestoreRecyclerOptions.Builder<Tremp>()
                .setQuery(query, Tremp.class)
                .build();
        return options;

    }
    public static FirestoreRecyclerOptions<Tremp> Board(String collection){
        List l = new ArrayList();
        l.add(getID());
        Query query = fStore.collection(collection).whereGreaterThan("emptySeats",0).orderBy("emptySeats");
        FirestoreRecyclerOptions<Tremp> options = new FirestoreRecyclerOptions.Builder<Tremp>()
                .setQuery(query, Tremp.class)
                .build();
        return options;

    }

    public static FirestoreRecyclerOptions<Tremp> trempList(String collection){
        Query query = fStore.collection(collection).whereEqualTo("driverId",getID());
        FirestoreRecyclerOptions<Tremp> options = new FirestoreRecyclerOptions.Builder<Tremp>()
                .setQuery(query, Tremp.class)
                .build();
        return options;
    }
    public static FirestoreRecyclerOptions<Tremp> trempistTremps(String collection){
        Query query = fStore.collection(collection).whereArrayContains("passengersIds",getID());
        FirestoreRecyclerOptions<Tremp> options = new FirestoreRecyclerOptions.Builder<Tremp>()
                .setQuery(query, Tremp.class)
                .build();
        return options;
    }

    public static void setNumberDriver(String id, TextView view) {
        DocumentReference dF;
        dF = fStore.collection("users").document(id);
        dF.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Driver user = documentSnapshot.toObject(Driver.class);
                view.setText(user.phone);
            }
        });
    }



    public static Task<Void> forgotPassword(String email) {
       return mAuth.sendPasswordResetEmail(email);
    }

    public static Query search(String a, String b) {
        Query query = fStore.collection("tremps").whereEqualTo("src", a)
                .whereEqualTo("dest", b).whereGreaterThan("emptySeats",0)
                .orderBy("emptySeats");
        return query;
    }
    public static void welcomeUser(TextView view){
        DocumentReference dF;
        dF = fStore.collection("users").document(getID());
        dF.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if(user.gender){
                    view.setText(" ברוך הבא "+user.name+" ! ");
                }else {
                    view.setText(" ברוכה הבאה "+user.name+" ! ");
                }

            }
        });
    }

    public static void setNotification(NotificationCompat.Builder builder, NotificationManagerCompat managerCompat, Tremp tremp){
        DocumentReference dF;
        dF = fStore.collection("users").document(tremp.driverId);
        dF.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Driver user = documentSnapshot.toObject(Driver.class);
                String message = user.myCar.carColor+" בצבע "+user.myCar.carType+" רכב מסוג ,"+tremp.dest+" ל "+tremp.src+" מ "+user.name+" הצטרפת לטרמפ של";
                builder.setContentTitle("הצטרפת לטרמפ");
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
//                Bitmap bitmapIcon = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.logo2);
//                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    builder.setSmallIcon(R.drawable.logo2).setLargeIcon(bitmapIcon);
//                } else {
                    builder.setSmallIcon(R.drawable.logo2);
//                }

                builder.setAutoCancel(true);
                managerCompat.notify(2,builder.build());
            }
        });
    }
}


