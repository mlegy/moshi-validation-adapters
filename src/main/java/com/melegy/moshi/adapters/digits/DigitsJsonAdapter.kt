package com.melegy.moshi.adapters.digits

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

/**
 * [JsonAdapter] that will not serialize `T` when the passed value is doesn't meet the rules.
 *
 * @see Digits
 */
class DigitsJsonAdapter<T>(
    private val delegate: JsonAdapter<T>,
    private val requiredIntegersCount: Int,
    private val requiredFractionsCount: Int
) : JsonAdapter<T>() {

    override fun fromJson(reader: JsonReader): T? {
        val fromJson = delegate.fromJson(reader)
        val (integers, fractions) = count(fromJson)
        if (integers != requiredIntegersCount) {
            throw throw IllegalArgumentException(
                "Invalid value at ${reader.path}, Expected integers count: $requiredIntegersCount" +
                        " found $integers"
            )
        }
        if (fractions != requiredFractionsCount) {
            throw throw IllegalArgumentException(
                "Invalid value at ${reader.path}, Expected fractions count: $requiredFractionsCount" +
                        " found $fractions"
            )
        }
        return fromJson
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        val (integers, fractions) = count(value)
        if (integers != requiredIntegersCount) {
            throw throw IllegalArgumentException(
                "Invalid value at ${writer.path}, Expected integers count: $requiredIntegersCount" +
                        " found $integers"
            )
        }
        if (fractions != requiredFractionsCount) {
            throw throw IllegalArgumentException(
                "Invalid value at ${writer.path}, Expected fractions count: $requiredFractionsCount" +
                        " found $fractions"
            )
        }
        delegate.toJson(writer, value)
    }

    private fun count(value: T?): Pair<Int, Int> {
        val bigDecimal = when (value) {
            is Float -> value.toBigDecimal()
            is Double -> value.toBigDecimal()
            else -> throw IllegalArgumentException("Unexpected type found $value")
        }
        val originalScale = bigDecimal.scale()
        val precision = bigDecimal.precision() - originalScale
        val scale = if (originalScale < 0) 0 else originalScale
        return precision to scale
    }

    override fun toString(): String {
        return "$delegate.Digits()"
    }

}
