package com.android.productdiscovery.ui.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.productdiscovery.R
import com.android.productdiscovery.app.AppComponent
import com.android.productdiscovery.data.manager.CartManager
import com.android.productdiscovery.domain.remote.api.ApiService
import com.android.productdiscovery.domain.remote.pojo.response.CartItem
import com.android.productdiscovery.domain.remote.pojo.response.Product
import com.android.productdiscovery.domain.repository.ProductRepo
import com.android.productdiscovery.ui.base.BaseViewModel
import com.android.productdiscovery.utils.SingleLiveData
import com.android.productdiscovery.utils.extension.observeOnMain
import com.android.productdiscovery.utils.extension.subscribeWith
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
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
    @Inject
    lateinit var productRepo: ProductRepo

    val productData = MutableLiveData<Product>()
    val changeSlide = SingleLiveData<Boolean>()
    val cartItemCount = CartManager.cartCountData
    val productTotalPrice = MutableLiveData<Double>()
    val productCount = MutableLiveData<Int>()

    var sku = ""
    var product: Product? = null

    private var eventDisposable: Disposable? = null

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    fun getProductDetail(sku: String) {
        this.sku = sku

        if (sku.isBlank()) {
            errorMsg.postValue(application.getString(R.string.invalid_sku))
            return
        }

        addDisposable(Observable.concat(productRepo.getProduct(sku), fetchProduct(sku))
                .take(1)
                .observeOnMain()
                .doOnSubscribe { loadingStatus.postValue(true) }
                .subscribeWith(
                        {
                            Timber.e("onNext")
                            loadingStatus.postValue(false)

                            this.product = it

                            productData.postValue(product)
                        },
                        {
                            Timber.e(it.message)
                            errorMsg.postValue(application.getString(R.string.error_default))
                        },
                        { errorMsg.postValue(application.getString(R.string.connection_fail_toast)) },
                        doOnError = {
                            loadingStatus.postValue(false)
                            Timber.e("ERROR!")
                        }
                ))
    }

    private fun fetchProduct(sku: String): Observable<Product> {
        return apiService.getProductDetail(sku)
                .map { it.result.product }
                .doOnNext { saveProduct(it) }
    }

    private fun saveProduct(product: Product) {
        addDisposable(productRepo.saveProduct(product)
                .observeOnMain()
                .subscribe(
                        { Timber.e("product is saved!") },
                        { Timber.e(it) }
                ))
    }

    fun startChangeSlideEvent() {
        eventDisposable?.dispose()

        eventDisposable = Flowable.intervalRange(0, 1, 5, 5, TimeUnit.SECONDS)
                .observeOnMain()
                .subscribe(
                        { changeSlide.postValue(true) },
                        { Timber.e(it) }
                )

        addDisposable(eventDisposable)
    }

    fun addItem() {
        product?.let { prod ->
            if (CartManager.addItemToCart(prod)) {
                CartManager.getCartItem(prod.sku)?.let { invalidateProductInCart(it) }
            }
        }
    }

    fun removeItem() {
        product?.let { prod ->
            if (CartManager.removeItemFromCart(prod)) {
                CartManager.getCartItem(prod.sku)?.let { invalidateProductInCart(it) }
            }
        }
    }

    private fun invalidateProductInCart(cartItem: CartItem) {
        productCount.postValue(cartItem.quantity)
        productTotalPrice.postValue(cartItem.quantity * cartItem.sellingPrice)
    }

}