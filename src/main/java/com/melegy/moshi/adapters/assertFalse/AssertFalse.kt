package com.melegy.moshi.adapters.assertFalse

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Indicates that the annotated field is False.
 * This annotation is applicable to Booleans only.
 *
 * To leverage from [AssertFalse] [AssertFalse.ADAPTER_FACTORY] must be added to your [Moshi] instance:
 *
 * ```
 * val moshi = Moshi.Builder()
 * .add(AssertFalse.ADAPTER_FACTORY)
 * .build()
 * ```
 */
@MustBeDocumented
@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class AssertFalse {

    companion object {
        /**
         * Builds an adapter that can process types annotated with [AssertFalse]
         */
        val ADAPTER_FACTORY = JsonAdapter.Factory { type, annotations, moshi ->
            val nextAnnotations =
                Types.nextAnnotations(annotations, AssertFalse::class.java) ?: return@Factory null
            val rawType = Types.getRawType(type)
            if (isSupportedType(rawType)) {
                AssertFalseJsonAdapter<Any>(moshi.adapter(type, nextAnnotations))
            } else {
                throw IllegalArgumentException(
                    "Unsupported type annotated with @AssertFalse." +
                            " Supported type is only Boolean. Found: $type"
                )
            }
        }

        private fun isSupportedType(clazz: Class<*>) = when {
            Boolean::class.java.isAssignableFrom(clazz) -> true
            else -> false
        }
    }
}
