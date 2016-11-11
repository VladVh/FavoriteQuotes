package com.development.vvoitsekh.favoritequotes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by v.voitsekh on 10.11.2016.
 */

public class QuoteDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " String";

    private static final String INT_TYPE = " Integer";

    private static final String DATABASE_NAME = "Quote.db";

    private static final String CREATE_DB =
            "CREATE TABLE " + QuotePersistentContract.QuoteEntry.TABLE_NAME + "(" +
                    QuotePersistentContract.QuoteEntry._ID + INT_TYPE + " PRIMARY KEY" + "," +
                    QuotePersistentContract.QuoteEntry.COLUMN_ENTRY_ID + INT_TYPE + "," +
                    QuotePersistentContract.QuoteEntry.COLUMN_QUOTE_TEXT + TEXT_TYPE + "," +
                    QuotePersistentContract.QuoteEntry.COLUMN_QUOTE_AUTHOR + TEXT_TYPE + "," +
                    QuotePersistentContract.QuoteEntry.COLUMN_FAVORITES + INT_TYPE +
                    " )";

    public QuoteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
