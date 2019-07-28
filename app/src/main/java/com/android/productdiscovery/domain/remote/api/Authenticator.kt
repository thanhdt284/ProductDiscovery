package com.android.productdiscovery.domain.remote.api

import com.android.productdiscovery.data.manager.UserManager
import com.android.productdiscovery.domain.bus.message.AuthFail
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
class Authenticator(private val preferenceManager: UserManager) : Authenticator {

    lateinit var apiService: ApiServiceNoAuth

    override fun authenticate(route: Route?, response: Response?): Request? {
        Timber.w("authenticate() called [401 error]")

        response?.let {
            Timber.w("Failed response count: ${responseCount(response)}")
            if (responseCount(response) >= 2) {
                return null
            }
        }

        var obtained = false

//        apiService.refreshToken(UserManager.getRefreshToken(app))
//                .subscribeWith(object : DisposableSingleObserver<Authenticate>() {
//
//                    override fun onSuccess(t: Authenticate) {
//                        Timber.w("onNext() called with dat: %s", t.toString())
//                        with(UserManager) {
//                            setAccessToken(app, t.accessToken)
//                            setRefreshToken(app, t.refreshToken)
//                            setExpires(app, t.expires)
//                            setExpiresInSeconds(app, t.expirationInSeconds)
//                        }
//
//                        obtained = true
//                    }
//
//                    override fun onError(e: Throwable) {
//                        Timber.w("onError() called")
//                        Timber.w(e)
//                        obtained = false
//                    }
//                })

        if (obtained) return response?.let {
            response.request()
                .newBuilder()
                .addHeader("Authorization", preferenceManager.getAuthorizationCode() ?: "")
                .build()
        }

        EventBus.getDefault().post(AuthFail())
        return null
    }

    private fun responseCount(response: Response): Int {
        var result = 1
        var failed: Response? = response

        while (failed?.priorResponse() != null) {
            result++
            failed = failed.priorResponse()
        }

        return result
    }
}