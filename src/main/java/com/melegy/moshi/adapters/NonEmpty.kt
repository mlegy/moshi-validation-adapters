package com.melegy.moshi.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type
@MustBeDocumented
@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class NonEmpty {
    companion object {
        /**
         * Builds an adapter that can process types annotated with [NonEmpty]
         */
        val ADAPTER_FACTORY: JsonAdapter.Factory = object : JsonAdapter.Factory {
            override fun create(
                type: Type, annotations: Set<Annotation>, moshi: Moshi
            ): JsonAdapter<*>? {
                val nextAnnotations =
                    Types.nextAnnotations(annotations, NonEmpty::class.java) ?: return null
                val rawType = Types.getRawType(type)
                return if (isSupportedType(rawType)) {
                    NonEmptyJsonAdapter<Any>(moshi.adapter(type, nextAnnotations))
                } else {
                    throw IllegalArgumentException(
                        "Unsupported type annotated with @NonEmpty." +
                                " Supported Type are: String, Array, Collection and Map"
                    )
                }
            }
        }
        fun isSupportedType(clazz: Class<*>) = when {
            String::class.java.isAssignableFrom(clazz) -> true
            clazz.isArray -> true
            MutableCollection::class.java.isAssignableFrom(clazz) -> true
            MutableMap::class.java.isAssignableFrom(clazz) -> true
            else -> false
        }
    }
}
