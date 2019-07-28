package com.android.productdiscovery.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * @author ThanhDT
 * @since 2019-07-28
 */
class ViewModelFactory(private val application: App) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val t: T = super.create(modelClass)
//        if (t is AppComponent.Injectable) {
//            (t as AppComponent.Injectable).inject(application.appComponent)
//        }

        return t
    }
}