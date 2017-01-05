package com.development.vvoitsekh.favoritequotes.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by v.voitsekh on 04.01.2017.
 */

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, NotificationService.class);
        //serviceIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        context.startService(serviceIntent);
    }
}
