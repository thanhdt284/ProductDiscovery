package com.android.productdiscovery.domain.remote.pojo.response

/**
 * @author ThanhDT
 * @since 2019-08-01
 */
enum class SellingStatus(val sale: String) {
    SELLING("hang_ban"),
    OUT_OF_STOCK("tam_het"),
    SAMPLE("hang_trung_bay"),
    STOP_SELLING("ngung_kinh_doanh");

    companion object {
        fun from(findValue: String): SellingStatus = values().find { it.sale == findValue } ?: SAMPLE
    }
}