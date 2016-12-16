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

    private final QuoteDataSource mDatabaseHelper;

    @Inject
    public DataManager(QuoteDataSource mDatabaseHelper) {
        this.mDatabaseHelper = mDatabaseHelper;
    }

    public Observable<List<Quote>> getQuotes() {
        return mDatabaseHelper.getQuotes().distinct();
    }
}
