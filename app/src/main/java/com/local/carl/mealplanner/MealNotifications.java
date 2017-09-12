package com.local.carl.mealplanner;

/**
 * Created by carlr on 9/8/2017.
 */

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class MealNotifications  extends IntentService {
    private static final String TAG = "MealNotification";

    MealNotifications(){
        super(TAG);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, MealNotifications.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = MealNotifications.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isOn){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, SystemClock.currentThreadTimeMillis(), pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    public static boolean isServiceAlarmOn(Context context){
        Intent i = MealNotifications.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    @Override
    public void onHandleIntent(Intent intent){
        Toast.makeText(this, "Alarm Triggered!", Toast.LENGTH_SHORT).show();
        Resources resources = getResources();
        Intent i = MainActivity.newIntent(this);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("123")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("NOTE TITLE")
                .setContentText(resources.getString(R.string.notification_message))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, notification);
    }
}

