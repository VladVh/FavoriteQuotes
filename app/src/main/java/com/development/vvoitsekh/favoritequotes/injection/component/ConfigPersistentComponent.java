package com.development.vvoitsekh.favoritequotes.injection.component;

import com.development.vvoitsekh.favoritequotes.injection.ConfigPersistent;
import com.development.vvoitsekh.favoritequotes.injection.module.ActivityModule;

import dagger.Component;

/**
 * Created by v.voitsekh on 14.12.2016.
 */

@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);
}
