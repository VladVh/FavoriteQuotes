package com.development.vvoitsekh.favoritequotes;

import android.app.Application;
import android.content.Context;

import com.development.vvoitsekh.favoritequotes.injection.component.ApplicationComponent;
import com.development.vvoitsekh.favoritequotes.injection.module.ApplicationModule;

/**
 * Created by v.voitsekh on 14.12.2016.
 */

public class MainApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }

        return mApplicationComponent;
    }
}
