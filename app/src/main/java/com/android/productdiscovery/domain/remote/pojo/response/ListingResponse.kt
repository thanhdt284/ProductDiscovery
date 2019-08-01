package com.android.productdiscovery.domain.remote.pojo.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * @author ThanhDT
 * @since 2019-07-31
 */
data class ListingResponse(
        @Expose
        @SerializedName("code")
        val code: String,
        @Expose
        @SerializedName("extra")
        val extra: Extra,
        @Expose
        @SerializedName("result")
        val result: ListingResult
)

data class Extra(
        @Expose
        @SerializedName("page")
        val page: Int,
        @Expose
        @SerializedName("pageSize")
        val pageSize: Int,
        @Expose
        @SerializedName("totalItems")
        val totalItems: Int
)

data class ListingResult(
        @Expose
        @SerializedName("filters")
        val filters: List<String>,
        @Expose
        @SerializedName("keywords")
        val keywords: List<String>,
        @Expose
        @SerializedName("products")
        val products: List<Product>
)