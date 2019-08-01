package com.android.productdiscovery.ui.base

import androidx.lifecycle.ViewModel
import com.android.productdiscovery.utils.SingleLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
open class BaseViewModel : ViewModel() {

    val disposables = CompositeDisposable()
    val loadingStatus = SingleLiveData<Boolean>()
    val errorMsg = SingleLiveData<String>()
    val taskCompleted = SingleLiveData<Boolean>()
    val forceQuit = SingleLiveData<Boolean>()

    fun addDisposable(disposable: Disposable?) {
        disposable?.let { disposables.add(it) }
    }

    override fun onCleared() {
        disposables.clear()
    }
}