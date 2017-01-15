package com.development.vvoitsekh.favoritequotes.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.notification.Receiver;

import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by v.voitsekh on 04.01.2017.
 */

public class AppUtils {

    public static Locale getCurrentLocale(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }

    public static void setDefaultPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String lang = sharedPreferences
                .getString(context.getResources().getString(R.string.preference_language_key), null);
        if (lang == null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(context.getResources().getString(R.string.preference_notification_key), true);
            setNotification(context);
            if (getCurrentLocale(context).toString().equalsIgnoreCase("ru_ru"))
                editor.putString(context.getResources().getString(R.string.preference_language_key), "ru_RU");
            else {
                editor.putString(context.getResources().getString(R.string.preference_language_key), "en_EN");
            }
        }
    }

    public static void setNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, Receiver.class); // AlarmReceiver1 = broadcast receiver

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);

        Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 12);
        alarmStartTime.set(Calendar.MINUTE, 0);
        alarmStartTime.set(Calendar.SECOND, 0);
        if (now.after(alarmStartTime)) {
            alarmStartTime.add(Calendar.DATE, 1);
        }
        alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d("Alarm","Alarms set for everyday 12 am.");
    }

    public static void cancelNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, Receiver.class); // AlarmReceiver1 = broadcast receiver

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
