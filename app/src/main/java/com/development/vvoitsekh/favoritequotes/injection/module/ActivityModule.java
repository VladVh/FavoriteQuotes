package com.development.vvoitsekh.favoritequotes.injection.module;

import android.app.Activity;
import android.content.Context;

import com.development.vvoitsekh.favoritequotes.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by v.voitsekh on 13.12.2016.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }
}
