package com.development.vvoitsekh.favoritequotes.quote;

import com.development.vvoitsekh.favoritequotes.BasePresenter;
import com.development.vvoitsekh.favoritequotes.BaseView;
import com.development.vvoitsekh.favoritequotes.model.content.Quote;

/**
 * Created by v.voitsekh on 10.11.2016.
 */

public interface QuoteContract {
    interface View extends BaseView<Presenter> {

        void showQuote(Quote quote);

        void showMarkedFavorite();

        void setLoadingIndicator(boolean active);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void addToFavorites(Quote quote);

        void loadQuote();

        void openFavoriteQuotes();
    }
}
