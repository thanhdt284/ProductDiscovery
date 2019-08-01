package com.android.productdiscovery.domain.remote.api

import com.android.productdiscovery.domain.remote.pojo.response.ListingResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * @author ThanhDT
 * @since 2019-07-28
 */
interface ApiService {

    @GET("search/")
    fun searchProduct(@Query("q") query: String = "",
                      @Query("_page") page: Int = 1,
                      @Query("_limit") limit: Int = 20,
                      @Query("channel") channel: String = "pv_online",
                      @Query("visitorId") visitorId: String = "",
                      @Query("terminal") terminal: String = "CP01"): Observable<ListingResponse>

    @GET("products/{sku}")
    fun getProductDetail(@Path("sku") sku: String = "")

}