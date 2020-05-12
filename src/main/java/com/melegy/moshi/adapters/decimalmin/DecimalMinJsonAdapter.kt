package com.melegy.moshi.adapters.decimalmin

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

/**
 * [JsonAdapter] that will not serialize `T` when the passed value is less than [minValue].
 *
 * @see DecimalMin
 */
class DecimalMinJsonAdapter<T>(
    private val delegate: JsonAdapter<T>,
    private val minValue: String,
    private val inclusive: Boolean
) : JsonAdapter<T>() {

    override fun fromJson(reader: JsonReader): T? {
        val fromJson = delegate.fromJson(reader)
        return if (isValid(fromJson)) fromJson
        else throw IllegalArgumentException(
            "Invalid value at ${reader.path}, Minimum value is $minValue found $fromJson"
        )
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        if (isValid(value)) delegate.toJson(writer, value)
        else throw IllegalArgumentException(
            "Invalid value at ${writer.path}, Minimum value is $minValue found $value"
        )
    }

    private fun isValid(value: T?): Boolean {
        return when (value) {
            is Long -> {
                if (inclusive) minValue.toLong() <= value
                else minValue.toLong() < value
            }
            is Int -> {
                if (inclusive) minValue.toInt() <= value
                else minValue.toInt() < value
            }
            is Byte -> {
                if (inclusive) minValue.toShort() <= value
                else minValue.toShort() < value
            }
            is Short -> {
                if (inclusive) minValue.toShort() <= value
                else minValue.toShort() < value
            }
            else -> throw IllegalArgumentException("Unexpected type found $value")
        }
    }

    override fun toString(): String {
        return "$delegate.DecimalMin()"
    }
}
