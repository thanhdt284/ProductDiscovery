package com.android.productdiscovery.data.manager

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
@Module
class PreferenceModule {

    @Singleton
    @Provides
    fun provideUserManager(application: Application): UserManager {
        return UserManager(application)
    }

//    @Singleton
//    @Provides
//    fun provideAppManager(application: Application): AppManager {
//        return AppManager(application)
//    }
}