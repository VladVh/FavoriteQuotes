package com.development.vvoitsekh.favoritequotes.injection.component;

import com.development.vvoitsekh.favoritequotes.injection.ActivityScope;
import com.development.vvoitsekh.favoritequotes.injection.module.ActivityModule;

import dagger.Subcomponent;

/**
 * Created by v.voitsekh on 13.12.2016.
 */
@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    // TODO: 13.12.2016 add activities
}
