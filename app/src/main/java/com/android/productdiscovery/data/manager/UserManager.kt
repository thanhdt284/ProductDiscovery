package com.android.productdiscovery.data.manager

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
class UserManager(val application: Application) {

    companion object {
        private const val PREF_NAME = "com.android.productdiscovery.user_manager"

        private const val AUTHORIZATION_CODE = "authorizationCode"

        private const val ACCESS_TOKEN = "access_token"
    }

    private fun getPrefs(): SharedPreferences =
        application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setAuthorizationCode(authorizationCode: String) {
        getPrefs().edit { putString(AUTHORIZATION_CODE, authorizationCode) }
    }

    fun getAuthorizationCode() = getPrefs().getString(AUTHORIZATION_CODE, "")


    var accessToken: String
        set(value) {
            getPrefs().edit { putString(ACCESS_TOKEN, value) }
        }
        get() = getPrefs().getString(ACCESS_TOKEN, "") ?: ""

    fun isSignedIn(): Boolean = getAuthorizationCode() != ""

    fun clear() {
        getPrefs().edit { clear() }
    }

}