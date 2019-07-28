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

        private const val NICKNAME = "nickname"
        private const val USER_ID = "user_id"
        private const val AVATAR_URL = "avatar_url"
        private const val AUTHORIZATION_CODE = "authorizationCode"
        private const val PHONE_NUMBER = "phone_number"
        private const val INVITE_CODE = "invite_code"
        private const val RECORD = "record"

        private const val ACCESS_TOKEN = "access_token"
    }

    private fun getPrefs(): SharedPreferences =
        application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setUserId(id: Int) {
        getPrefs().edit { putInt(USER_ID, id) }
    }

    fun getUserId() = getPrefs().getInt(USER_ID, 0)

    fun setNickName(nickName: String) {
        getPrefs().edit { putString(NICKNAME, nickName) }
    }

    fun getNickName() = getPrefs().getString(NICKNAME, "")

    fun setAvatarUrl(avatarUrl: String) {
        getPrefs().edit { putString(AVATAR_URL, avatarUrl) }
    }

    fun getAvatarUrl() = getPrefs().getString(AVATAR_URL, "")

    fun setAuthorizationCode(authorizationCode: String) {
        getPrefs().edit { putString(AUTHORIZATION_CODE, authorizationCode) }
    }

    fun getAuthorizationCode() = getPrefs().getString(AUTHORIZATION_CODE, "")

    fun setRecord(record: Int) {
        getPrefs().edit { putInt(RECORD, record) }
    }

    fun getRecord() = getPrefs().getInt(RECORD, 0)

    fun setPhoneNumber(phoneNumber: String) {
        getPrefs().edit { putString(PHONE_NUMBER, phoneNumber) }
    }

    fun getPhoneNumber() = getPrefs().getString(PHONE_NUMBER, "")

    fun setInviteCode(inviteCode: String) {
        getPrefs().edit { putString(INVITE_CODE, inviteCode) }
    }

    fun getInviteCode() = getPrefs().getString(INVITE_CODE, "")

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