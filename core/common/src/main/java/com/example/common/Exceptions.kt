package com.example.common

open class AppException(
    message: String = "",
    cause: Throwable? = null
) : Exception(message, cause)