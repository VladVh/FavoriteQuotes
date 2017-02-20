package com.development.vvoitsekh.favoritequotes.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.development.vvoitsekh.favoritequotes.R;

/**
 * Created by v.voitsekh on 13.02.2017.
 */

public class StartupReceiver extends BroadcastReceiver {

    private static final String TAG = "StartupReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isNotificationSet = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getResources().getString(R.string.preference_notification_key), false);
        NotificationService.setServiceAlarm(context, isNotificationSet);
    }
}
