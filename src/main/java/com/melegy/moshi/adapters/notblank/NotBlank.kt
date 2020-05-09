package com.melegy.moshi.adapters.notblank

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

/**
 * Indicates that the annotated field may not contain blank string.
 * This annotation is applicable to Strings only.
 *
 * To leverage from [NotBlank] [NotBlank.ADAPTER_FACTORY] must be added to your [Moshi] instance:
 *
 * ```
 * val moshi = Moshi.Builder()
 * .add(NotBlank.ADAPTER_FACTORY)
 * .build()
 * ```
 */
@MustBeDocumented
@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class NotBlank {

    companion object {
        /**
         * Builds an adapter that can process types annotated with [NotBlank]
         */
        val ADAPTER_FACTORY = object : JsonAdapter.Factory {
            override fun create(
                type: Type,
                annotations: Set<Annotation>,
                moshi: Moshi
            ): JsonAdapter<*>? {
                val nextAnnotations =
                    Types.nextAnnotations(annotations, NotBlank::class.java) ?: return null
                val isString = String::class.java.isAssignableFrom(Types.getRawType(type))
                if (isString) {
                    return NotBlankJsonAdapter<String>(moshi.adapter(type, nextAnnotations))
                } else {
                    throw IllegalArgumentException(
                        "Unsupported type annotated with @NotEmpty. Expected String found: $type"
                    )
                }
            }
        }
    }
}
