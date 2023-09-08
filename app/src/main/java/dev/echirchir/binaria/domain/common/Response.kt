package dev.echirchir.binaria.domain.common

sealed class Response<T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Failure<T>(val throwable: Throwable) : Response<T>()

    companion object {
        fun <T> success(data: T) = Success<T>(data)
        fun <T> failure(throwable: Throwable) = Failure<T>(throwable)
    }

    fun isSuccess(): Boolean {
        return this is Success
    }

    fun fold(
        onSuccess: (value: T) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(throwable)
        }
    }

    inline fun <R> map(transform: (value: T) -> R): Response<R> {
        return when (this) {
            is Failure -> failure(this.throwable)
            is Success -> success(transform(this.data))
        }
    }
}

data class ErrorResponse(val error: String)
