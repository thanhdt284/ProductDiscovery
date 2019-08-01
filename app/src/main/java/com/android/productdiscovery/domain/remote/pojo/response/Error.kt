package com.android.productdiscovery.domain.remote.pojo.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author ThanhDT
 * @since 2019-07-28
 */

class Error {

    @Expose
    @SerializedName("code")
    var code: String = ""

    @Expose
    @SerializedName("massage")
    var message: String = ""
}
