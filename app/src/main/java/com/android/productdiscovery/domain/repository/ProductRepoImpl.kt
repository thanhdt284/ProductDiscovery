package com.android.productdiscovery.domain.repository

import com.android.productdiscovery.data.db.AppDb
import com.android.productdiscovery.domain.remote.pojo.response.Product
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * @author ThanhDT
 * @since 2019-08-04
 */
class ProductRepoImpl(private val database: AppDb): ProductRepo {
    override fun saveProduct(product: Product): Completable {
        return Completable.fromAction { database.productDao().saveProduct(product) }
    }

    override fun getProduct(sku: String): Observable<Product> {
        return database.productDao().getProduct(sku)
            .toObservable()
    }
}