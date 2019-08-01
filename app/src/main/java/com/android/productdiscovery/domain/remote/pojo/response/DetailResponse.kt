package com.android.productdiscovery.domain.remote.pojo.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * @author ThanhDT
 * @since 2019-08-01
 */
data class DetailResponse(

        @Expose
        @SerializedName("result")
        val result: DetailResult
) : BaseResponse()

data class DetailResult(
        @Expose
        @SerializedName("product")
        val product: Product
)
