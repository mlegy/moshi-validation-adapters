package com.melegy.moshi.adapters

import com.melegy.moshi.adapters.decimalmin.DecimalMin
import com.melegy.moshi.adapters.utils.DoubleValue
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MinDecimalJsonAdapterTest {

    // Lazy adapters work only within the context of moshi.
    private val moshi = Moshi.Builder()
        .add(DecimalMin.ADAPTER_FACTORY)
        .add(DoubleValue)
        .build()

    @Nested
    inner class IntTests {

        @Test
        fun `should be able to deserialize value exceeds min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveInt::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 101}"))
            assertEquals(101, fromJson.value)
        }

        @Test
        fun `should be able to serialize value exceeds min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveInt::class.java)
            val toJson = adapter.toJson(NotInclusiveInt(value = 101))
            assertEquals("{\"value\":101}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value exceeds min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveInt::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 99}")
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should throw when try to deserialize value equals min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveInt::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 100}")
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value below min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveInt::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveInt(value = 99))
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value equals min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveInt::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveInt(value = 100))
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should be able to deserialize value exceeds min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveInt::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 101}"))
            assertEquals(101, fromJson.value)
        }

        @Test
        fun `should be able to serialize value exceeds min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveInt::class.java)
            val toJson = adapter.toJson(InclusiveInt(value = 101))
            assertEquals("{\"value\":101}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value below min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveInt::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 99}")
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value below min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveInt::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(InclusiveInt(value = 99))
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should be able to serialize min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveInt::class.java)
            val toJson = adapter.toJson(InclusiveInt(value = 100))
            assertEquals("{\"value\":100}", toJson)
        }

        @Test
        fun `should be able to deserialize min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveInt::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 100}"))
            assertEquals(100, fromJson.value)
        }
    }

    @Nested
    inner class ByteTests {

        @Test
        fun `should be able to deserialize value exceeds min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveByte::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 101}"))
            assertEquals(101, fromJson.value)
        }

        @Test
        fun `should be able to serialize value exceeds min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveByte::class.java)
            val toJson = adapter.toJson(NotInclusiveByte(value = 101))
            assertEquals("{\"value\":101}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value below min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveByte::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 99}")
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should throw when try to deserialize value equals min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveByte::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 100}")
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value below min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveByte::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveByte(value = 99))
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize equals min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveByte::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveByte(value = 100))
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should be able to deserialize value above min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveByte::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 101}"))
            assertEquals(101, fromJson.value)
        }

        @Test
        fun `should be able to serialize value above min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveByte::class.java)
            val toJson = adapter.toJson(InclusiveByte(value = 100))
            assertEquals("{\"value\":100}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value below min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveByte::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 99}")
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value blow min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveByte::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(InclusiveByte(value = 99))
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should be able to serialize min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveByte::class.java)
            val toJson = adapter.toJson(InclusiveByte(value = 100))
            assertEquals("{\"value\":100}", toJson)
        }

        @Test
        fun `should be able to deserialize min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveByte::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 100}"))
            assertEquals(100, fromJson.value)
        }

    }

    @Nested
    inner class ShortTests {

        @Test
        fun `should be able to deserialize value above min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 101}"))
            assertEquals(101, fromJson.value)
        }

        @Test
        fun `should be able to serialize value above min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val toJson = adapter.toJson(NotInclusiveShort(value = 101))
            assertEquals("{\"value\":101}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value below min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 99}")
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should throw when try to deserialize value equals min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 100}")
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value below min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveShort(value = 99))
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize equals min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveShort(value = 100))
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should be able to deserialize value above min value when inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 101}"))
            assertEquals(101, fromJson.value)
        }

        @Test
        fun `should be able to serialize value above min value when inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val toJson = adapter.toJson(NotInclusiveShort(value = 101))
            assertEquals("{\"value\":101}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value below min value when inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 99}")
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value below min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(InclusiveShort(value = 99))
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should be able to serialize min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveShort::class.java)
            val toJson = adapter.toJson(InclusiveShort(value = 100))
            assertEquals("{\"value\":100}", toJson)
        }

        @Test
        fun `should be able to deserialize min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveShort::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 100}"))
            assertEquals(100, fromJson.value)
        }

    }

    @Nested
    inner class LongTests {

        @Test
        fun `should be able to deserialize value above min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveLong::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 101}"))
            assertEquals(101, fromJson.value)
        }

        @Test
        fun `should be able to serialize value above min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveLong::class.java)
            val toJson = adapter.toJson(NotInclusiveLong(value = 101))
            assertEquals("{\"value\":101}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value below min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveLong::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 99}")
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should throw when try to deserialize value equals min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveLong::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 100}")
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value below min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveLong::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveLong(value = 99))
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize equals min value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveLong::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveLong(value = 100))
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should be able to deserialize value above min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveLong::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 100}"))
            assertEquals(100, fromJson.value)
        }

        @Test
        fun `should be able to serialize value above min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveLong::class.java)
            val toJson = adapter.toJson(InclusiveLong(value = 100))
            assertEquals("{\"value\":100}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value below min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveLong::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 99}")
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value below min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(InclusiveShort(value = 99))
            }
            assertEquals(
                "Invalid value at \$.value, Minimum value is 100 found 99",
                exception.message
            )
        }

        @Test
        fun `should be able to serialize min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveShort::class.java)
            val toJson = adapter.toJson(InclusiveShort(value = 100))
            assertEquals("{\"value\":100}", toJson)
        }

        @Test
        fun `should be able to deserialize min value when inclusive`() {
            val adapter = moshi.adapter(InclusiveShort::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 100}"))
            assertEquals(100, fromJson.value)
        }
    }

    @Test
    fun `factory should maintain other annotations`() {
        val adapter = moshi.adapter(Double::class.java)
        val fromJson = requireNotNull(adapter.fromJson("{\"value\": 1}"))
        assertEquals(2, fromJson.value)
        assertEquals("{\"value\":\"1\"}", adapter.toJson(fromJson))
    }

    companion object {
        @JsonClass(generateAdapter = true)
        data class NotInclusiveInt(@DecimalMin(value = "100", inclusive = false) val value: Int)

        @JsonClass(generateAdapter = true)
        data class InclusiveInt(@DecimalMin(value = "100", inclusive = true) val value: Int)

        @JsonClass(generateAdapter = true)
        data class NotInclusiveByte(@DecimalMin(value = "100", inclusive = false) val value: Byte)

        @JsonClass(generateAdapter = true)
        data class InclusiveByte(@DecimalMin(value = "100", inclusive = true) val value: Byte)

        @JsonClass(generateAdapter = true)
        data class NotInclusiveShort(@DecimalMin(value = "100", inclusive = false) val value: Short)

        @JsonClass(generateAdapter = true)
        data class InclusiveShort(@DecimalMin(value = "100", inclusive = true) val value: Short)

        @JsonClass(generateAdapter = true)
        data class NotInclusiveLong(@DecimalMin(value = "100", inclusive = false) val value: Long)

        @JsonClass(generateAdapter = true)
        data class InclusiveLong(@DecimalMin(value = "100", inclusive = true) val value: Long)

        @JsonClass(generateAdapter = true)
        data class Double(
            @DecimalMin(value = "1", inclusive = true)
            @DoubleValue val value: Int
        )
    }
}
