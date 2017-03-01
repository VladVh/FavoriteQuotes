package com.development.vvoitsekh.favoritequotes.ui.favorites;

import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.ui.base.MvpView;

import java.util.List;

interface FavoritesMvpView extends MvpView {

    void showFavorites(List<Quote> quotes);

    void showFavoritesEmpty();

    void showError();
}
