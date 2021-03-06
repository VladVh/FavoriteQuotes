package com.development.vvoitsekh.favoritequotes.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.development.vvoitsekh.favoritequotes.MainApplication;
import com.development.vvoitsekh.favoritequotes.injection.component.ActivityComponent;
import com.development.vvoitsekh.favoritequotes.injection.component.ConfigPersistentComponent;
import com.development.vvoitsekh.favoritequotes.injection.component.DaggerConfigPersistentComponent;
import com.development.vvoitsekh.favoritequotes.injection.module.ActivityModule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by v.voitsekh on 14.12.2016.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();

    private ActivityComponent mActivityComponent;
    private long mActivityId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (!sComponentsMap.containsKey(mActivityId)) {
            //Timber.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(MainApplication.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        } else {
            //Timber.i("Reusing ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = sComponentsMap.get(mActivityId);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            sComponentsMap.remove(mActivityId);
        }
        super.onDestroy();
    }

    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }
}
