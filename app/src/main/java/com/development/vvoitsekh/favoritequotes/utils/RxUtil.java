package com.development.vvoitsekh.favoritequotes.utils;

import rx.Subscription;


public class RxUtil {

    public static void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
