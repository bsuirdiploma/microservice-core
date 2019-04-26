package by.trusevich.house.core.util

import org.apache.logging.log4j.LogManager.getLogger

inline fun <reified T : Any> T.logger() = getLogger(T::class.java)!!

inline fun <reified T : Any> T.lazyLogger() = lazy { logger() }

fun logger(name: String) = getLogger(name)!!

fun lazyLogger(name: String) = lazy { logger(name) }
