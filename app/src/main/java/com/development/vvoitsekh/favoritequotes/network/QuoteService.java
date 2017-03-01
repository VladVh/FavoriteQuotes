package com.development.vvoitsekh.favoritequotes.network;

import okhttp3.ResponseBody;
import retrofit2.http.POST;
import rx.Observable;


public interface QuoteService {

    @POST("?method=getQuote&lang=en&format=json")
    Observable<ResponseBody> randomQuote();

    @POST("?method=getQuote&lang=ru&format=json")
    Observable<ResponseBody> randomQuoteRU();
}
