
# Moshi Validation Adapters

A collection of simple JsonAdapters for [Moshi](https://github.com/square/moshi).
 
This library acts as an extension to Moshi by providing general purpose, yet useful `JsonAdapter`s that are not present in the main library. Most provided adapters are linked via specialized `JsonQualifier` annotations that alter serialization/deserialization strategies.

### How To Use It

This library is **not** forcing any of it's own adapters by default. To leverage from any of the provided annotations/adapters add their respective factory to your `Moshi.Builder`:

```kotlin
val moshi = Moshi.Builder()  
  .add(DecimalMax.ADAPTER_FACTORY)  
  .build()
```
---
### Overall contract

Every declared annotation/adapter in this library, supports adapter composition. Which means that no `JsonAdapter.Factory` will short circuit adapter construction on it's own annotation, and instead will delegate to the next adapter returned by `Moshi`. Meaning that given the following type declaration:
 
```kotlin
data class Double(  
    @DecimalMax(value = "100", inclusive = true)  
    @DoubleIntValue val x: Int  
)
```
The resulting `JsonAdapter` will validate `x` from the json response and double the value of it.
 
One **important** concept to keep in mind, that the order of declared annotations in the example 
 above **does not have any influence** on the way how the final adapter will be constructed. 
 Instead **the order** of the `JsonAdapter.Factory`'s added to the `Moshi` instance is what plays 
 a major role in overall behavior. Meaning that in order for the example above to satisfy the 
 expected result, one must add the factories in the following order:
 
```kotlin
val moshi = Moshi.Builder()  
  .add(DecimalMax.ADAPTER_FACTORY)  
  .add(DoubleIntValue)  
  .build()
```
---
## Available annotations

-  `@DecimalMax(value=, inclusive=)`
	- Checks whether the annotated value is less than the specified maximum, when  `inclusive`=false. Otherwise whether the value is less than or equal to the specified maximum.
	- The parameter `value` is the string representation of the max value according to the  `BigDecimal`  string representation.
	- **Supported data types**:  `Int`, `Byte`, `Short` and `Long`
	- **Example**:
		```kotlin
		@DecimalMax(value = "100", inclusive = false) val value: Int
		```
---
-  `@DecimalMin(value=, inclusive=)`
Checks whether the annotated value is larger than the specified minimum, when  `inclusive`=false. Otherwise whether the value is larger than or equal to the specified minimum.
  - The parameter `value` is the string representation of the min value according to the  `BigDecimal`  string representation.
	- **Supported data types**:  `Int`, `Byte`, `Short` and `Long`
	- **Example**:
		```kotlin
		@DecimalMin(value = "100", inclusive = false) val value: Int
		```
---
- `@Digits(integer=, fraction=)`
	- Checks whether the annotated value is a number having up to  `integer`  digits and  `fraction`  fractional digits
	- **Supported data types**: `Float` and `Double`.
	- **Example**:
		```kotlin
		@Digits(integer = 1, fraction = 1) val value: Float
		```
---
- `@NotEmpty`
	- Checks that the annotated string is not empty. 
	- **Supported data types**: `String`.
	- **Example**:
		```kotlin
		@NotEmpty val string: String
		```
---
- `@NotBlank`
	- Checks that the annotated string is not blank. 
	- The difference to  `@NotEmpty`  is and that trailing white-spaces are ignored.
	- **Supported data types**: `String`.
	- **Example**:
		```kotlin
		@NotBlank val string: String
		```
---
- `@AssertFalse`
	- Checks that the annotated element is `false`.
	- **Supported data types**: `Boolean`.
	- **Example**:
		```kotlin
		@AssertFalse val b: Boolean
		```
---
