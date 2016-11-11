package com.development.vvoitsekh.favoritequotes.network;

import com.development.vvoitsekh.favoritequotes.model.content.Quote;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by v.voitsekh on 10.11.2016.
 */

public interface QuoteService {

    @GET("?method=getQuote&lang=en&format=json&json=?")
    Observable<Quote> popularMovies();
}
