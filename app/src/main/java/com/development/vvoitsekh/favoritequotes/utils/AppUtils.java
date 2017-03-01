package com.development.vvoitsekh.favoritequotes.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.development.vvoitsekh.favoritequotes.R;
import com.development.vvoitsekh.favoritequotes.notification.NotificationService;

import java.util.Locale;


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
            setNotification(context, true);
            if (getCurrentLocale(context).toString().equalsIgnoreCase("ru_ru"))
                editor.putString(context.getResources().getString(R.string.preference_language_key), "ru_RU");
            else {
                editor.putString(context.getResources().getString(R.string.preference_language_key), "en_EN");
            }
            editor.apply();
        }
    }

    public static void setNotification(Context context, boolean isOn) {
        NotificationService.setServiceAlarm(context, isOn);
    }

    public static boolean isNotificationSet(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean(context.getResources().getString(R.string.preference_notification_key), false);
    }
}
