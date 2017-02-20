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
import com.development.vvoitsekh.favoritequotes.utils.AppUtils;

import java.util.Locale;

/**
 * Created by v.voitsekh on 06.01.2017.
 */

public class SettingsActivity extends PreferenceActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppUtils.setDefaultPreferences(this);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        public static final int LANGUAGE_CHANGED = 1000;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(getResources().getString(R.string.preference_language_key))) {
                String lang = sharedPreferences.getString(key, "en");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = new Locale(lang);
                res.updateConfiguration(conf, dm);

                Intent refresh = new Intent(getActivity(), SettingsActivity.class);
                startActivity(refresh);
                getActivity().setResult(LANGUAGE_CHANGED);
                getActivity().finish();
            } else if (key.equals(getResources().getString(R.string.preference_notification_key))) {
                boolean isNotificationSet = sharedPreferences.getBoolean(key, true);
                AppUtils.setNotification(getActivity(), isNotificationSet);
            }
        }
    }
}
