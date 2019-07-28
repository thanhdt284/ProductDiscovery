package com.android.productdiscovery.domain.remote.pojo.response

import com.google.gson.annotations.Expose

/**
 * @author ThanhDT
 * @since 2019-07-28
 */

class Error {

    @Expose
    var code: Int = 20001

    @Expose
    var message: String? = null
}
