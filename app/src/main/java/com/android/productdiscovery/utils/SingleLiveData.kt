package com.android.productdiscovery.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
class SingleLiveData<T> : LiveData<T>() {

    private var mPending = AtomicBoolean(false)


    @MainThread
    override fun observe(@NonNull owner: LifecycleOwner, @NonNull observer: Observer<in T>) {

        if (hasActiveObservers()) {
            Timber.w("Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    public override fun postValue(@Nullable value: T?) {
        mPending.set(true)
        super.postValue(value)
    }

//    fun fireValue(@Nullable value: T?) {
//        mPending.set(true)
//        super.postValue(value)
//    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}