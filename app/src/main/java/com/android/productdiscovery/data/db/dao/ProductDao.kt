package com.android.productdiscovery.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.productdiscovery.domain.remote.pojo.response.Product
import com.android.productdiscovery.domain.remote.pojo.response.Product.Companion.SKU
import com.android.productdiscovery.domain.remote.pojo.response.Product.Companion.TABLE_NAME
import io.reactivex.Maybe

/**
 * @author ThanhDT
 * @since 2019-08-04
 */
@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProduct(product: Product)

    @Query("SELECT * FROM $TABLE_NAME WHERE $SKU = :sku")
    fun getProduct(sku: String): Maybe<Product>
}