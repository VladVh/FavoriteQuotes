package com.development.vvoitsekh.favoritequotes.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.development.vvoitsekh.favoritequotes.data.model.Quote;


public class PersistentContract {

    private PersistentContract() {

    }

    public static abstract class QuoteEntry implements BaseColumns {
        static final String TABLE_NAME = "quotes";

        public static final String COLUMN_ENTRY_ID = "entryid";
        public static final String COLUMN_QUOTE_TEXT = "text";
        public static final String COLUMN_QUOTE_AUTHOR = "author";
        public static final String COLUMN_FAVORITES = "favorites";
    }

    static ContentValues toContentValues(Quote quote) {
        ContentValues values = new ContentValues();
        values.put(QuoteEntry.COLUMN_QUOTE_TEXT, quote.getQuoteText());
        values.put(QuoteEntry.COLUMN_QUOTE_AUTHOR, quote.getQuoteAuthor());
        return values;
    }

    static Quote parseCursor(Cursor cursor) {
        String quote = cursor.getString(cursor.getColumnIndexOrThrow(QuoteEntry.COLUMN_QUOTE_TEXT));
        String author = cursor.getString(cursor.getColumnIndexOrThrow(QuoteEntry.COLUMN_QUOTE_AUTHOR));
        return new Quote(quote, author);
    }
}
