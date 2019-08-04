package com.android.productdiscovery.data.db.converter

import androidx.room.TypeConverter
import com.android.productdiscovery.domain.remote.pojo.response.Status
import com.google.gson.Gson

/**
 * @author ThanhDT
 * @since 2019-08-04
 */
class StatusConverter {

    /**
     * Convert from open hours list to string
     * @param obj: status obj
     * @return: a string to insert to db
     */
    @TypeConverter
    fun objToString(obj: Status?): String {
        return if (obj == null) {
            ""
        } else {
            Gson().toJson(obj)
        }
    }

    /**
     * Convert from string to open time list
     * @param str: string from db
     * @return status obj
     */
    @TypeConverter
    fun strToObject(str: String): Status {
        if (str.isBlank()) {
            return Status()
        }
        return Gson().fromJson(str, Status::class.java)
    }
}