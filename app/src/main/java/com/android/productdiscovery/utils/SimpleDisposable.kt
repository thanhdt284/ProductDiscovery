package com.android.productdiscovery.utils

import io.reactivex.observers.DisposableObserver

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
open class SimpleDisposable<T> : DisposableObserver<T>() {

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {

    }

    override fun onNext(t: T) {

    }
}