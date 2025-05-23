package com.example.exception

enum class ErrorCode(val code: Int, val message: String) {
    /**
     * 枚举值没有定义
     */
    ENUM_VALUE_IS_NOT_DEFINE(405, "value is not defined"),

    TRANSACTION_ERROR(50000, "transaction error"),
}