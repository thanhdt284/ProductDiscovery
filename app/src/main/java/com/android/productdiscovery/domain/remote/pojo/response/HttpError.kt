package com.android.productdiscovery.domain.remote.pojo.response

import androidx.annotation.StringRes

/**
 * @author ThanhDT
 * @since 4/17/18
 */
class HttpError(val code: String, @StringRes val msgId: Int)