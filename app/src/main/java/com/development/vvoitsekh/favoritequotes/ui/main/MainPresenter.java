package com.development.vvoitsekh.favoritequotes.ui.main;

import com.development.vvoitsekh.favoritequotes.data.DataManager;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.network.ApiFactory;
import com.development.vvoitsekh.favoritequotes.ui.base.BasePresenter;
import com.development.vvoitsekh.favoritequotes.utils.JsonUtils;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

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

    public void loadQuote() {
        checkViewAttached();

        mSubscription = ApiFactory.getQuotesService()
                .randomQuote()
                .timeout(2, TimeUnit.SECONDS)
                .retry(3)
                .subscribeOn(Schedulers.io()) // do the network call on another thread
                .observeOn(AndroidSchedulers.mainThread()) // return the result in mainThread
                .map(new Func1<JSONObject, Quote>() {
                    @Override
                    public Quote call(JSONObject jsonObject) {
                        return JsonUtils.parse(jsonObject);
                    }
                })
                .subscribe(new Subscriber<Quote>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(Quote quote) {
                        getMvpView().showQuote(quote);
                    }
                });

    }
}
