package com.tatsuki.data.api

// https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
sealed class Result<out T> {
    data class Success<out T>(
        val data: T
    ) : Result<T>()

    data class Error(
        val code: Int? = null,
        val message: String? = null
    ) : Result<Nothing>()

    data class ClientError(
        val code: Int,
        val message: String? = null
    ) : Result<Nothing>()

    object ServerError : Result<Nothing>()
    object NetworkError : Result<Nothing>()
}
