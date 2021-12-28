package com.example.takeme;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class DataBase {

    public static final String TAG = "TAG";
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private static DocumentReference documentReference;
    private static StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private static Context context;

//    public static void uploadImage(Uri image){
//        StorageReference file = storageReference.child("profile.jpg");
//        file.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Picasso.get().load(uri).into()
//                    }
//                })
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
////                Toast.makeText(context.getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }


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
                .whereEqualTo("dest", b).whereGreaterThan("emptySeats",0)
                .orderBy("emptySeats");
        return query;
    }
    public static void welcomeUser(TextView view){
        documentReference = fStore.collection("users").document(getID());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                    view.setText(" שלום "+user.name+" ! ");

                }
        });
    }
    public static void profile(EditText viewfirstName,EditText viewlastName, TextView viewEmail, TextView viewGender, TextView viewID, EditText viewPhone){
        documentReference = fStore.collection("users").document(getID());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                viewfirstName.setText(user.name);
                viewlastName.setText(user.lastName);
                viewEmail.setText(user.email);
                if(user.gender)
                    viewGender.setText("זכר");
                else viewGender.setText("נקבה");
                viewID.setText(user.id);
                viewPhone.setText(user.phone);

            }
        });
    }


    public static void setNotification(NotificationCompat.Builder builder, NotificationManagerCompat managerCompat, Tremp tremp){
        documentReference = fStore.collection("users").document(tremp.driverId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

    public static void updateProfile(String firstName,String lastName,String phone) {
        documentReference = fStore.collection("users").document(getID());
        documentReference.update("name",firstName);
        documentReference.update("lastName",lastName);
        documentReference.update("phone",phone);



    }

    public static void profileDriver(EditText firstName, EditText lastName, TextView email, TextView gender, TextView id, EditText phone, EditText carColor, EditText carType, EditText carNumber) {
        documentReference = fStore.collection("users").document(getID());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Driver driver = documentSnapshot.toObject(Driver.class);
                firstName.setText(driver.name);
                lastName.setText(driver.lastName);
                email.setText(driver.email);
                if(driver.gender)
                    gender.setText("זכר");
                else gender.setText("נקבה");
                id.setText(driver.id);
                phone.setText(driver.phone);
                carNumber.setText(String.valueOf(driver.myCar.carNumber));
                carColor.setText(driver.myCar.carColor);
                carType.setText(driver.myCar.carType);

            }
        });
    }

    public static void updateProfileDriver(String stringFisrtName, String stringLastName, String stringPhone, String stringCarNum, String stringCarColor, String stringCarType) {
        documentReference = fStore.collection("users").document(getID());
        documentReference.update("name",stringFisrtName);
        documentReference.update("lastName",stringLastName);
        documentReference.update("phone",stringPhone);
        documentReference.update("carNumber",stringCarNum);
        documentReference.update("carColor",stringCarColor);
        documentReference.update("carType",stringCarType);

    }
}


