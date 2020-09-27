package com.melegy.moshi.adapters.assertFalse

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

/**
 * [JsonAdapter] that will not serialize `T` when the passed value is empty.
 */
class AssertFalseJsonAdapter<T>(private val delegate: JsonAdapter<T>) : JsonAdapter<T>() {
    override fun fromJson(reader: JsonReader): T? {
        val fromJson = delegate.fromJson(reader)
        return if (fromJson == false) fromJson
        else throw IllegalArgumentException("expected false at path ${reader.path} but found $fromJson")
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        if (value == false) delegate.toJson(writer, value)
        else throw IllegalArgumentException("expected false at path ${writer.path} but found $value")
    }

    override fun toString(): String {
        return "$delegate.AssertFalse()"
    }
}
