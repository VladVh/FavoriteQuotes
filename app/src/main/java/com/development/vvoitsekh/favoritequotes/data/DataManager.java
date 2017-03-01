package com.development.vvoitsekh.favoritequotes.data;

import com.development.vvoitsekh.favoritequotes.data.local.QuoteDataSource;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;


@Singleton
public class DataManager {

    private final QuoteDataSource mDataSource;
    //private final DataSource mDataSource;

    @Inject
    DataManager(QuoteDataSource mDatabaseHelper) {
        this.mDataSource = mDatabaseHelper;
    }


    public Observable<List<Quote>> getQuotes() {
        return mDataSource.getQuotes();
    }

    public Observable<Long> addQuote(Quote quote) {
        return mDataSource.addQuote(quote);
    }

    public Observable<Integer> deleteQuote(Quote quote) {
        return mDataSource.removeQuote(quote);
    }
}
