package com.development.vvoitsekh.favoritequotes.ui.main;

import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.ui.base.MvpView;

/**
 * Created by v.voitsekh on 14.12.2016.
 */
public interface MainMvpView extends MvpView {

    void showQuote(Quote quote);

    void showExistsInFavorites(boolean exists);

    void showError();

}
