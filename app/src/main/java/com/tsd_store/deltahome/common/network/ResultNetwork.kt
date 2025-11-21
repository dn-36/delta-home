package com.tsd_store.deltahome.common.network


sealed interface ResultNetwork<out D, out E: ErrorApi> {
    data class Success<out D>(val data: D): ResultNetwork<D, Nothing>
    data class Error<out E: ErrorApi>(val error: E):
        ResultNetwork<Nothing, E>
}

inline fun <T, E: ErrorApi, R> ResultNetwork<T, E>.map(map: (T) -> R): ResultNetwork<R, E> {
    return when(this) {
        is com.tsd_store.deltahome.common.network.ResultNetwork.Error -> ResultNetwork.Error(error)
        is ResultNetwork.Success -> ResultNetwork.Success(map(data))
    }
}

fun <T, E: ErrorApi> ResultNetwork<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: ErrorApi> ResultNetwork<T, E>.onSuccess(action: (T) -> Unit): ResultNetwork<T, E> {
    return when(this) {
        is com.tsd_store.deltahome.common.network.ResultNetwork.Error -> this
        is ResultNetwork.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: ErrorApi> ResultNetwork<T, E>.onError(action: (E) -> Unit): ResultNetwork<T, E> {
    return when(this) {
        is com.tsd_store.deltahome.common.network.ResultNetwork.Error -> {
            action(error)
            this
        }
        is ResultNetwork.Success -> this
    }
}

typealias EmptyResult<E> = ResultNetwork<Unit, E>