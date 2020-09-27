package com.melegy.moshi.adapters.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

/**
 * Helper annotation (to test if JsonQualifier annotations are propagated further down the stream).
 */
@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StringBoolean {
    /**
     * Boolean adapter, that will invert value on read and write.
     */
    companion object {
        @StringBoolean
        @FromJson
        fun fromJson(b: Boolean) = b


        @ToJson
        fun toJson(@StringBoolean b: Boolean) = b.toString()
    }
}
