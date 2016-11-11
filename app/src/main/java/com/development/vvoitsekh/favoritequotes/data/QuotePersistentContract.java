package com.development.vvoitsekh.favoritequotes.data;

import android.provider.BaseColumns;

/**
 * Created by v.voitsekh on 10.11.2016.
 */

public class QuotePersistentContract {

    private QuotePersistentContract() {

    }

    public static abstract class QuoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "table";
        public static final String COLUMN_ENTRY_ID = "entryid";
        public static final String COLUMN_QUOTE_TEXT = "text";
        public static final String COLUMN_QUOTE_AUTHOR = "author";
        public static final String COLUMN_FAVORITES = "favorites";
    }
}
