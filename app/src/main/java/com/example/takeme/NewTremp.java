package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Locale;

public class NewTremp extends AppCompatActivity {

    EditText srcCity,destCity, seatsNum;
    Button trempButton,date,time;
    String userID;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    Button timeButton;
    int hour,minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tremp);

        initDatePicker();
        dateButton=findViewById(R.id.datePickerButton);
        dateButton.setText(getTodayDate());
        timeButton=findViewById(R.id.timeButton);


        srcCity = (EditText) findViewById(R.id.a_srcCity);
        destCity = (EditText) findViewById(R.id.a_destCity);
        time = (Button) findViewById(R.id.timeButton);
        date = (Button) findViewById(R.id.datePickerButton);
        seatsNum = (EditText) findViewById(R.id.a_numberOfSeats);
        trempButton = (Button) findViewById(R.id.buttonSearchTremp);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        trempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtSrcCity = srcCity.getText().toString();
                String txtDestCity = destCity.getText().toString();
                String txtTime = time.getText().toString();
                String txtDate = date.getText().toString();
                String txtSeatsNum = seatsNum.getText().toString();


                if(TextUtils.isEmpty(txtSrcCity))
                {
                    srcCity.setError("שדה חובה");
                    return;
                }
                if(TextUtils.isEmpty(txtDestCity))
                {
                    destCity.setError("שדה חובה");
                    return;
                }

                if(TextUtils.isEmpty(txtSeatsNum))
                {
                    seatsNum.setError("שדה חובה");
                    return;
                }
                int seats=Integer.parseInt(txtSeatsNum);
                if(seats<1||seats>7)
                {
                    seatsNum.setError("מספר מקומות לא תקין");
                    return;
                }

                DataBase.createTremp("tremps",txtSrcCity, txtDestCity, txtTime, txtDate, Integer.parseInt(txtSeatsNum));
                //notification code


                NotificationCompat.Builder builder = new NotificationCompat.Builder(NewTremp.this, "My Notification");
                builder.setContentTitle("טרמפ חדש");
                builder.setContentText(txtDestCity+" ל "+txtSrcCity+"יצרת טרמפ חדש מ ");
                builder.setSmallIcon(R.drawable.logo2);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NewTremp.this);
                managerCompat.notify(1,builder.build());
                startActivity(new Intent(getApplicationContext(), DriverOrTrempist.class));
            }
        });

    }

    private String getTodayDate() {
        Calendar cal= Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        month=month+1;
        int day=cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date= makeDateString(dayOfMonth,month,year);
                dateButton.setText(date);

            }
        };
        Calendar cal= Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);

        int style= AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog=new DatePickerDialog(this,style,dateSetListener, year,month,day);
    }

    private String makeDateString(int day, int month, int year) {
        return day+"/"+month+"/"+year;

    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker timePicker, int selectHourOfDay, int selectMinute) {
                hour=selectHourOfDay;
                minute=selectMinute;
                timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));

            }
        };
        int style=AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog=new TimePickerDialog(this,style,onTimeSetListener,hour,minute,true);
        timePickerDialog.show();
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
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

}