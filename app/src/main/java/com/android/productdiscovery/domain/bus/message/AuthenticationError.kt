package com.android.productdiscovery.domain.bus.message

import com.android.productdiscovery.domain.remote.pojo.response.HttpError

/**
 * @author ThanhDT
 * @since 2019-07-28
 */
data class AuthenticationError(val httpError: HttpError)