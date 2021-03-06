package com.melegy.moshi.adapters.nonempty

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Indicates that the annotated field may not contain any empty values.
 * This annotation is applicable to Strings, Lists, Arrays, Maps and Collections.
 *
 * To leverage from [NonEmpty] [NonEmpty.ADAPTER_FACTORY] must be added to your [Moshi] instance:
 *
 * ```
 * val moshi = Moshi.Builder()
 * .add(NonEmpty.ADAPTER_FACTORY)
 * .build()
 * ```
 */
@MustBeDocumented
@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class NonEmpty {

    companion object {
        /**
         * Builds an adapter that can process types annotated with [NonEmpty]
         */
        val ADAPTER_FACTORY = JsonAdapter.Factory { type, annotations, moshi ->
            val nextAnnotations =
                Types.nextAnnotations(annotations, NonEmpty::class.java) ?: return@Factory null
            val rawType = Types.getRawType(type)
            if (isSupportedType(rawType)) {
                NonEmptyJsonAdapter<Any>(moshi.adapter(type, nextAnnotations))
            } else {
                throw IllegalArgumentException(
                    "Unsupported type annotated with @NonEmpty." +
                            " Supported Type are: String, Array, Collection and Map. Found: $type"
                )
            }
        }

        private fun isSupportedType(clazz: Class<*>) = when {
            String::class.java.isAssignableFrom(clazz) -> true
            clazz.isArray -> true
            MutableCollection::class.java.isAssignableFrom(clazz) -> true
            MutableMap::class.java.isAssignableFrom(clazz) -> true
            else -> false
        }
    }
}
