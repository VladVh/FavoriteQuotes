package com.development.vvoitsekh.favoritequotes.utils;

import rx.Subscription;

/**
 * Created by v.voitsekh on 14.12.2016.
 */

public class RxUtil {

    public static void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
