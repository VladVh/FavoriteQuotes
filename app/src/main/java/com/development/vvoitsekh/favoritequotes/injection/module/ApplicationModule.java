package com.development.vvoitsekh.favoritequotes.injection.module;

import android.app.Application;
import android.content.Context;

import com.development.vvoitsekh.favoritequotes.injection.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by v.voitsekh on 13.12.2016.
 */

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }
}
