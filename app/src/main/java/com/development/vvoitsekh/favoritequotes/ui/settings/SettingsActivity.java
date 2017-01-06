package com.development.vvoitsekh.favoritequotes.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;

import com.development.vvoitsekh.favoritequotes.R;

import java.util.Locale;

/**
 * Created by v.voitsekh on 06.01.2017.
 */

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(R.string.preference_language_key)) {
            String lang = sharedPreferences.getString(key, "English");
            Locale myLocale = new Locale(lang);
            Resources res = getResources();
            Configuration conf = res.getConfiguration();
            DisplayMetrics dm = res.getDisplayMetrics();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, SettingsActivity.class);
            startActivity(refresh);
            finish();
        } else if (key.equals(R.string.preference_notification_key)) {

        }
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

    }
}
