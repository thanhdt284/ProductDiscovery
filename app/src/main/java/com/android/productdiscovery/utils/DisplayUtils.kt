package com.android.productdiscovery.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.TypedValue
import java.text.Normalizer
import java.util.regex.Pattern


/**
 * @author ThanhDT
 * @since 2019-07-28
 */
object DisplayUtils {

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }


    fun fromHtml(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }

    fun spToPx(context: Context, sp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics).toInt()
    }

    fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }

    fun unAccent(s: String): String {
        val temp = Normalizer.normalize(s, Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        //        return pattern.matcher(temp).replaceAll("");
        return pattern.matcher(temp).replaceAll("").replace("Đ", "D").replace("đ", "d")
    }
}