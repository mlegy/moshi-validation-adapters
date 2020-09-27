package com.melegy.moshi.adapters

import com.melegy.moshi.adapters.assertTrue.AssertTrue
import com.melegy.moshi.adapters.utils.StringBoolean
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AssertTrueJsonAdapterTest {

    // Lazy adapters work only within the context of moshi.
    private val moshi = Moshi.Builder()
        .add(AssertTrue.ADAPTER_FACTORY)
        .add(StringBoolean)
        .build()

    @Test
    fun `AssertTrue should be able to deserialize true booleans`() {
        val adapter = moshi.adapter(Data::class.java)
        val fromJson = requireNotNull(adapter.fromJson("{\"value\": true}"))
        assertEquals(true, fromJson.value)
    }

    @Test
    fun `AssertTrue should be able to serialize false booleans`() {
        val adapter = moshi.adapter(Data::class.java)
        val toJson = requireNotNull(adapter.toJson(Data(value = true)))
        assertEquals("{\"value\":true}", toJson)
    }

    @Test
    fun `AssertTrue should throw when try to deserialize true booleans`() {
        val adapter = moshi.adapter(Data::class.java)
        val exception = assertThrows<IllegalArgumentException> {
            adapter.fromJson("{\"value\": false}")
        }
        assertEquals("expected true at path \$.value but found false", exception.message)
    }

    @Test
    fun `AssertTrue should throw when try to serialize true booleans`() {
        val adapter = moshi.adapter(Data::class.java)
        val exception = assertThrows<IllegalArgumentException> {
            adapter.toJson(Data(value = false))
        }
        assertEquals("expected true at path \$.value but found false", exception.message)
    }

    @Test
    fun `toString() should reflects InnerAdapter`() {
        val adapter = moshi.adapter<AssertTrue>(Boolean::class.java, AssertTrue::class.java)
        assertEquals("JsonAdapter(Boolean).AssertTrue()", adapter.toString())
    }

    @Test
    fun `factory should maintains other annotations`() {
        val adapter = moshi.adapter(DataInvert::class.java)
        val fromJson = requireNotNull(adapter.fromJson("{\"value\":true}"))
        assertEquals(true, fromJson.value)
        assertEquals("{\"value\":\"true\"}", adapter.toJson(fromJson))
    }

    companion object {
        @JsonClass(generateAdapter = true)
        data class Data(@AssertTrue val value: Boolean)

        @JsonClass(generateAdapter = true)
        data class DataInvert(@AssertTrue @StringBoolean val value: Boolean)
    }
}
