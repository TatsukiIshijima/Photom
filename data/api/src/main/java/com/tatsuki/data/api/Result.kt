package com.tatsuki.data.api

// https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
// https://medium.com/swlh/kotlin-sealed-class-for-success-and-error-handling-d3054bef0d4e
// https://github.com/seanghay/result-of/blob/master/resultof/src/main/java/com/seanghay/resultof/result-of-ktx.kt
sealed class Result<out T> {
    data class Success<out R>(
        val data: R
    ) : Result<R>()

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

inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> =
    when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.ClientError -> this
        is Result.Error -> this
        Result.NetworkError -> Result.NetworkError
        Result.ServerError -> Result.ServerError
    }

inline fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> =
    when (this) {
        is Result.Success -> transform(data)
        is Result.ClientError -> this
        is Result.Error -> this
        Result.NetworkError -> Result.NetworkError
        Result.ServerError -> Result.ServerError
    }

inline fun <T> Result<T>.withDefault(data: () -> T): Result.Success<T> =
    when (this) {
        is Result.Success -> this
        else -> Result.Success(data())
    }

