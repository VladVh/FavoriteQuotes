package com.development.vvoitsekh.favoritequotes.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.development.vvoitsekh.favoritequotes.R;

public class StartupReceiver extends BroadcastReceiver {

    private static final String TAG = "StartupReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            boolean isNotificationSet = PreferenceManager.getDefaultSharedPreferences(context)
                    .getBoolean(context.getResources().getString(R.string.preference_notification_key), false);
            NotificationService.setServiceAlarm(context, isNotificationSet);
        }
    }
}
