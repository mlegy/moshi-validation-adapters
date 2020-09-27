package com.melegy.moshi.adapters

import com.melegy.moshi.adapters.assertFalse.AssertFalse
import com.melegy.moshi.adapters.utils.StringBoolean
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AssertFalseJsonAdapterTest {

    // Lazy adapters work only within the context of moshi.
    private val moshi = Moshi.Builder()
        .add(AssertFalse.ADAPTER_FACTORY)
        .add(StringBoolean)
        .build()

    @Test
    fun `AssertFalse should be able to deserialize false booleans`() {
        val adapter = moshi.adapter(Data::class.java)
        val fromJson = requireNotNull(adapter.fromJson("{\"value\": false}"))
        assertEquals(false, fromJson.value)
    }

    @Test
    fun `AssertFalse should be able to serialize false booleans`() {
        val adapter = moshi.adapter(Data::class.java)
        val toJson = requireNotNull(adapter.toJson(Data(value = false)))
        assertEquals("{\"value\":false}", toJson)
    }

    @Test
    fun `AssertFalse should throw when try to deserialize true booleans`() {
        val adapter = moshi.adapter(Data::class.java)
        val exception = assertThrows<IllegalArgumentException> {
            adapter.fromJson("{\"value\": true}")
        }
        assertEquals("expected false at path \$.value but found true", exception.message)
    }

    @Test
    fun `AssertFalse should throw when try to serialize true booleans`() {
        val adapter = moshi.adapter(Data::class.java)
        val exception = assertThrows<IllegalArgumentException> {
            adapter.toJson(Data(value = true))
        }
        assertEquals("expected false at path \$.value but found true", exception.message)
    }

    @Test
    fun `toString() should reflects InnerAdapter`() {
        val adapter = moshi.adapter<AssertFalse>(Boolean::class.java, AssertFalse::class.java)
        assertEquals("JsonAdapter(Boolean).AssertFalse()", adapter.toString())
    }

    @Test
    fun `factory should maintains other annotations`() {
        val adapter = moshi.adapter(DataInvert::class.java)
        val fromJson = requireNotNull(adapter.fromJson("{\"value\":false}"))
        assertEquals(false, fromJson.value)
        assertEquals("{\"value\":\"false\"}", adapter.toJson(fromJson))
    }

    companion object {
        @JsonClass(generateAdapter = true)
        data class Data(@AssertFalse val value: Boolean)

        @JsonClass(generateAdapter = true)
        data class DataInvert(@AssertFalse @StringBoolean val value: Boolean)
    }
}
