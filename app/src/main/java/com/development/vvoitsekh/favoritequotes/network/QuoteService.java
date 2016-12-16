package com.development.vvoitsekh.favoritequotes.network;

import org.json.JSONObject;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by v.voitsekh on 10.11.2016.
 */

public interface QuoteService {

    @POST("?method=getQuote&lang=en&format=json")
    Observable<JSONObject> randomQuote();
}
