package com.development.vvoitsekh.favoritequotes.utils;

import com.development.vvoitsekh.favoritequotes.data.model.Quote;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by v.voitsekh on 14.12.2016.
 */

public class JsonUtils {

    private static final String QUOTE_TEXT = "quoteText";
    private static final String QUOTE_AUTHOR = "quoteAuthor";
    private static final String QUOTE_LINK = "quoteLink";

    public static Quote parse(JSONObject jsonObject) {
        Quote quote = new Quote();
        try {
            String quoteText = jsonObject.getString(QUOTE_TEXT);
            String quoteAuthor = jsonObject.getString(QUOTE_AUTHOR);

            quote.setQuoteAuthor(quoteAuthor);
            quote.setQuoteText(quoteText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return quote;
    }
}
