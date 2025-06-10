package com.example.enums

import com.example.exception.EnumValueIsNotDefineException
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import org.babyfish.jimmer.sql.EnumType

@EnumType(value = EnumType.Strategy.NAME)
enum class ERole {
    ADMIN,
    GENERAL;

    @JsonValue
    fun serialize() =
        this.name

    @JsonCreator
    fun deserialize(value: String) =
        entries.firstOrNull { it.name == value }
            ?: throw EnumValueIsNotDefineException()

}