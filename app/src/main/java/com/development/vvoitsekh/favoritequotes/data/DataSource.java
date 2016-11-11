package com.development.vvoitsekh.favoritequotes.data;

import com.development.vvoitsekh.favoritequotes.model.content.Quote;

import java.util.List;

/**
 * Created by v.voitsekh on 11.11.2016.
 */

public interface DataSource {
    interface LoadQuotesCallback {

        void onQuotesLoaded(List<Quote> quotes);

        void onDataNotAvailable();
    }

    interface GetQuoteCallback {

        void onQuoteLoaded(Quote quote);

        void onDataNotAvailable();
    }

    void getFavoriteQuotes(LoadQuotesCallback callback);

    void getQuote(GetQuoteCallback callback);

    void insertQuote(Quote quote);

    void markAsFavorite(int quoteId);

    void removeQuote(int quoteId);

    void refreshFavoriteQuotes();
}
