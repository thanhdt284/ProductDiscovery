package com.android.productdiscovery.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.productdiscovery.data.db.converter.*
import com.android.productdiscovery.data.db.dao.ProductDao
import com.android.productdiscovery.domain.remote.pojo.response.Product

/**
 * @author ThanhDT
 * @since 2019-08-04
 */
@Database(entities = [(Product::class)], version = 1)
@TypeConverters(AttributeGroupConverter::class, ImageConverter::class, PriceConverter::class,  SeoInfoConverter::class, StatusConverter::class)
abstract class AppDb : RoomDatabase() {

    abstract fun productDao(): ProductDao
}