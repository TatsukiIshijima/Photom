package com.tatsuki.core

// https://medium.com/firebase-developers/firebase-ing-with-kotlin-coroutines-flow-dab1bc364816
sealed class State<T> {
    data class Success<T>(val data: T): State<T>()
    data class Failed<T>(val exception: Exception): State<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> failed(exception: java.lang.Exception) = Failed<T>(exception)
    }
}