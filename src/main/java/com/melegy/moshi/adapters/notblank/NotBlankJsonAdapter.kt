package com.melegy.moshi.adapters.notblank

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

/**
 * [JsonAdapter] that will not serialize `T` when the passed value is blank.
 */
class NotBlankJsonAdapter<T>(private val delegate: JsonAdapter<T>) : JsonAdapter<T>() {
    override fun fromJson(reader: JsonReader): T? {
        val fromJson = delegate.fromJson(reader)
        return if (isNotBlank(fromJson)) fromJson
        else throw IllegalArgumentException("unexpected blank field found at path ${reader.path}")
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        if (isNotBlank(value)) delegate.toJson(writer, value)
        else throw IllegalArgumentException("unexpected blank field found at path ${writer.path}")
    }

    private fun isNotBlank(value: T?): Boolean {
        return when (value) {
            is String -> value.isNotBlank()
            else -> throw IllegalArgumentException("Expected String found $value")
        }
    }

    override fun toString(): String {
        return "$delegate.NotBlank()"
    }
}
