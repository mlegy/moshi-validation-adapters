package com.melegy.moshi.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

/**
 * Helper annotation (to test if JsonQualifier annotations are propagated further down the stream).
 */
@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PlusMoshi {
    /**
     * String adapter, that will append "Moshi" on read, and exclude it on write.
     */
    companion object {
        @PlusMoshi
        @FromJson
        fun fromJson(str: String): String {
            return str + "Moshi"
        }

        @ToJson
        fun toJson(@PlusMoshi str: String): String {
            return str.substring(0, str.length - "Moshi".length)
        }
    }
}
