package com.development.vvoitsekh.favoritequotes;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.development.vvoitsekh.favoritequotes.injection.component.ApplicationComponent;
import com.development.vvoitsekh.favoritequotes.injection.component.DaggerApplicationComponent;
import com.development.vvoitsekh.favoritequotes.injection.module.ApplicationModule;

import rx.plugins.DebugHook;
import rx.plugins.DebugNotification;
import rx.plugins.DebugNotificationListener;
import rx.plugins.RxJavaPlugins;

import static android.content.ContentValues.TAG;

/**
 * Created by v.voitsekh on 14.12.2016.
 */

public class MainApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        RxJavaPlugins.getInstance().registerObservableExecutionHook(new DebugHook(new DebugNotificationListener() {
            public Object onNext(DebugNotification n) {
                Log.v(TAG, "onNext on " + n);
                return super.onNext(n);
            }


            public Object start(DebugNotification n) {
                Log.v(TAG, "start on " + n);
                return super.start(n);
            }


            public void complete(Object context) {
                Log.v(TAG, "complete on " + context);
            }

            public void error(Object context, Throwable e) {
                Log.e(TAG, "error on " + context);
            }
        }));
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
