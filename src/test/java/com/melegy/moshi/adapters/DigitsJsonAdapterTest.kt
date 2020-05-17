package com.melegy.moshi.adapters

import com.melegy.moshi.adapters.digits.Digits
import com.melegy.moshi.adapters.utils.DoubleFloatValue
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class DigitsJsonAdapterTest {

    // Lazy adapters work only within the context of moshi.
    private val moshi = Moshi.Builder()
        .add(Digits.ADAPTER_FACTORY)
        .add(DoubleFloatValue)
        .build()

    @Nested
    inner class FloatTests {
        @Test
        fun `should be able to deserialize float values with valid integers and fraction values`() {
            val adapter = moshi.adapter(ValidFloatValue::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\":9.1}"))
            assertEquals(ValidFloatValue(value = 9.1f), fromJson)
        }

        @Test
        fun `should be able to serialize float values with valid integers and fraction values`() {
            val adapter = moshi.adapter(ValidFloatValue::class.java)
            val toJson = requireNotNull(adapter.toJson(ValidFloatValue(value = 9.1f)))
            assertEquals("{\"value\":9.1}", toJson)
        }

        @Test
        fun `should throw when integers value is not equal to the target one`() {
            val adapter = moshi.adapter(ValidFloatValue::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(ValidFloatValue(value = 10f))
            }
            assertEquals(
                "Invalid value at \$.value, Expected integers count: 1 found 2",
                exception.message
            )
        }

        @Test
        fun `should throw when integers value is invalid`() {
            val exception = assertThrows<IllegalArgumentException> {
                moshi.adapter(InvalidFloatIntegerValue::class.java)
            }
            assertTrue {
                exception.message!!.contains(
                    "Invalid integer value for type float, expected 0 or greater value found -1"
                )
            }
        }

        @Test
        fun `should throw when fractions value is invalid`() {
            val exception = assertThrows<IllegalArgumentException> {
                moshi.adapter(InvalidFloatFractionValue::class.java)
            }
            assertTrue {
                exception.message!!.contains(
                    "Invalid fraction value for type float, expected positive value found -1"
                )
            }
        }

    }

    @Nested
    inner class DoubleTests {
        @Test
        fun `should be able to deserialize double values with valid integers and fraction values`() {
            val adapter = moshi.adapter(ValidDoubleValue::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"value\":9.1}"))
            assertEquals(ValidDoubleValue(value = 9.1), fromJson)
        }

        @Test
        fun `should be able to serialize double values with valid integers and fraction values`() {
            val adapter = moshi.adapter(ValidDoubleValue::class.java)
            val toJson = requireNotNull(adapter.toJson(ValidDoubleValue(value = 9.1)))
            assertEquals("{\"value\":9.1}", toJson)
        }

        @Test
        fun `should throw when integers double is not equal to the target one`() {
            val adapter = moshi.adapter(ValidDoubleValue::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(ValidDoubleValue(value = 10.0))
            }
            assertEquals(
                "Invalid value at \$.value, Expected integers count: 1 found 2",
                exception.message
            )
        }

        @Test
        fun `should throw when integers value is invalid`() {
            val exception = assertThrows<IllegalArgumentException> {
                moshi.adapter(InvalidDoubleIntegerValue::class.java)
            }
            assertTrue {
                exception.message!!.contains(
                    "Invalid integer value for type double, expected 0 or greater value found -1"
                )
            }
        }

        @Test
        fun `should throw when fractions value is invalid`() {
            val exception = assertThrows<IllegalArgumentException> {
                moshi.adapter(InvalidDoubleFractionValue::class.java)
            }
            assertTrue {
                exception.message!!.contains(
                    "Invalid fraction value for type double, expected positive value found -1"
                )
            }
        }

    }

    @Test
    fun `factory should maintain other annotations`() {
        val adapter = moshi.adapter(DoubleData::class.java)
        val fromJson = requireNotNull(adapter.fromJson("{\"value\": 1.0}"))
        assertEquals(2.0f, fromJson.value)
        assertEquals("{\"value\":\"1.0\"}", adapter.toJson(fromJson))
    }

    @Test
    fun `Annotating unsupported types should throw exception`() {
        val exception = assertThrows<IllegalArgumentException> {
            moshi.adapter(InvalidType::class.java)
        }
        assertTrue(exception.message!!.contains("Unsupported type annotated with @Digits"))
    }

    companion object {
        // Float models
        @JsonClass(generateAdapter = true)
        data class ValidFloatValue(@Digits(integer = 1, fraction = 1) val value: Float)

        @JsonClass(generateAdapter = true)
        data class InvalidFloatIntegerValue(@Digits(integer = -1, fraction = 1) val value: Float)

        @JsonClass(generateAdapter = true)
        data class InvalidFloatFractionValue(@Digits(integer = 1, fraction = -1) val value: Float)

        // Double models
        @JsonClass(generateAdapter = true)
        data class ValidDoubleValue(@Digits(integer = 1, fraction = 1) val value: Double)

        @JsonClass(generateAdapter = true)
        data class InvalidDoubleIntegerValue(@Digits(integer = -1, fraction = 1) val value: Double)

        @JsonClass(generateAdapter = true)
        data class InvalidDoubleFractionValue(@Digits(integer = 1, fraction = -1) val value: Double)

        @JsonClass(generateAdapter = true)
        data class DoubleData(@Digits(integer = 1, fraction = 1) @DoubleFloatValue val value: Float)

        // Invalid type model
        @JsonClass(generateAdapter = true)
        data class InvalidType(@Digits(integer = 1, fraction = 1) val value: String)

    }
}
