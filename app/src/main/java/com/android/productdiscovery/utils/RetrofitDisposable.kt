package com.android.productdiscovery.utils

import com.android.productdiscovery.domain.remote.pojo.response.HttpError
import com.android.productdiscovery.domain.remote.pojo.response.Error
import com.google.gson.Gson
import com.android.productdiscovery.domain.remote.api.ApiErrorCode
import retrofit2.HttpException
import retrofit2.Response

/**
 * @author ThanhDT
 * @since 4/17/18
 */
open class RetrofitDisposable<T> : SimpleDisposable<T>() {

    override fun onComplete() {

    }

    /**
     * Do not override this method in derived class, just implement [onHttpError] and [onNetworkError]
     * method to handle [Error] response
     */
    final override fun onError(e: Throwable) {
        if (e is HttpException) {
            val error = parseError(e.response())

            onHttpError(HttpError(error.code, ApiErrorCode.parseMsg(error.code)))
            return
        }

        onNetworkError(e)
    }

    override fun onNext(t: T) {

    }

    /**
     * Callback for handling Http response error (statusCode >= 400)
     */
    open fun onHttpError(httpError: HttpError) {

    }

    /**
     * Internet connection is not available.
     */
    open fun onNetworkError(e: Throwable) {

    }

    private fun parseError(response: Response<*>): Error {
        val error: Error

        error = try {
            val gson = Gson()
            gson.fromJson(response.errorBody()?.string(), Error::class.java)
        } catch (e: Exception) {
            Error()
        }

        return error
    }

}