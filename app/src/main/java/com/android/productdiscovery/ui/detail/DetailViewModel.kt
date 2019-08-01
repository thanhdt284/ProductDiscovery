package com.android.productdiscovery.ui.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.productdiscovery.R
import com.android.productdiscovery.app.AppComponent
import com.android.productdiscovery.domain.remote.api.ApiService
import com.android.productdiscovery.domain.remote.pojo.response.Product
import com.android.productdiscovery.ui.base.BaseViewModel
import com.android.productdiscovery.utils.extension.observeOnMain
import com.android.productdiscovery.utils.extension.subscribeWith
import timber.log.Timber
import javax.inject.Inject

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
class DetailViewModel : BaseViewModel(), AppComponent.Injectable {

    @Inject
    lateinit var application: Application
    @Inject
    lateinit var apiService: ApiService

    val productData = MutableLiveData<Product>()

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    fun getProductDetail(sku: String) {
        if (sku.isBlank()) {
            errorMsg.postValue(application.getString(R.string.invalid_sku))
            return
        }

        addDisposable(apiService.getProductDetail(sku)
                .observeOnMain()
                .doOnSubscribe { loadingStatus.postValue(true) }
                .subscribeWith(
                        {
                            loadingStatus.postValue(false)
                            productData.postValue(it.result.product)
                        },
                        {
                            Timber.e(it.message)
                            errorMsg.postValue(application.getString(R.string.error_default))
                        },
                        { errorMsg.postValue(application.getString(R.string.connection_fail_toast)) },
                        doOnError = { loadingStatus.postValue(false) }
                ))
    }

}