package com.example.enums

import com.example.exception.EnumValueIsNotDefineException
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class EPermission{
    READ_POSITION_PERMISSION,
    WRITE_POSITION_PERMISSION,
    READ_DEPARTMENT_PERMISSION,
    WRITE_DEPARTMENT_PERMISSION,
    READ_SCHEDULER_PERMISSION,
    WRITE_SCHEDULER_PERMISSION,
    WRITE_USER_ROLE_PERMISSION,
    DELETE_USER_ROLE_PERMISSION,
    READ_USER_ROLE_PERMISSION,
    READ_LLM_CONFIG_PERMISSION,
    WRITE_LLM_CONFIG_PERMISSION;

    @JsonValue
    fun serialize() =
        this.name

    @JsonCreator
    fun deserialize(value: String) =
        entries.firstOrNull { it.name.lowercase() == value }
            ?: throw EnumValueIsNotDefineException()

}
