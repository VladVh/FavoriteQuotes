package com.development.vvoitsekh.favoritequotes.ui.base;


interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
