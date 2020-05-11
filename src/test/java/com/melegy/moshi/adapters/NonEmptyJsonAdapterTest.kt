package com.melegy.moshi.adapters

import com.melegy.moshi.adapters.nonempty.NonEmpty
import com.melegy.moshi.adapters.utils.PlusMoshi
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class NonEmptyJsonAdapterTest {

    // Lazy adapters work only within the context of moshi.
    private val moshi = Moshi.Builder()
        .add(NonEmpty.ADAPTER_FACTORY)
        .add(PlusMoshi)
        .build()

    @Nested
    inner class StringTests {

        @Test
        fun `NonEmpty should be able to deserialize non empty strings`() {
            val adapter = moshi.adapter(NonEmptyString::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"string\": \"test\"}"))
            assertEquals("test", fromJson.string)
        }

        @Test
        fun `NonEmpty should be able to serialize non empty strings`() {
            val adapter = moshi.adapter(NonEmptyString::class.java)
            val toJson = requireNotNull(adapter.toJson(NonEmptyString(string = "test")))
            assertEquals("{\"string\":\"test\"}", toJson)
        }

        @Test
        fun `NonEmpty should be able to deserialize blank string`() {
            val adapter = moshi.adapter(NonEmptyString::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"string\": \" \"}"))
            assertEquals(" ", fromJson.string)
        }

        @Test
        fun `NonEmpty should be able to serialize blank string`() {
            val adapter = moshi.adapter(NonEmptyString::class.java)
            val toJson = requireNotNull(adapter.toJson(NonEmptyString(string = " ")))
            assertEquals("{\"string\":\" \"}", toJson)
        }

        @Test
        fun `NonEmpty should throw exception when deserialize empty string`() {
            val adapter = moshi.adapter(NonEmptyString::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"string\": \"\"}")
            }
            assertEquals("unexpected empty field found at path \$.string", exception.message)
        }

        @Test
        fun `NonEmpty should throw exception when serialize empty string`() {
            val adapter = moshi.adapter(NonEmptyString::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NonEmptyString(string = ""))
            }
            assertEquals("unexpected empty field found at path \$.string", exception.message)
        }
    }

    @Nested
    inner class ListTests {

        @Test
        fun `NonEmpty should be able to deserialize non empty list`() {
            val adapter = moshi.adapter(NonEmptyList::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"list\": [1]}"))
            assertEquals(listOf(1), fromJson.list)
        }

        @Test
        fun `NonEmpty should be able to serialize non empty list`() {
            val adapter = moshi.adapter(NonEmptyList::class.java)
            val toJson = requireNotNull(adapter.toJson(NonEmptyList(list = listOf(1))))
            assertEquals("{\"list\":[1]}", toJson)
        }

        @Test
        fun `NonEmpty should throw exception when deserialize empty list`() {
            val adapter = moshi.adapter(NonEmptyList::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"list\": []}")
            }
            assertEquals("unexpected empty field found at path \$.list", exception.message)
        }

        @Test
        fun `NonEmpty should throw exception when serialize empty list`() {
            val adapter = moshi.adapter(NonEmptyList::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NonEmptyList(list = emptyList()))
            }
            assertEquals("unexpected empty field found at path \$.list", exception.message)
        }
    }

    @Nested
    inner class CollectionTests {

        @Test
        fun `NonEmpty should be able to deserialize non empty collection`() {
            val adapter = moshi.adapter(NonEmptyCollection::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"collection\": [1]}"))
            assertEquals(listOf(1), fromJson.collection)
        }

        @Test
        fun `NonEmpty should be able to serialize non empty collection`() {
            val adapter = moshi.adapter(NonEmptyCollection::class.java)
            val toJson =
                requireNotNull(adapter.toJson(NonEmptyCollection(collection = listOf(1))))
            assertEquals("{\"collection\":[1]}", toJson)
        }

        @Test
        fun `NonEmpty should throw exception when deserialize empty collection`() {
            val adapter = moshi.adapter(NonEmptyCollection::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"collection\": []}")
            }
            assertEquals("unexpected empty field found at path \$.collection", exception.message)
        }

        @Test
        fun `NonEmpty should throw exception when serialize empty collection`() {
            val adapter = moshi.adapter(NonEmptyCollection::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NonEmptyCollection(collection = emptyList()))
            }
            assertEquals("unexpected empty field found at path \$.collection", exception.message)
        }
    }

    @Nested
    inner class MapTests {

        @Test
        fun `NonEmpty should be able to deserialize non empty map`() {
            val adapter = moshi.adapter(NonEmptyMap::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"map\": {\"one\": 1}}"))
            assertEquals(mapOf("one" to 1), fromJson.map)
        }

        @Test
        fun `NonEmpty should be able to serialize non empty map`() {
            val adapter = moshi.adapter(NonEmptyMap::class.java)
            val toJson = requireNotNull(adapter.toJson(NonEmptyMap(map = mapOf("one" to 1))))
            assertEquals("{\"map\":{\"one\":1}}", toJson)
        }

        @Test
        fun `NonEmpty should throw exception when deserialize empty map`() {
            val adapter = moshi.adapter(NonEmptyMap::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"map\": {}}")
            }
            assertEquals("unexpected empty field found at path \$.map", exception.message)
        }

        @Test
        fun `NonEmpty should throw exception when serialize empty map`() {
            val adapter = moshi.adapter(NonEmptyMap::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NonEmptyMap(map = emptyMap()))
            }
            assertEquals("unexpected empty field found at path \$.map", exception.message)
        }
    }

    @Nested
    inner class ArrayTests {

        @Test
        fun `NonEmpty should be able to deserialize non empty array`() {
            val adapter = moshi.adapter(NonEmptyArray::class.java)
            val fromJson = requireNotNull(adapter.fromJson("{\"array\": [1]}"))
            assertArrayEquals(arrayOf(1), fromJson.array)
        }

        @Test
        fun `NonEmpty should be able to serialize non empty array`() {
            val adapter = moshi.adapter(NonEmptyArray::class.java)
            val toJson = requireNotNull(adapter.toJson(NonEmptyArray(array = arrayOf(1))))
            assertEquals("{\"array\":[1]}", toJson)
        }

        @Test
        fun `NonEmpty should throw exception when deserialize empty array`() {
            val adapter = moshi.adapter(NonEmptyArray::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.fromJson("{\"array\": []}")
            }
            assertEquals("unexpected empty field found at path \$.array", exception.message)
        }

        @Test
        fun `NonEmpty should throw exception when serialize empty array`() {
            val adapter = moshi.adapter(NonEmptyArray::class.java)
            val exception = assertThrows<IllegalArgumentException> {
                adapter.toJson(NonEmptyArray(array = emptyArray()))
            }
            assertEquals("unexpected empty field found at path \$.array", exception.message)
        }
    }

    @Test
    fun `Annotating unsupported types should throw exception`() {
        val exception = assertThrows<IllegalArgumentException> {
            moshi.adapter(InvalidType::class.java)
        }
        assertTrue(exception.message!!.contains("Unsupported type annotated with @NonEmpty"))
    }

    @Test
    fun `toString() should reflects InnerAdapter`() {
        val adapter = moshi.adapter<NonEmpty>(String::class.java, NonEmpty::class.java)
        assertEquals("JsonAdapter(String).nullSafe().NonEmpty()", adapter.toString())
    }

    @Test
    fun `factory should maintains other annotations`() {
        val adapter = moshi.adapter(NonEmptyPlusMoshi::class.java)
        val fromJson = requireNotNull(adapter.fromJson("{\"string\": \"test\"}"))
        assertEquals("testMoshi", fromJson.string)
        assertEquals("{\"string\":\"test\"}", adapter.toJson(fromJson))
    }

    companion object {

        @JsonClass(generateAdapter = true)
        data class NonEmptyString(@NonEmpty val string: String)

        @JsonClass(generateAdapter = true)
        data class NonEmptyList(@NonEmpty val list: List<Int>)

        @JsonClass(generateAdapter = true)
        data class NonEmptyCollection(@NonEmpty val collection: Collection<Int>)

        @JsonClass(generateAdapter = true)
        data class NonEmptyMap(@NonEmpty val map: Map<String, Int>)

        @JsonClass(generateAdapter = true)
        data class NonEmptyArray(@NonEmpty val array: Array<Int>)

        @JsonClass(generateAdapter = true)
        data class NonEmptyPlusMoshi(@NonEmpty @PlusMoshi val string: String)

        @JsonClass(generateAdapter = true)
        data class InvalidType(@NonEmpty val any: Any)
    }
}
