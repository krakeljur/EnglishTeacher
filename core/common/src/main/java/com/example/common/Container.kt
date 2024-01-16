package com.example.common


/**
 * Class - wrapper for correct work with async result
 */
sealed class Container<out T> {
    data class Success<out T>(val data: T) : Container<T>()
    data class Error<out T>(val data: T) : Container<T>()
    data object Pending : Container<Nothing>()
}