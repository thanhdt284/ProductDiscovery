package com.android.productdiscovery.domain.remote.api

import androidx.annotation.StringRes
import android.util.SparseIntArray
import com.android.productdiscovery.R

/**
 * @author ThanhDT
 * @since 4/17/18
 */
object ApiErrorCode {

    private val sparseCode: SparseIntArray = SparseIntArray()

    init {

//        sparseCode.put(100, R.string.error_100)
//        sparseCode.put(101, R.string.error_101)
//        sparseCode.put(102, R.string.error_102)
//        sparseCode.put(201, R.string.error_201)
//        sparseCode.put(202, R.string.error_202)
//        sparseCode.put(203, R.string.error_203)
//        sparseCode.put(204, R.string.error_204)
//        sparseCode.put(205, R.string.error_205)
//        sparseCode.put(206, R.string.error_206)
//        sparseCode.put(207, R.string.error_207)
//        sparseCode.put(208, R.string.error_208)
//        sparseCode.put(209, R.string.error_209)
//        sparseCode.put(210, R.string.error_210)
//        sparseCode.put(211, R.string.error_211)
//        sparseCode.put(301, R.string.error_301)
//        sparseCode.put(302, R.string.error_302)
//        sparseCode.put(404, R.string.error_404)
//        sparseCode.put(600, R.string.error_600)
//        sparseCode.put(800, R.string.error_800)
//        sparseCode.put(801, R.string.error_801)
//        sparseCode.put(802, R.string.error_802)
//        sparseCode.put(803, R.string.error_803)
//        sparseCode.put(804, R.string.error_804)
//        sparseCode.put(805, R.string.error_805)
//        sparseCode.put(806, R.string.error_806)
//        sparseCode.put(807, R.string.error_807)
    }

    @StringRes
    fun parseMsg(code: Int): Int {
        val msgId = sparseCode.get(code)
        return if (msgId > 0) {
            msgId
        } else {
            R.string.error_default // default error message
        }
    }
}