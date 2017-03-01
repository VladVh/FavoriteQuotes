package com.development.vvoitsekh.favoritequotes.data.local;

import android.database.Cursor;

import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class QuoteDataSource {

    private final BriteDatabase mDb;

    @Inject
    QuoteDataSource(QuoteDBHelper quoteDBHelper) {
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

    public Observable<Long> addQuote(final Quote quote) {
        return Observable.defer(new Func0<Observable<Long>>() {
            @Override
            public Observable<Long> call() {
                return Observable.just(mDb.query("SELECT * FROM " + PersistentContract.QuoteEntry.TABLE_NAME + " WHERE " + PersistentContract.QuoteEntry.COLUMN_QUOTE_TEXT + "=?",
                        quote.getQuoteText()).getCount())
                        .flatMap(new Func1<Integer, Observable<Long>>() {
                            @Override
                            public Observable<Long> call(Integer integer) {
                                if (integer < 1) {
                                    return Observable.just(mDb.insert(PersistentContract.QuoteEntry.TABLE_NAME, PersistentContract.toContentValues(quote)));
                                }
                                return Observable.just((long) -1);
                            }
                        });
            }
        });
    }

    public Observable<Integer> removeQuote(final Quote quote) {
        return Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return Observable.just(mDb.delete(
                        PersistentContract.QuoteEntry.TABLE_NAME,
                        PersistentContract.QuoteEntry.COLUMN_QUOTE_TEXT + "=?",
                        quote.getQuoteText()));
            }
        });
    }
}
