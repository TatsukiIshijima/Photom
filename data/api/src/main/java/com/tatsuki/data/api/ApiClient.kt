package com.tatsuki.data.api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

// https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
object ApiClient {
    suspend fun <T> safeApiCall(
        api: suspend () -> T,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Result<T> = withContext(dispatcher) {
        try {
            Result.Success(api.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> Result.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val message = throwable.message()
                    if (code in 400..499) {
                        Result.ClientError(code, message)
                    }
                    if (code in 500..599) {
                        Result.ServerError
                    }
                    Result.Error(code, message)
                }
                else -> Result.Error(null, null)
            }
        }
    }
}