package com.android.productdiscovery.app

import android.app.Application
import com.android.productdiscovery.data.manager.PreferenceModule
import com.android.productdiscovery.domain.remote.api.ApiModule
import com.android.productdiscovery.domain.remote.api.ApiService
import com.android.productdiscovery.ui.listing.ListingViewModel
import dagger.Component
import java.util.logging.LogManager
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author ThanhDT
 * @since 2019-07-28
 */

@Singleton
@Component(modules = [
    AppModule::class,
    ApiModule::class,
    PreferenceModule::class
])
interface AppComponent {

    fun application(): Application
    fun apiService(): ApiService

    interface Injectable {
        fun inject(appComponent: AppComponent)
    }

    fun inject(app: App)

    fun inject(viewModel: ListingViewModel)

}