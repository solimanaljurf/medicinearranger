package com.e.medicinearranger;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class time extends AppCompatActivity implements View.OnClickListener {
    Button addtime;
    Button savemed;
    TextView timee;
    EditText durationmed;
    FirebaseDatabase timesave;
    DatabaseReference timesrefrance;
    String userId = null;
    String format;
    ArrayList<String> day;
    CheckBox friday, sat, sun, mon, tus, wed, thursday, all;
    int hour,min;
    UserHelper userHelper = new UserHelper();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        addtime = findViewById(R.id.addtimebtn);
        savemed = findViewById(R.id.save);
        timee = findViewById(R.id.timetext);
        friday = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);
        sun = findViewById(R.id.sun);
        mon = findViewById(R.id.mun);
        durationmed = findViewById(R.id.dauerationmed);
        tus = findViewById(R.id.tus);
        wed = findViewById(R.id.wen);
        thursday = findViewById(R.id.thrs);
        all = findViewById(R.id.all);
        day = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

       creatNotificationChannel();


        //--- time of day-------------


        timesave = FirebaseDatabase.getInstance();
        timesrefrance = timesave.getReference("Medicine");
        final FirebaseUser userKey = FirebaseAuth.getInstance().getCurrentUser();
        assert userKey != null;
        userId = userKey.getUid();

        //---------------------add day-------------------------
        friday.setOnClickListener(this);
        sat.setOnClickListener(this);
        sun.setOnClickListener(this);
        mon.setOnClickListener(this);
        tus.setOnClickListener(this);
        wed.setOnClickListener(this);
        thursday.setOnClickListener(this);
        all.setOnClickListener(this);


        savemed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String duration = durationmed.getText().toString();

                FirebaseDatabase.getInstance().getReference("Medicine").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists())
                                    return;

                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    userHelper = dataSnapshot.getValue(UserHelper.class);

                                }
                                userHelper.setDay(day);
                                userHelper.setHour(String.valueOf(hour));
                                userHelper.setMin(String.valueOf(min));
                                userHelper.setFormat(String.valueOf(format));
                                userHelper.setDuration(duration);

                                timesrefrance.child(userId).setValue(userHelper).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(time.this, "data saved ", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), home.class));
                                        } else
                                            Toast.makeText(time.this, "Fail saving data", Toast.LENGTH_SHORT).show();

                                    }
                                });


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


            }
        });
        //-------------selected time-------------






        addtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(time.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            hour = hourOfDay;
                            min = minute;
                            if (hour == 0) {
                                hour += 12;
                                format = "AM";
                            } else if (hour == 12) {
                                format = "PM";
                            } else if (hour > 12) {
                                hour -= 12;
                                format = "PM";
                            } else {
                                format = "AM";
                            }

                            timee.setText(hour +":"+ min+" "+format);
                            Intent intent = new Intent(time.this,AlertRreciver.class);

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(time.this, 1 , intent,0 );
                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.HOUR, hour);
                            calendar.set(Calendar.MINUTE, min);
                            if (format.equals("AM"))
                                calendar.set(Calendar.AM_PM, Calendar.AM);
                            else if (format.equals("PM"))
                                calendar.set(Calendar.AM_PM, Calendar.PM);
                            calendar.set(Calendar.SECOND, 0);




                            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                                    calendar.getTimeInMillis() ,
                                    pendingIntent);



                        }



                    }, hour, min, false

                    );

                timePickerDialog.show();



            }

        });

    }

    private void creatNotificationChannel(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "LamubitReminderChannel";
            String description = "Channel for Lemubit Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit",name, importance);
            channel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }


    }





    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fri:
                textDesign(friday);
                day.add("Friday");
                break;
            case R.id.sat:
                textDesign(sat);
                day.add("Saturday");
                break;
            case R.id.sun:
                textDesign(sun);
                day.add("Sunday");
                break;
            case R.id.mun:
                textDesign(mon);
                day.add("Monday");
                break;
            case R.id.tus:
                textDesign(tus);
                day.add("Tusday");
                break;
            case R.id.wen:
                textDesign(wed);
                day.add("Wednsday");
                break;
            case R.id.thrs:
                textDesign(thursday);
                day.add("Thursday");
                break;




            case R.id.all:
                textDesign(all);
                textDesign(friday);
                textDesign(sat);
                textDesign(sun);
                textDesign(mon);
                textDesign(tus);
                textDesign(wed);
                textDesign(thursday);

                day.add("Friday");
                day.add("Saturday");
                day.add("Sunday");
                day.add("Monday");
                day.add("Tusday");
                day.add("Wednsday");
                day.add("Thursday");
                break;
        }
    }
    private void textDesign(CheckBox c){
        c.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        c.setTextColor(getResources().getColor(R.color.whit));
    }


    public void bac_med(View view) {
        startActivity(new Intent(getApplicationContext(),medicine.class));

    }
}





