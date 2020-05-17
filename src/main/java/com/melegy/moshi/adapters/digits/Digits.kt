package com.melegy.moshi.adapters.digits

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

/**
 * Checks whether the annotated value is a number having up to integer [digits]
 * and [fraction] fractional digits.
 *
 * This annotation is applicable to [Float] and [Double].
 *
 * To leverage from [Digits] [Digits.ADAPTER_FACTORY] must be added to your [Moshi] instance:
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
annotation class Digits(val integer: Int, val fraction: Int) {

    companion object {
        /**
         * Builds an adapter that can process types annotated with [Digits]
         */
        val ADAPTER_FACTORY = JsonAdapter.Factory { type, annotations, moshi ->
            val nextAnnotations =
                Types.nextAnnotations(annotations, Digits::class.java)
                    ?: return@Factory null

            val rawType = Types.getRawType(type)
            if (isSupportedType(rawType)) {
                val digits = annotations.filterIsInstance<Digits>().first()
                val integer = digits.integer
                val fraction = digits.fraction

                validateParams(integer, fraction, type, rawType)

                return@Factory DigitsJsonAdapter<Any>(
                    moshi.adapter(type, nextAnnotations),
                    integer,
                    fraction
                )
            } else {
                throw IllegalArgumentException(
                    "Unsupported type annotated with @Digits." +
                            " Supported Type are: Float and Double." +
                            " Found: $type"
                )
            }
        }

        private fun isSupportedType(clazz: Class<*>) = when {
            Float::class.java.isAssignableFrom(clazz) -> true
            Double::class.java.isAssignableFrom(clazz) -> true
            else -> false
        }

        private fun validateParams(integer: Int, fraction: Int, type: Type, rawType: Class<*>) {
            require(integer >= 0) {
                "Invalid integer value for type $type, expected 0 or greater value found $integer"
            }
            require(fraction > 0) {
                "Invalid fraction value for type $type, expected positive value found $fraction"
            }
        }
    }
}
