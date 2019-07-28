package com.android.productdiscovery.domain.bus.message

import com.android.productdiscovery.domain.remote.pojo.response.HttpError

/**
 * @author ThanhDT
 * @since 5/24/18
 */
data class AuthenticationError(val httpError: HttpError)