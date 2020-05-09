package com.melegy.moshi.adapters

import com.melegy.moshi.adapters.notblank.NotBlank
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NotBlankJsonAdapterTest {

    // Lazy adapters work only within the context of moshi.
    private val moshi = Moshi.Builder()
        .add(NotBlank.ADAPTER_FACTORY)
        .add(PlusMoshi)
        .build()

    @Test
    fun `NonBlank should be able to deserialize non blank strings`() {
        val adapter = moshi.adapter(NotBlankString::class.java)
        val nonEmpty = requireNotNull(adapter.fromJson("{\"string\": \"test\"}"))
        assertEquals(nonEmpty.string, "test")
    }

    @Test
    fun `NonBlank should be able to serialize non blank strings`() {
        val adapter = moshi.adapter(NotBlankString::class.java)
        val nonEmpty = requireNotNull(adapter.toJson(NotBlankString(string = "test")))
        assertEquals(nonEmpty, "{\"string\":\"test\"}")
    }

    @Test
    fun `NonBlank should throw when try to deserialize blank string`() {
        val adapter = moshi.adapter(NotBlankString::class.java)
        val exception = assertThrows<IllegalArgumentException> {
            adapter.fromJson("{\"string\": \" \"}")
        }
        assertEquals("unexpected blank field found at path \$.string", exception.message)
    }

    @Test
    fun `NonBlank should throw when try to serialize blank string`() {
        val adapter = moshi.adapter(NotBlankString::class.java)
        val exception = assertThrows<IllegalArgumentException> {
            adapter.toJson(NotBlankString(string = " "))
        }
        assertEquals("unexpected blank field found at path \$.string", exception.message)
    }

    @Test
    fun `toString() should reflects InnerAdapter`() {
        val adapter = moshi.adapter<NotBlank>(String::class.java, NotBlank::class.java)
        assertEquals(adapter.toString(), "JsonAdapter(String).nullSafe().NotBlank()")
    }

    @Test
    fun `factory should maintains other annotations`() {
        val adapter = moshi.adapter(NonBlankPlusMoshi::class.java)
        val model = requireNotNull(adapter.fromJson("{\"string\": \"test\"}"))
        assertEquals(model.string, "testMoshi")
        assertEquals(adapter.toJson(model), "{\"string\":\"test\"}")
    }

    companion object {
        @JsonClass(generateAdapter = true)
        data class NotBlankString(@NotBlank val string: String)

        @JsonClass(generateAdapter = true)
        data class NonBlankPlusMoshi(@NotBlank @PlusMoshi val string: String)
    }
}
