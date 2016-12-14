package com.development.vvoitsekh.favoritequotes.ui.base;

/**
 * Created by v.voitsekh on 14.12.2016.
 */

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
