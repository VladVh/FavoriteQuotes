package com.development.vvoitsekh.favoritequotes.injection.component;

import android.app.Application;

import com.development.vvoitsekh.favoritequotes.data.DataManager;
import com.development.vvoitsekh.favoritequotes.data.local.QuoteDataSource;
import com.development.vvoitsekh.favoritequotes.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Application application();
    QuoteDataSource databaseHelper();
    DataManager dataManager();
}
