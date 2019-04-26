//@file:Suppress("unused", "ObjectPropertyName")

package by.trusevich.house.core.util

import by.trusevich.house.core.exception.MalformedRequestDataException
import com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_MISSING_VALUES
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.databind.type.CollectionType
import com.fasterxml.jackson.databind.type.TypeFactory.defaultInstance
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.StringUtils.EMPTY
import org.apache.commons.lang3.StringUtils.appendIfMissing
import org.apache.commons.lang3.StringUtils.prependIfMissing
import org.apache.logging.log4j.LogManager.getLogger
import java.io.IOException

private val log by lazy { getLogger("JsonUtils")!! }

const val EMPTY_TEMPLATE = "{ }"

private val knownListTypes = mutableMapOf<Class<*>, CollectionType>()

/**
 * Configures provided [ObjectMapper]
 *
 * @return [ObjectMapper]
 */
fun ObjectMapper.configureMapper() = apply {
    configure(ALLOW_MISSING_VALUES, true)
    enable(INDENT_OUTPUT)
    disable(WRITE_DATES_AS_TIMESTAMPS)
    registerModule(JavaTimeModule())
}

/**
 * Default instance of [ObjectMapper] available in the application.
 */
private val objectMapper = ObjectMapper().configureMapper()

/**
 * `Singleton`
 * Default instance of [ObjectMapper] commonly available in the application.
 *
 * @return [ObjectMapper]
 * @since 1.0.0
 */
fun mapper() = objectMapper

/**
 *
 * Method converts a JSON provided into [T].
 * In case of any error method returns null.
 *
 * @param mapper Custom instance of [ObjectMapper] to be used during serialization/deserialization of
 * JSON/Java objects.
 * @param json   JSON representation of the object
 * @param clazz  [Class] to cast the result to
 * @param <T>    [type][Class] of the object
 * @return A new instance of [T] or **`null`** value in case of any error.
 * @throws MalformedRequestDataException Exception to be thrown in case of any exception occurred during parsing and
 * converting of the JSON representation object to its Java type.
 * @see ObjectMapper.readValue
 * @since 1.0.0
</T> */
fun <T> toObject(json: String, clazz: Class<T>, mapper: ObjectMapper = objectMapper): T {
    val normalizedJson = json.trim()

    if (normalizedJson.isEmpty()) {
        throw MalformedRequestDataException()
    }

    try {
        return mapper.readValue(normalizedJson, clazz)
    } catch (exception: IOException) {
        log.error("Unexpected error occurred during deserialization from JSON to Object", exception)

        throw MalformedRequestDataException()
    }
}

fun Any.toJson(default: String = EMPTY_TEMPLATE, mapper: ObjectMapper = mapper()) =
    toJsonString(this, default, mapper)

fun <T> String.fromJson(clazz: Class<T>, mapper: ObjectMapper = objectMapper) =
    toObject(this, clazz, mapper)

inline fun <reified T> String.fromJson(mapper: ObjectMapper = mapper()) =
    fromJson(clazz = T::class.java, mapper = mapper)

fun <T> String.toListFromJson(clazz: Class<T>, mapper: ObjectMapper = objectMapper) =
    toList(this, clazz, mapper)

inline fun <reified T> String.toListFromJson(mapper: ObjectMapper = mapper()) =
    this.toListFromJson(clazz = T::class.java, mapper = mapper)

/**
 *
 * Method converts an object provided into JSON string.
 * In case of any error method returns null.
 *
 * @param mapper        Custom instance of [ObjectMapper] to be used during serialization/deserialization of
 * JSON/Java objects.
 * @param data        Object to be converted to JSON
 * @param defaultResult Value to be returned in case if conversion of the object produces any kind of exception
 * @return JSON representation of the object provided
 * @see ObjectMapper.writeValueAsString
 * @since 1.0.0
 */
fun toJsonString(data: Any?, defaultResult: String = EMPTY, mapper: ObjectMapper = objectMapper): String {
    data ?: return defaultResult

    return try {
        mapper.writeValueAsString(data)
    } catch (exception: JsonProcessingException) {
        log.error(
            "Unexpected error occurred during conversion of  to JSON representation",
            data.javaClass.canonicalName,
            exception
        )
        defaultResult
    }
}

/**
 * Gets cached Jackson mapping's [CollectionType] for [List] as a container and `source` as
 * supported type if exists, otherwise creates a new instance, caches it and returns as the result.
 *
 * @param clazz Type of items in List to create a conversion settings for
 * @return [CollectionType]
 * @since 1.0.0
 */
fun listType(clazz: Class<*>): CollectionType {

    return knownListTypes[clazz] ?: return registerListType(clazz)
}

private fun registerListType(clazz: Class<*>): CollectionType = synchronized(knownListTypes) {
    with(defaultInstance().constructCollectionType(List::class.java, clazz)) {
        knownListTypes[clazz] = this

        return this
    }
}

/**
 *
 * Method converts a JSON provided into a Collection of [T] items.
 * In case if JSON provided is malformed or in case of any other processing error, method throws
 * [MalformedRequestDataException].
 *
 * @param mapper  Custom instance of [ObjectMapper] to be used during serialization/deserialization of
 * JSON/Java objects.
 * @param rawJson JSON representation of the object.
 * @param clazz   [Class] of items in the collection
 * @param <T>     [type][Class] to cast each item in the resulted collection to
 * @return Collection of parsed items, may contain null values.
 * @throws MalformedRequestDataException Exception to be thrown in case if JSON data provided is malformed, incorrectly
 * formatted or [is blank][StringUtils.isBlank].
 * @see ObjectMapper.readValue
 * @see CollectionType
 *
 * @since 1.0.0
 */
fun <T> toList(rawJson: String, clazz: Class<T>, mapper: ObjectMapper = objectMapper): List<T> {
    val normalizedJson = rawJson.trim()

    if (normalizedJson.isEmpty()) {
        throw MalformedRequestDataException()
    }

    val arrayJson = prependIfMissing(appendIfMissing(normalizedJson, "]"), "[")

    try {
        return mapper.readValue<List<T>>(arrayJson, listType(clazz)).filter { it != null }
    } catch (exception: IOException) {
        log.error(
            "Unexpected error occurred during transforming of JSON to Collection of {} items",
            clazz.simpleName,
            exception
        )

        throw MalformedRequestDataException()
    }
}

inline fun <reified S, reified T> ObjectMapper.addMixIn(): ObjectMapper = addMixIn(S::class.java, T::class.java)