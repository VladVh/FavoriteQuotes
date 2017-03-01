package com.development.vvoitsekh.favoritequotes.injection.module;

import android.app.Application;
import android.content.Context;

import com.development.vvoitsekh.favoritequotes.injection.ApplicationContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Application mApplication;

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
