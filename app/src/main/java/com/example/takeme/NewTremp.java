package com.example.takeme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Locale;

public class NewTremp extends AppCompatActivity {

    EditText srcCity,destCity, day, seatsNum;
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
//        day = (EditText) findViewById(R.id.a_day);
        time = (Button) findViewById(R.id.timeButton);
        date = (Button) findViewById(R.id.datePickerButton);
        seatsNum = (EditText) findViewById(R.id.a_numberOfSeats);
        trempButton = (Button) findViewById(R.id.buttonCreateTremp);


        trempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtSrcCity = srcCity.getText().toString();
                String txtDestCity = destCity.getText().toString();
                String txtDay = day.getText().toString();
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
                if(TextUtils.isEmpty(txtDay))
                {
                    day.setError("שדה חובה");
                    return;
                }
//                if(TextUtils.isEmpty(txtTime))
//                {
//                    time.setError("שדה חובה");
//                    return;
//                }

                if(TextUtils.isEmpty(txtSeatsNum))
                {
                    seatsNum.setError("שדה חובה");
                    return;
                }

                DataBase.createTremp("tremps",txtSrcCity, txtDestCity, txtDay, txtTime, txtDate, txtSeatsNum);
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
}