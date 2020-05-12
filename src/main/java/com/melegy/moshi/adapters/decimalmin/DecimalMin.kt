package com.melegy.moshi.adapters.decimalmin

import com.melegy.moshi.adapters.decimalmax.DecimalMaxJsonAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Checks whether the annotated value is larger than the specified minimum
 * when inclusive=false.
 * Otherwise whether the value is less than or equal to the specified maximum.
 *
 * The parameter value is the string representation of the max value according to the BigDecimal
 * string representation.
 *
 * This annotation is applicable to [Byte], [Short], [Int] and [Long].
 *
 * To leverage from [DecimalMin] [DecimalMin.ADAPTER_FACTORY] must be added to your [Moshi] instance:
 *
 * ```
 *  val moshi = Moshi.Builder()
 *      .add(DecimalMin.ADAPTER_FACTORY)
 *      .build()
 * ```
 *
 * @see DecimalMinJsonAdapter
 */
@MustBeDocumented
@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class DecimalMin(val value: String, val inclusive: Boolean) {

    companion object {
        /**
         * Builds an adapter that can process types annotated with [DecimalMin]
         */
        val ADAPTER_FACTORY = JsonAdapter.Factory { type, annotations, moshi ->
            val nextAnnotations =
                Types.nextAnnotations(annotations, DecimalMin::class.java)
                    ?: return@Factory null

            val rawType = Types.getRawType(type)

            if (isSupportedType(rawType)) {
                val decimalMin = annotations.filterIsInstance<DecimalMin>().first()
                val maxValue = decimalMin.value
                val inclusive = decimalMin.inclusive
                return@Factory DecimalMinJsonAdapter<Any>(
                    moshi.adapter(type, nextAnnotations),
                    maxValue,
                    inclusive
                )
            } else {
                throw IllegalArgumentException(
                    "Unsupported type annotated with @DecimalMin." +
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
