package com.melegy.moshi.adapters.decimalmax

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

/**
 * [JsonAdapter] that will not serialize `T` when the passed value exceeds [maxValue].
 */
class DecimalMaxJsonAdapter<T>(
    private val delegate: JsonAdapter<T>,
    private val maxValue: String,
    private val inclusive: Boolean
) : JsonAdapter<T>() {

    override fun fromJson(reader: JsonReader): T? {
        val fromJson = delegate.fromJson(reader)
        return if (isValid(fromJson)) fromJson
        else throw IllegalArgumentException(
            "Invalid value at ${reader.path}, Maximum value is $maxValue found $fromJson"
        )
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        if (isValid(value)) delegate.toJson(writer, value)
        else throw IllegalArgumentException(
            "Invalid value at ${writer.path}, Maximum value is $maxValue found $value"
        )
    }

    private fun isValid(value: T?): Boolean {
        return when (value) {
            is Long -> {
                if (inclusive) maxValue.toLong() >= value
                else maxValue.toLong() > value
            }
            is Int -> {
                if (inclusive) maxValue.toInt() >= value
                else maxValue.toInt() > value
            }
            is Byte -> {
                if (inclusive) maxValue.toShort() >= value
                else maxValue.toShort() > value
            }
            is Short -> {
                if (inclusive) maxValue.toShort() >= value
                else maxValue.toShort() > value
            }
            else -> throw IllegalArgumentException("Unexpected type found $value")
        }
    }

    override fun toString(): String {
        return "$delegate.DecimalMax()"
    }
}
