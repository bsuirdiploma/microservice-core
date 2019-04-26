package by.trusevich.house.core.util

import by.trusevich.house.core.model.BaseEntity
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

inline fun <reified S : Any, reified T : Any> Collection<S>?.mapWithTransform(transform: (source: S, target: T) -> Unit) =
    orEmpty().map { it.convert(transform) }

inline fun <S : Any, reified T : Any> S.convert(transform: (source: S, target: T) -> Unit) =
    T::class.java.getDeclaredConstructor().newInstance()!!.apply { transform(this@convert, this) }

fun <T : BaseEntity> T.merge(other: T, clazz: KClass<T>): T {
    val nameToProperty = clazz.declaredMemberProperties.associateBy { it.name }
    val primaryConstructor = clazz.primaryConstructor!!
    val args = primaryConstructor.parameters.associate { parameter ->
        val property = nameToProperty[parameter.name]!!
        parameter to (property.get(this) ?: property.get(other))
    }
    return primaryConstructor.callBy(args)
}
