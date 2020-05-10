package com.melegy.moshi.adapters

import com.melegy.moshi.adapters.decimalmax.DecimalMax
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MaxDecimalJsonAdapterTest {

    // Lazy adapters work only within the context of moshi.
    private val moshi = Moshi.Builder()
        .add(DecimalMax.ADAPTER_FACTORY)
        .add(PlusMoshi)
        .build()

    @Nested
    inner class IntTests {

        @Test
        fun `should be able to deserialize value below max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveInt::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 99}"))
            assertEquals(99, fromJson.value)
        }

        @Test
        fun `should be able to serialize value below max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveInt::class.java)
            val toJson = adapter.toJson(NotInclusiveInt(value = 99))
            assertEquals("{\"value\":99}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value above max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveInt::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 101}")
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should throw when try to deserialize value equals max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveInt::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 100}")
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value above max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveInt::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveInt(value = 101))
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize equals max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveInt::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveInt(value = 100))
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should be able to deserialize value below max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveInt::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 99}"))
            assertEquals(99, fromJson.value)
        }

        @Test
        fun `should be able to serialize value below max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveInt::class.java)
            val toJson = adapter.toJson(InclusiveInt(value = 99))
            assertEquals("{\"value\":99}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value above max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveInt::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 101}")
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value above max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveInt::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(InclusiveInt(value = 101))
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should be able to serialize max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveInt::class.java)
            val toJson = adapter.toJson(InclusiveInt(value = 100))
            assertEquals("{\"value\":100}", toJson)
        }

        @Test
        fun `should be able to deserialize max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveInt::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 100}"))
            assertEquals(100, fromJson.value)
        }
    }

    @Nested
    inner class ByteTests {

        @Test
        fun `should be able to deserialize value below max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveByte::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 99}"))
            assertEquals(99, fromJson.value)
        }

        @Test
        fun `should be able to serialize value below max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveByte::class.java)
            val toJson = adapter.toJson(NotInclusiveByte(value = 99))
            assertEquals("{\"value\":99}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value above max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveByte::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 101}")
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should throw when try to deserialize value equals max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveByte::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 100}")
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value above max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveByte::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveByte(value = 101))
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize equals max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveByte::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveByte(value = 100))
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should be able to deserialize value below max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveByte::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 99}"))
            assertEquals(99, fromJson.value)
        }

        @Test
        fun `should be able to serialize value below max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveByte::class.java)
            val toJson = adapter.toJson(InclusiveByte(value = 99))
            assertEquals("{\"value\":99}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value above max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveByte::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 101}")
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value above max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveByte::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(InclusiveByte(value = 101))
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should be able to serialize max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveByte::class.java)
            val toJson = adapter.toJson(InclusiveByte(value = 100))
            assertEquals("{\"value\":100}", toJson)
        }

        @Test
        fun `should be able to deserialize max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveByte::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 100}"))
            assertEquals(100, fromJson.value)
        }

    }

    @Nested
    inner class ShortTests {

        @Test
        fun `should be able to deserialize value below max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 99}"))
            assertEquals(99, fromJson.value)
        }

        @Test
        fun `should be able to serialize value below max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val toJson = adapter.toJson(NotInclusiveShort(value = 99))
            assertEquals("{\"value\":99}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value above max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 101}")
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should throw when try to deserialize value equals max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 100}")
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value above max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveShort(value = 101))
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize equals max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveShort(value = 100))
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should be able to deserialize value below max value when inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 99}"))
            assertEquals(99, fromJson.value)
        }

        @Test
        fun `should be able to serialize value below max value when inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val toJson = adapter.toJson(NotInclusiveShort(value = 99))
            assertEquals("{\"value\":99}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value above max value when inclusive`() {
            val adapter = moshi.adapter(NotInclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 101}")
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value above max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(InclusiveShort(value = 101))
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should be able to serialize max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveShort::class.java)
            val toJson = adapter.toJson(InclusiveShort(value = 100))
            assertEquals("{\"value\":100}", toJson)
        }

        @Test
        fun `should be able to deserialize max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveShort::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 100}"))
            assertEquals(100, fromJson.value)
        }

    }

    @Nested
    inner class LongTests {

        @Test
        fun `should be able to deserialize value below max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveLong::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 99}"))
            assertEquals(99, fromJson.value)
        }

        @Test
        fun `should be able to serialize value below max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveLong::class.java)
            val toJson = adapter.toJson(NotInclusiveLong(value = 99))
            assertEquals("{\"value\":99}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value above max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveLong::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 101}")
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should throw when try to deserialize value equals max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveLong::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 100}")
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value above max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveLong::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveLong(value = 101))
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize equals max value when not inclusive`() {
            val adapter = moshi.adapter(NotInclusiveLong::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NotInclusiveLong(value = 100))
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 100",
                exception.message
            )
        }

        @Test
        fun `should be able to deserialize value below max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveLong::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 99}"))
            assertEquals(99, fromJson.value)
        }

        @Test
        fun `should be able to serialize value below max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveLong::class.java)
            val toJson = adapter.toJson(InclusiveLong(value = 99))
            assertEquals("{\"value\":99}", toJson)
        }

        @Test
        fun `should throw when try to deserialize value above max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveLong::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"value\": 101}")
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should throw when try to serialize value above max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveShort::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(InclusiveShort(value = 101))
            }
            assertEquals(
                "Invalid value at \$.value, Maximum value is 100 found 101",
                exception.message
            )
        }

        @Test
        fun `should be able to serialize max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveShort::class.java)
            val toJson = adapter.toJson(InclusiveShort(value = 100))
            assertEquals("{\"value\":100}", toJson)
        }

        @Test
        fun `should be able to deserialize max value when inclusive`() {
            val adapter = moshi.adapter(InclusiveShort::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\": 100}"))
            assertEquals(100, fromJson.value)
        }
    }


    companion object {
        @JsonClass(generateAdapter = true)
        data class NotInclusiveInt(@DecimalMax(value = "100", inclusive = false) val value: Int)

        @JsonClass(generateAdapter = true)
        data class InclusiveInt(@DecimalMax(value = "100", inclusive = true) val value: Int)

        @JsonClass(generateAdapter = true)
        data class NotInclusiveByte(@DecimalMax(value = "100", inclusive = false) val value: Byte)

        @JsonClass(generateAdapter = true)
        data class InclusiveByte(@DecimalMax(value = "100", inclusive = true) val value: Byte)

        @JsonClass(generateAdapter = true)
        data class NotInclusiveShort(@DecimalMax(value = "100", inclusive = false) val value: Short)

        @JsonClass(generateAdapter = true)
        data class InclusiveShort(@DecimalMax(value = "100", inclusive = true) val value: Short)

        @JsonClass(generateAdapter = true)
        data class NotInclusiveLong(@DecimalMax(value = "100", inclusive = false) val value: Long)

        @JsonClass(generateAdapter = true)
        data class InclusiveLong(@DecimalMax(value = "100", inclusive = true) val value: Long)
    }
}
