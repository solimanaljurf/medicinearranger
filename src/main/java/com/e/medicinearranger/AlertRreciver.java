package com.e.medicinearranger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class AlertRreciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String nameOfMedicine = preferences.getString("name",null);
        String numberOfDoses = preferences.getString("doses",null);
        String beforeaftereat =preferences.getString("beforAfter",null);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifyLemubit")
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentTitle(nameOfMedicine)
                .setContentText("Don't forget to take "+ numberOfDoses +" "+ beforeaftereat )
                .setPriority(NotificationCompat.PRIORITY_HIGH);
                 NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                 notificationManager.notify(1,builder.build());

    }
}

