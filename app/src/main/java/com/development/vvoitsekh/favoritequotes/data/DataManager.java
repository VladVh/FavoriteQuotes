package com.development.vvoitsekh.favoritequotes.data;

import com.development.vvoitsekh.favoritequotes.data.local.QuoteDataSource;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;

import java.util.List;

import rx.Observable;

/**
 * Created by v.voitsekh on 13.12.2016.
 */

public class DataManager {

    private final QuoteDataSource mDatabaseHelper;

    public DataManager(QuoteDataSource mDatabaseHelper) {
        this.mDatabaseHelper = mDatabaseHelper;
    }

    public Observable<List<Quote>> getQuotes() {
        return mDatabaseHelper.getQuotes().distinct();
    }
}
