package com.development.vvoitsekh.favoritequotes.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.development.vvoitsekh.favoritequotes.model.content.Quote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v.voitsekh on 11.11.2016.
 */

public class QuoteDataSource implements DataSource {
    private static QuoteDataSource INSTANCE;

    private QuoteDBHelper mDbHelper;

    private QuoteDataSource(Context context) {
        mDbHelper = new QuoteDBHelper(context);
    }

    public static QuoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new QuoteDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getFavoriteQuotes(LoadQuotesCallback callback) {
        List<Quote> quotes = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                QuotePersistentContract.QuoteEntry.COLUMN_ENTRY_ID,
                QuotePersistentContract.QuoteEntry.COLUMN_QUOTE_TEXT,
                QuotePersistentContract.QuoteEntry.COLUMN_QUOTE_AUTHOR,
                QuotePersistentContract.QuoteEntry.COLUMN_FAVORITES
        };
        String selection = QuotePersistentContract.QuoteEntry.COLUMN_FAVORITES + " = 1";
        Cursor cursor =
                db.query(QuotePersistentContract.QuoteEntry.TABLE_NAME, projection, selection, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int itemId = cursor.getInt(cursor.getColumnIndex(QuotePersistentContract.QuoteEntry.COLUMN_ENTRY_ID));
                String text = cursor.getString(cursor.getColumnIndex(QuotePersistentContract.QuoteEntry.COLUMN_QUOTE_TEXT));
                String author = cursor.getString(cursor.getColumnIndex(QuotePersistentContract.QuoteEntry.COLUMN_QUOTE_AUTHOR));
                //Quote quote = new Quote(itemId, text, author);
                //quotes.add(quote);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        if (quotes.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onQuotesLoaded(quotes);
        }
    }

    @Override
    public void getQuote(GetQuoteCallback callback) {

    }

    @Override
    public void insertQuote(Quote quote) {

    }

    @Override
    public void markAsFavorite(int quoteId) {

    }

    @Override
    public void removeQuote(int quoteId) {

    }

    @Override
    public void refreshFavoriteQuotes() {

    }
}
