package com.development.vvoitsekh.favoritequotes.data.local;

import android.database.Cursor;

import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by v.voitsekh on 11.11.2016.
 */

public class QuoteDataSource {

    private final BriteDatabase mDb;

    @Inject
    public QuoteDataSource(QuoteDBHelper quoteDBHelper) {
        //QuoteDBHelper mDbHelper = new QuoteDBHelper(context);
        SqlBrite.Builder builder = new SqlBrite.Builder();
        mDb = builder.build().wrapDatabaseHelper(quoteDBHelper, Schedulers.immediate());
    }

    public Observable<List<Quote>> getQuotes() {
        return mDb.createQuery(PersistentContract.QuoteEntry.TABLE_NAME,
                "SELECT * FROM " + PersistentContract.QuoteEntry.TABLE_NAME)
                .mapToList(new Func1<Cursor, Quote>() {
                    @Override
                    public Quote call(Cursor cursor) {
                        return PersistentContract.parseCursor(cursor);
                    }
                });
    }

    public Observable<Quote> addQuote(final Quote quote) {
        return Observable.create(new Observable.OnSubscribe<Quote>() {
            @Override
            public void call(Subscriber<? super Quote> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    long result = mDb.insert(PersistentContract.QuoteEntry.TABLE_NAME,
                            PersistentContract.toContentValues(quote));
                    if (result >= 0) {
                        subscriber.onNext(quote);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }
}
