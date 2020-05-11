package com.melegy.moshi.adapters.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

/**
 * Helper annotation (to test if JsonQualifier annotations are propagated further down the stream).
 */
@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DoubleValue {
    /**
     * String adapter, that will append double the int value on read, and divide it back on write.
     */
    companion object {
        @DoubleValue
        @FromJson
        fun fromJson(str: String): Int {
            return str.toInt() * 2
        }

        @ToJson
        fun toJson(@DoubleValue str: Int): String {
            return (str / 2).toString()
        }
    }
}
