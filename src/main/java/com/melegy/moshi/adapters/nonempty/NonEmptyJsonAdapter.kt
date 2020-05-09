package com.melegy.moshi.adapters.nonempty

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

/**
 * [JsonAdapter] that will not serialize `T` when the passed value is empty.
 */
class NonEmptyJsonAdapter<T>(private val delegate: JsonAdapter<T>) : JsonAdapter<T>() {
    override fun fromJson(reader: JsonReader): T? {
        val fromJson = delegate.fromJson(reader)
        return if (isNotEmpty(fromJson)) fromJson
        else throw IllegalArgumentException("unexpected empty field found at path ${reader.path}")
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        if (isNotEmpty(value)) delegate.toJson(writer, value)
        else throw IllegalArgumentException("unexpected empty field found at path ${writer.path}")
    }

    private fun isNotEmpty(value: T?): Boolean {
        return when (value) {
            is String -> value.isNotEmpty()
            is Collection<*> -> value.isNotEmpty()
            is Map<*, *> -> value.isNotEmpty()
            is Array<*> -> value.size != 0
            else -> false
        }
    }

    override fun toString(): String {
        return "$delegate.NonEmpty()"
    }
}

