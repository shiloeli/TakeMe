package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    EditText lastName,firstName,phone;
    TextView gender,email,id;
    ImageView profileImage;
    Button edit;
    private static StorageReference storageReference = FirebaseStorage.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firstName = (EditText) findViewById(R.id.textViewPName);
        lastName=(EditText) findViewById(R.id.textViewFName);
        email=(TextView) findViewById(R.id.textViewEmail);
        id=(TextView) findViewById(R.id.textViewId);
        phone=(EditText)findViewById(R.id.textViewPhone);
        DataBase.profile(firstName,lastName,email,gender,id,phone);
        profileImage = findViewById(R.id.profile_image);
        edit = findViewById(R.id.edit_profile);
        StorageReference profileref = storageReference.child("users/" +DataBase.getID() + "/profile.jpg");
        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri image = data.getData();
//                profileImage.setImageURI(image);
                uploadImage(image);
            }
        }
    }

    public void uploadImage(Uri image){
        StorageReference file = storageReference.child("users/" +DataBase.getID() + "/profile.jpg");
        file.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(context.getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void OnClickEditInfo(View view) {

        String stringFisrtName=firstName.getText().toString();
        String stringLastName=lastName.getText().toString();
        String stringEmail=email.getText().toString();
        String stringPhone=phone.getText().toString();
        String stringID=id.getText().toString();
        int phoneLen=stringPhone.length();
        if(phoneLen!=10)
        {
            phone.setError("???????? ?????????? ???? ????????");
            return;
        }
        int idLen=stringID.length();
        if(idLen!=9)
        {
            id.setError("???????? ?????????? ???????? ???? ????????");
            return;
        }
        if(TextUtils.isEmpty(stringFisrtName))
        {
            firstName.setError("???????? ????");
            return;
        }
        if(TextUtils.isEmpty(stringLastName))
        {
            lastName.setError("???????? ???? ??????????");
            return;
        }
        DataBase.updateProfile(stringFisrtName,stringLastName,stringPhone);
        Toast.makeText(this,"Data has been update",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent=new Intent(getApplicationContext(), DriverOrTrempist.class);
                startActivity(intent);
                return true;
            case R.id.nav_profile:
                fStore.collection("users").document(DataBase.getID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.contains("myCar"))
                        {
                            startActivity(new Intent(getApplicationContext(), DriverProfile.class));
                        }
                        else {
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        }

                    }
                });
                return true;
            case R.id.nav_find:
                Intent intent3=new Intent(getApplicationContext(), Board.class);
                startActivity(intent3);
            case R.id.nav_logout:
                DataBase.logout();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();

        }
        return super.onOptionsItemSelected(item);
    }
}