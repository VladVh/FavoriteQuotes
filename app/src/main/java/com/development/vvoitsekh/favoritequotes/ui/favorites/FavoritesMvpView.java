package com.development.vvoitsekh.favoritequotes.ui.favorites;

import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.ui.base.MvpView;

import java.util.List;

/**
 * Created by v.voitsekh on 21.12.2016.
 */

public interface FavoritesMvpView extends MvpView {

    void showFavorites(List<Quote> quotes);

    void showFavoritesEmpty();

    void showError();
}
