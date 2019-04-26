package by.trusevich.house.core.model.enumeration

import com.fasterxml.jackson.annotation.JsonCreator

@Suppress("unused")
enum class Role {

    ADMIN,

    USER,

    UNDEFINED;

    companion object {

        private val NAMES_MAP = values().associate { it.name.toUpperCase() to it }

        @JvmStatic
        @JsonCreator
        @Suppress("unused")
        fun byName(value: String?) = NAMES_MAP[value?.trim()?.toUpperCase()] ?: UNDEFINED

    }

}