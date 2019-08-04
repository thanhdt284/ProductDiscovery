package com.android.productdiscovery.data.db.converter

import androidx.room.TypeConverter
import com.android.productdiscovery.domain.remote.pojo.response.AttributeGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author ThanhDT
 * @since 2019-08-04
 */
class AttributeGroupConverter {

    /**
     * Convert from open hours list to string
     * @param obj: attribute group list
     * @return: a string to insert to db
     */
    @TypeConverter
    fun objToString(obj: List<AttributeGroup>): String = Gson().toJson(obj)

    /**
     * Convert from string to open time list
     * @param str: string from db
     * @return attribute group list
     */
    @TypeConverter
    fun strToObject(str: String): List<AttributeGroup> {
        val type = object : TypeToken<List<AttributeGroup>>() {}.type
        return Gson().fromJson(str, type)
    }
}