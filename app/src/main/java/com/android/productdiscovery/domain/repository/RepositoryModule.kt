package com.android.productdiscovery.domain.repository

import android.app.Application
import androidx.room.Room
import com.android.productdiscovery.data.db.AppDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author ThanhDT
 * @since 2019-08-04
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDb {
        return Room.databaseBuilder(application, AppDb::class.java, "product_discovery.db").build()
    }

    @Provides
    @Singleton
    fun provideProductRepo(database: AppDb): ProductRepo {
        return ProductRepoImpl(database)
    }
}