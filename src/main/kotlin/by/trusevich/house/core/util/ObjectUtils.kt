package by.trusevich.house.core.util

inline fun <reified S : Any, reified T : Any> Collection<S>?.mapWithTransform(transform: (source: S, target: T) -> Unit) =
    orEmpty().map { it.convert(transform) }

inline fun <S : Any, reified T : Any> S.convert(transform: (source: S, target: T) -> Unit) =
    T::class.java.getDeclaredConstructor().newInstance()!!.apply { transform(this@convert, this) }
