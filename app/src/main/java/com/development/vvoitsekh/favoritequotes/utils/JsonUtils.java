package com.development.vvoitsekh.favoritequotes.utils;

import com.development.vvoitsekh.favoritequotes.data.model.Quote;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;


public class JsonUtils {

    private static final String QUOTE_TEXT = "quoteText";
    private static final String QUOTE_AUTHOR = "quoteAuthor";

    public static Quote parse(ResponseBody response) {
        Quote quote = new Quote();
        try {
            JSONObject jsonObject = new JSONObject(response.string());
            String quoteText = jsonObject.getString(QUOTE_TEXT);
            String quoteAuthor = jsonObject.getString(QUOTE_AUTHOR);

            quote.setQuoteAuthor(quoteAuthor);
            quote.setQuoteText(quoteText);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quote;
    }
}
