package com.melegy.moshi.adapters.decimalmax

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Checks whether the annotated value is less than the specified maximum,
 * when inclusive=false.
 * Otherwise whether the value is less than or equal to the specified maximum.
 *
 * The parameter value is the string representation of the max value according to the BigDecimal
 * string representation.
 *
 * This annotation is applicable to [Byte], [Short], [Int] and [Long].
 *
 * To leverage from [DecimalMax] [DecimalMax.ADAPTER_FACTORY] must be added to your [Moshi] instance:
 *
 * ```
 *  val moshi = Moshi.Builder()
 *      .add(DecimalMax.ADAPTER_FACTORY)
 *      .build()
 * ```
 */
@MustBeDocumented
@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class DecimalMax(val value: String, val inclusive: Boolean) {

    companion object {
        /**
         * Builds an adapter that can process types annotated with [DecimalMax]
         */
        val ADAPTER_FACTORY = JsonAdapter.Factory { type, annotations, moshi ->
            val nextAnnotations =
                Types.nextAnnotations(annotations, DecimalMax::class.java)
                    ?: return@Factory null

            val rawType = Types.getRawType(type)

            if (isSupportedType(rawType)) {
                val decimalMax = annotations.filterIsInstance<DecimalMax>().first()
                val maxValue = decimalMax.value
                val inclusive = decimalMax.inclusive
                return@Factory DecimalMaxJsonAdapter<Any>(
                    moshi.adapter(type, nextAnnotations),
                    maxValue,
                    inclusive
                )
            } else {
                throw IllegalArgumentException(
                    "Unsupported type annotated with @DecimalMax." +
                            " Supported Type are: Byte, Short, Int and Long. Found: $type"
                )
            }
        }

        private fun isSupportedType(clazz: Class<*>) = when {
            Byte::class.java.isAssignableFrom(clazz) -> true
            Short::class.java.isAssignableFrom(clazz) -> true
            Int::class.java.isAssignableFrom(clazz) -> true
            Long::class.java.isAssignableFrom(clazz) -> true
            else -> false
        }
    }
}
