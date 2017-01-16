package com.development.vvoitsekh.favoritequotes.ui.main;

import android.util.Log;

import com.development.vvoitsekh.favoritequotes.data.DataManager;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.network.ApiFactory;
import com.development.vvoitsekh.favoritequotes.ui.base.BasePresenter;
import com.development.vvoitsekh.favoritequotes.utils.JsonUtils;
import com.development.vvoitsekh.favoritequotes.utils.RxUtil;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by v.voitsekh on 14.12.2016.
 */

public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void loadQuote(Locale locale) {
        if (locale.getLanguage().equals("ru_ru")) {
            loadQuoteRu();
        } else {
            loadQuoteEn();
        }
    }

    private void loadQuoteEn() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        Log.e("loadQuote", "loadQuote");

        mSubscription = ApiFactory.getQuotesService()
                .randomQuote()
                .subscribeOn(Schedulers.io()) // do the network call on another thread
                .observeOn(AndroidSchedulers.mainThread()) // return the result in mainThread
                .map(new Func1<ResponseBody, Quote>() {
                    @Override
                    public Quote call(ResponseBody response) {
                        return JsonUtils.parse(response);
                    }
                })
                .subscribe(new Subscriber<Quote>() {
                    @Override
                    public void onCompleted() {
                        Log.e("Completed", "completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("INTERNET ERROR", e.getMessage());
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(Quote quote) {
                        Log.e("added", "quote set");
                        getMvpView().showQuote(quote);
                    }
                });
    }

    private void loadQuoteRu() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        Log.e("loadQuote", "loadQuote");

        mSubscription = ApiFactory.getQuotesService()
                .randomQuoteRU()
                .subscribeOn(Schedulers.io()) // do the network call on another thread
                .observeOn(AndroidSchedulers.mainThread()) // return the result in mainThread
                .map(new Func1<ResponseBody, Quote>() {
                    @Override
                    public Quote call(ResponseBody response) {
                        return JsonUtils.parse(response);
                    }
                })
                .subscribe(new Subscriber<Quote>() {
                    @Override
                    public void onCompleted() {
                        Log.e("Completed", "completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("INTERNET ERROR", e.getMessage());
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(Quote quote) {
                        Log.e("added", "quote set");
                        getMvpView().showQuote(quote);
                    }
                });
    }

    public void isQuoteInFavorites(final String quote) {
        mSubscription = mDataManager.getQuotes()
                //.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Quote>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("DB error", "error loading the quotes");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Quote> quotes) {
                        for (Quote quoteItem : quotes) {
                            if (quoteItem.getQuoteText().equals(quote)) {
                                getMvpView().showExistsInFavorites(true);
                            }
                        }
                        getMvpView().showExistsInFavorites(false);
                    }
                });
    }

    public long addToFavorites(String quoteText, String quoteAuthor) {
        Quote quote = new Quote(quoteText, quoteAuthor);
        final Long[] value = new Long[1];
        mDataManager.addQuote(quote).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Log.e("added to favorites", "added");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("adding to favorites", e.getMessage());
            }

            @Override
            public void onNext(Long aLong) {
                Log.e("received long", "" + aLong);
                value[0] = aLong;
            }
        });
        return value[0];
    }
}
