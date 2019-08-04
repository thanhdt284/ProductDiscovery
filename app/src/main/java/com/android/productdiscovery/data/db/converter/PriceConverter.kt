package com.android.productdiscovery.data.db.converter

import androidx.room.TypeConverter
import com.android.productdiscovery.domain.remote.pojo.response.Price
import com.google.gson.Gson

/**
 * @author ThanhDT
 * @since 2019-08-04
 */
class PriceConverter {

    /**
     * Convert from open hours list to string
     * @param obj: price obj
     * @return: a string to insert to db
     */
    @TypeConverter
    fun objToString(obj: Price): String = Gson().toJson(obj)

    /**
     * Convert from string to open time list
     * @param str: string from db
     * @return price obj
     */
    @TypeConverter
    fun strToObject(str: String): Price {
        return Gson().fromJson(str, Price::class.java)
    }
}