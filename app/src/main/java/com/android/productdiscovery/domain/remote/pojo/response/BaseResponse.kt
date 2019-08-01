package com.android.productdiscovery.domain.remote.pojo.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
open class BaseResponse {

    @Expose
    @SerializedName("code")
    val code: String = ""

//    @SerializedName("massage")
//    val massage: String = ""
}