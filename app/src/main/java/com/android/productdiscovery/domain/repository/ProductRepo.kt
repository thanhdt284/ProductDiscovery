package com.android.productdiscovery.domain.repository

import com.android.productdiscovery.domain.remote.pojo.response.Product
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * @author ThanhDT
 * @since 2019-08-04
 */
interface ProductRepo {

    fun saveProduct(product: Product): Completable

    fun getProduct(sku: String): Observable<Product>
}
