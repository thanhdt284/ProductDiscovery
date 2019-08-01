package com.android.productdiscovery.domain.remote.pojo.response

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
enum class SaleStatus(val sale: String) {
    SELLING("hang_ban"),
    STOP_SELLING("ngung_kinh_doanh");

    companion object {
        fun from(findValue: String): SaleStatus = values().first { it.sale == findValue }
    }
}