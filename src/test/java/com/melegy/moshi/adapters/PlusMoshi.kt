package com.melegy.moshi.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Helper annotation (to test if JsonQualifier annotations are propagated further down the stream).
 */
@JsonQualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
internal annotation class Multiply {
    /**
     * String adapter, that will append "Test" on read, and exclude it on write.
     */
     class TestAdapter {
        @Multiply
        @FromJson
        fun fromJson(str: String): String {
            return str + "Test"
        }

        @ToJson
        fun toJson(@Multiply str: String): String {
            return str.substring(0, str.length - "Test".length)
        }
    }
}
