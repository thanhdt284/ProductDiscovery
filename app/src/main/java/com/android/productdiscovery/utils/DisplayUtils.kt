package com.android.productdiscovery.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.Html
import android.text.Spanned
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import android.view.WindowManager
import androidx.core.os.ConfigurationCompat
import java.io.ByteArrayOutputStream
import java.lang.reflect.InvocationTargetException
import java.text.DecimalFormat
import java.text.Normalizer
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern


/**
 * @author ThanhDT
 * @since 4/19/18
 */
object DisplayUtils {


    var screenWidth = 0
    var screenHeight = 0

    fun calculateViewsSize(context: Context) {
        val screenSize = getAppUsableScreenSize(context)

        screenWidth = context.resources.displayMetrics.widthPixels
        screenHeight = context.resources.displayMetrics.heightPixels
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Avatar", null)
        bytes.close()
        bytes.flush()
        return Uri.parse(path)
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun currencyFormat(currency: Double?): String {
        val locale = Locale("vi", "VN")
        val format = NumberFormat.getCurrencyInstance(locale)
        val decimalFormatSymbols = (format as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        format.decimalFormatSymbols = decimalFormatSymbols
        return format.format(currency).trim()
    }

    fun getFakeBalance(currency: Double?): String {
        return if (currency != null) {
            (currency / 1000).toInt().toString()
        } else {
            "0"
        }
    }

    fun fromHtml(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }

    fun getCurrentLanguageCode(): String {
        return ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0).language
    }

    fun getNavigationBarSize(context: Context): Point {
        val appUsableSize = getAppUsableScreenSize(context)
        val realScreenSize = getRealScreenSize(context)

        // navigation bar on the right
        if (appUsableSize.x < realScreenSize.x) {
            return Point(realScreenSize.x - appUsableSize.x, appUsableSize.y)
        }

        // navigation bar at the bottom
        return if (appUsableSize.y < realScreenSize.y) {
            Point(appUsableSize.x, realScreenSize.y - appUsableSize.y)
        } else Point()

        // navigation bar is not present
    }

    fun getAppUsableScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

    fun isWhole(value: Double): Boolean {
        return Math.floor(value) == value
    }

    fun getRealScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size)
        } else if (Build.VERSION.SDK_INT >= 16) {
            try {
                size.x = Display::class.java.getMethod("getRawWidth").invoke(display) as Int
                size.y = Display::class.java.getMethod("getRawHeight").invoke(display) as Int
            } catch (e: IllegalAccessException) {
            } catch (e: InvocationTargetException) {
            } catch (e: NoSuchMethodException) {
            }

        }

        return size
    }

    fun getDisplaySize(windowManager: WindowManager): Point {
        return try {
            if (Build.VERSION.SDK_INT > 16) {
                val display = windowManager.defaultDisplay
                val displayMetrics = DisplayMetrics()
                display.getMetrics(displayMetrics)
                Point(displayMetrics.widthPixels, displayMetrics.heightPixels)
            } else {
                Point(0, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Point(0, 0)
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