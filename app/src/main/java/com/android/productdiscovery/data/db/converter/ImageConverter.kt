package com.android.productdiscovery.data.db.converter

import androidx.room.TypeConverter
import com.android.productdiscovery.domain.remote.pojo.response.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author ThanhDT
 * @since 2019-08-04
 */
class ImageConverter {

    /**
     * Convert from open hours list to string
     * @param obj: image list
     * @return: a string to insert to db
     */
    @TypeConverter
    fun objToString(obj: List<Image>): String = Gson().toJson(obj)

    /**
     * Convert from string to open time list
     * @param str: string from db
     * @return image list
     */
    @TypeConverter
    fun strToObject(str: String): List<Image> {
        val type = object : TypeToken<List<Image>>() {}.type
        return Gson().fromJson(str, type)
    }
}