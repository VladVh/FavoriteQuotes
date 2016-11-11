package com.development.vvoitsekh.favoritequotes.quote;

import com.development.vvoitsekh.favoritequotes.data.QuoteDataSource;
import com.development.vvoitsekh.favoritequotes.model.content.Quote;
import com.development.vvoitsekh.favoritequotes.network.ApiFactory;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by v.voitsekh on 10.11.2016.
 */

public class QuotePresenter implements QuoteContract.Presenter {

    private final QuoteContract.View mQuoteView;

    private final QuoteDataSource mDataSource;

    private Subscription mQuoteSubscription;

    public QuotePresenter(QuoteDataSource dataSource, QuoteContract.View fragment) {
        mQuoteView = fragment;
        mDataSource = dataSource;
    }

    @Override
    public void addToFavorites(Quote quote) {

    }

    @Override
    public void loadQuote() {
        Observable<Quote> loadedQuote = ApiFactory.getQuotesService()
                .randomQuote();
        loadedQuote.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> mQuoteView.setLoadingIndicator(true))
                .doAfterTerminate(() -> mQuoteView.setLoadingIndicator(false))
                .subscribe(new Observer<Quote>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //mDataSource.getQuote();
                    }

                    @Override
                    public void onNext(Quote quote) {
                        mDataSource.insertQuote(quote);
                        mQuoteView.showQuote(quote);
                    }
                });
    }

    @Override
    public void openFavoriteQuotes() {

    }

    @Override
    public void start() {
        loadQuote();
    }
}
