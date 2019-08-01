package com.android.productdiscovery.utils

import java.text.NumberFormat
import java.util.*

/**
 * @author ThanhDT
 * @since 5/15/18
 */
object CurrencyUtils {

    fun formatCurrency(money: Double, currencyCode: String = "VND", withCurrency: Boolean = true): String {

        when (currencyCode) {
            "VND" -> {
                val vnLocale = getLocale(currencyCode)
//                val currency = Currency.getInstance(currencyCode)

                val formatter = NumberFormat.getInstance(vnLocale)
                formatter.maximumFractionDigits = 0
                return formatter.format(money) + if (withCurrency) " đ" else ""
            }
        }

        val formatter = NumberFormat.getCurrencyInstance(getLocale(currencyCode))
        return formatter.format(money)
    }

    private fun getLocale(currencyCode: String): Locale {
        return when (currencyCode) {
            "VND" -> Locale("vi", "VN")
            "USD" -> Locale.US
            "KRW" -> Locale.KOREA

            else -> Locale.US
        }
    }
}