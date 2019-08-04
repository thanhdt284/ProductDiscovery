package com.android.productdiscovery.data.db.converter

import androidx.room.TypeConverter
import com.android.productdiscovery.domain.remote.pojo.response.SeoInfo
import com.google.gson.Gson

/**
 * @author ThanhDT
 * @since 2019-08-04
 */
class SeoInfoConverter {

    /**
     * Convert from open hours list to string
     * @param obj: SeoInfo obj
     * @return: a string to insert to db
     */
    @TypeConverter
    fun objToString(obj: SeoInfo): String = Gson().toJson(obj)

    /**
     * Convert from string to open time list
     * @param str: string from db
     * @return SeoInfo obj
     */
    @TypeConverter
    fun strToObject(str: String): SeoInfo {
        return Gson().fromJson(str, SeoInfo::class.java)
    }
}