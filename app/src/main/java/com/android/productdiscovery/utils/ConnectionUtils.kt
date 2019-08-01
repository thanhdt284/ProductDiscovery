package com.android.productdiscovery.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author ThanhDT
 * @since 4/19/18
 */
object ConnectionUtils {
    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
    }
}
