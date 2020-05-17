package com.melegy.moshi.adapters.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

/**
 * Helper annotation (to test if JsonQualifier annotations are propagated further down the stream).
 */
@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DoubleFloatValue {
    /**
     * String adapter, that will append double the float value on read, and divide it back on write.
     */
    companion object {
        @DoubleFloatValue
        @FromJson
        fun fromJson(str: String): Float {
            return str.toFloat() * 2
        }

        @ToJson
        fun toJson(@DoubleFloatValue value: Float): String {
            return (value / 2).toString()
        }
    }
}
