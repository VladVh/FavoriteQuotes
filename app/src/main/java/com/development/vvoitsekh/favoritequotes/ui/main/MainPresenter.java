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


public class MainPresenter extends BasePresenter<MainMvpView> {

    private static final String TAG = "MainPresenter";

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
     MainPresenter(DataManager dataManager) {
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

     void loadQuote(Locale locale) {
        if (locale.getLanguage().equalsIgnoreCase("ru")) {
            loadQuoteRu();
        } else {
            loadQuoteEn();
        }
    }

    private void loadQuoteEn() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        Log.e(TAG, "loading Quote");

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

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(Quote quote) {
                        Log.e(TAG, "quotes set");
                        getMvpView().showQuote(quote);
                    }
                });
    }

    private void loadQuoteRu() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        Log.e(TAG, "loading Quote");

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

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(Quote quote) {
                        Log.e(TAG, "quotes set");
                        getMvpView().showQuote(quote);
                    }
                });
    }

     void isQuoteInFavorites(final String quote) {
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getQuotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Quote>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "error loading the quotes");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Quote> quotes) {
                        for (Quote quoteItem : quotes) {
                            if (quoteItem.getQuoteText().equals(quote)) {
                                getMvpView().showExistsInFavorites(true);
                                return;
                            }
                        }
                        getMvpView().showExistsInFavorites(false);
                    }
                });
    }

    void addToFavorites(String quoteText, String quoteAuthor) {
        Quote quote = new Quote(quoteText, quoteAuthor);
        RxUtil.unsubscribe(mSubscription);
        mDataManager.addQuote(quote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onNext(Long aLong) {
                Log.e(TAG, "received value " + aLong);
            }
        });
    }
}
