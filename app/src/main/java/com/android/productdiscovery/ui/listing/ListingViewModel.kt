package com.android.productdiscovery.ui.listing

import android.app.Application
import com.android.productdiscovery.R
import com.android.productdiscovery.app.AppComponent
import com.android.productdiscovery.domain.remote.api.ApiService
import com.android.productdiscovery.domain.remote.pojo.response.Product
import com.android.productdiscovery.ui.base.BaseViewModel
import com.android.productdiscovery.utils.ConnectionUtils
import com.android.productdiscovery.utils.SingleLiveData
import com.android.productdiscovery.utils.extension.observeOnMain
import com.android.productdiscovery.utils.extension.subscribeWith
import timber.log.Timber
import javax.inject.Inject

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
class ListingViewModel : BaseViewModel(), AppComponent.Injectable {

    @Inject
    lateinit var application: Application
    @Inject
    lateinit var apiService: ApiService

    private var nextPage = 1
    private var currentKey = ""

    val firstPageData = SingleLiveData<List<Product>>()
    val pageData = SingleLiveData<List<Product>>()

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    fun searchProduct(query: String = currentKey, page: Int = nextPage) {
        if (ConnectionUtils.isOnline(application)) {
            if (currentKey != query) {
                currentKey = query
                nextPage = 1
            }

            if (page < nextPage) {
                return
            }

            addDisposable(apiService.searchProduct(query, page)
                    .observeOnMain()
                    .doOnSubscribe {
                        if (page == 1)
                            loadingStatus.postValue(true)
                    }
                    .subscribeWith(
                            {
                                if (page == 1) {
                                    loadingStatus.postValue(false)

                                    firstPageData.postValue(it.result.products)
                                } else {
                                    pageData.postValue(it.result.products)
                                }

                                nextPage++
                            },
                            {
                                Timber.e(it.message)
                                errorMsg.postValue(application.getString(R.string.error_default))
                            },
                            {
                                Timber.e(application.getString(R.string.connection_fail_toast))
                                errorMsg.postValue(application.getString(R.string.connection_fail_toast))
                            },
                            doOnError = {
                                if (page == 1){}
                                    loadingStatus.postValue(false)
                            }
                    ))
        } else {
            errorMsg.postValue(application.getString(R.string.connection_fail_toast))
        }
    }
}