package com.development.vvoitsekh.favoritequotes.injection.component;

import android.app.Application;
import android.content.Context;

import com.development.vvoitsekh.favoritequotes.data.DataManager;
import com.development.vvoitsekh.favoritequotes.data.local.QuoteDataSource;
import com.development.vvoitsekh.favoritequotes.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by v.voitsekh on 13.12.2016.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();

    Application application();
    QuoteDataSource databaseHelper();
    DataManager dataManager();
}
