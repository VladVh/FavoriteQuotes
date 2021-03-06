package com.development.vvoitsekh.favoritequotes.ui.favorites;

import android.util.Log;

import com.development.vvoitsekh.favoritequotes.data.DataManager;
import com.development.vvoitsekh.favoritequotes.data.model.Quote;
import com.development.vvoitsekh.favoritequotes.ui.base.BasePresenter;
import com.development.vvoitsekh.favoritequotes.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by v.voitsekh on 21.12.2016.
 */

public class FavoritesPresenter extends BasePresenter<FavoritesMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public FavoritesPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void getQuotes() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
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
                        if (quotes.isEmpty()) {
                            getMvpView().showFavoritesEmpty();
                        } else {
                            getMvpView().showFavorites(quotes);
                        }
                    }
                });
    }

    public void deleteQuoteFromFavorites(Quote quote) {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mDataManager.deleteQuote(quote).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                getMvpView().checkItemsCount();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {

            }
        });
    }
}
