package com.development.vvoitsekh.favoritequotes.injection.component;

import com.development.vvoitsekh.favoritequotes.injection.ActivityScope;
import com.development.vvoitsekh.favoritequotes.injection.module.ActivityModule;
import com.development.vvoitsekh.favoritequotes.ui.favorites.FavoritesActivity;
import com.development.vvoitsekh.favoritequotes.ui.main.MainActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(FavoritesActivity favoritesActivity);
}
