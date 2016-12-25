package com.development.vvoitsekh.favoritequotes.data;

import com.development.vvoitsekh.favoritequotes.data.local.QuoteDataSource;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by v.voitsekh on 13.12.2016.
 */

@Singleton
public class DataManager {

    private final QuoteDataSource mDataSource;
    //private final DataSource mDataSource;

    @Inject
    public DataManager(QuoteDataSource mDatabaseHelper) {
        this.mDataSource = mDatabaseHelper;
    }

//    @Inject
//    public DataManager(DataSource dataSource) {
//        this.mDataSource = dataSource;
//    }

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
