package com.melegy.moshi.adapters.assertTrue

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

/**
 * [JsonAdapter] that will not serialize `T` when the passed value is True.
 */
class AssertTrueJsonAdapter<T>(private val delegate: JsonAdapter<T>) : JsonAdapter<T>() {
    override fun fromJson(reader: JsonReader): T? {
        val fromJson = delegate.fromJson(reader)
        return if (fromJson == true) fromJson
        else throw IllegalArgumentException("expected true at path ${reader.path} but found $fromJson")
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        if (value == true) delegate.toJson(writer, value)
        else throw IllegalArgumentException("expected true at path ${writer.path} but found $value")
    }

    override fun toString(): String {
        return "$delegate.AssertTrue()"
    }
}
