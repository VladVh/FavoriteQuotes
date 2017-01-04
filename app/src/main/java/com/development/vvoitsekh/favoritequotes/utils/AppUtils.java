package com.development.vvoitsekh.favoritequotes.utils;

import android.content.Context;
import android.os.Build;

import java.util.Locale;

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
}
