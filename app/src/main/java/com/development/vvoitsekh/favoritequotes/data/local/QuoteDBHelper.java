package com.development.vvoitsekh.favoritequotes.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by v.voitsekh on 10.11.2016.
 */

public class QuoteDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Quote.db";

    private static final String TEXT_TYPE = " String";

    private static final String INT_TYPE = " Integer";


    private static final String CREATE_DB =
            "CREATE TABLE " + PersistentContract.QuoteEntry.TABLE_NAME + "(" +
                    PersistentContract.QuoteEntry._ID + INT_TYPE + " PRIMARY KEY" + "," +
                    PersistentContract.QuoteEntry.COLUMN_ENTRY_ID + INT_TYPE + "," +
                    PersistentContract.QuoteEntry.COLUMN_QUOTE_TEXT + TEXT_TYPE + "," +
                    PersistentContract.QuoteEntry.COLUMN_QUOTE_AUTHOR + TEXT_TYPE + "," +
                    PersistentContract.QuoteEntry.COLUMN_FAVORITES + INT_TYPE +
                    " )";

    public QuoteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(CREATE_DB);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
