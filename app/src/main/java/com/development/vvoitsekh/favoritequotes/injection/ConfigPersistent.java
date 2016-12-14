package com.development.vvoitsekh.favoritequotes.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by v.voitsekh on 14.12.2016.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigPersistent {
}
