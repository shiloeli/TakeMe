package com.example.takeme;

import android.content.Intent;
import android.util.Log;

import android.widget.TextView;

import androidx.core.app.NotificationCompat;

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
        User user = new User(txtName, txtLastName, txtEmail, txtPhone, txtID, male,false);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG, "onSuccess: user profile is create for" + ID);
            }
        });
    }

    public static void isDriver(Intent Driver , Intent Trempist) {
//        fStore.collection("users").document(DataBase.getID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.contains("myCar"))
//                {
//                    Log.d(TAG, "Its a Driver");
//                    startActivity(D);
//                }
//                else {
//                    Log.d(TAG, "Its a Trempist");
//                    startActivity(new Intent(getApplicationContext(), TrempistDashboard.class).putExtra("UID",DataBase.getID()));
//                }
//
//            }
//        });
    }
    public static void createTremp(String collection, String txtSrcCity,String txtDestCity,String txtHour,String txtDate,int txtSeatsNum){
        String userDbId = getID();
        documentReference = fStore.collection(collection).document();

        Tremp tremp = new Tremp(txtSrcCity, txtDestCity, txtHour, txtDate, txtSeatsNum,userDbId);
        documentReference.set(tremp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG, "onSuccess: Tremp is create for" + userDbId);

            }
        });
    }
    public static void createDriver(String collection, String name,String lastName,String email,String phone,String id,int StringNumberCar,String StringTypeCar,String StringColor,boolean male,boolean isDriver){
        String ID = getID();
        documentReference = fStore.collection(collection).document(ID);
        Driver userDriver = new Driver(name, lastName, email, phone, id, male ,StringNumberCar,StringTypeCar,StringColor,true);
        documentReference.set(userDriver).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Log.d(TAG, "onSuccess: user profile is create for" + ID);
            }
        });
    }
    public static void trempistLeaveTremp (String trempId)
    {
        documentReference = fStore.collection("tremps").document(trempId);

        documentReference.update("passengersIds",FieldValue.arrayRemove(getID())).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                documentReference.update("emptySeats", FieldValue.increment(+1)).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        documentReference = fStore.collection("tremps").document(trempId);
        documentReference.update("emptySeats", FieldValue.increment(-1)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Tremps details updated successfully for user : " + getID() );
            }
        });
        documentReference.update("passengersIds",FieldValue.arrayUnion(getID())).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: Trempsist " + getID() + "joined tremp " +trempId + "Successfully ");
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
        Query query = fStore.collection(collection).whereGreaterThan("emptySeats",0);
        FirestoreRecyclerOptions<Tremp> options = new FirestoreRecyclerOptions.Builder<Tremp>()
                .setQuery(query, Tremp.class)
                .build();
        return options;

    }
    public static FirestoreRecyclerOptions<Tremp> BoardSerchByCities(String dest,String src)
    {
        Query query = fStore.collection("tremps").whereGreaterThan("seats",0).whereEqualTo("dest",dest).whereEqualTo("src",src);
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
        documentReference = fStore.collection("users").document(id);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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
                .whereEqualTo("dest", b)
                .orderBy("emptySeats");
        return query;
    }
    public static void welcomeUser(TextView view){
        documentReference = fStore.collection("users").document(getID());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

    public static void setNotification(NotificationCompat.Builder builder, Tremp tremp){
        documentReference = fStore.collection("users").document(tremp.driverId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Driver user = documentSnapshot.toObject(Driver.class);
                builder.setContentText(user.myCar.carColor+" בצבע "+user.myCar.carType+"רכב מסוג "+"\n"+tremp.dest+" ל "+tremp.src+" מ "+user.name+"הצטרפת לטרמפ של ");
            }
        });

    }
}


