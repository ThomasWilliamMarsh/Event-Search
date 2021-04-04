package info.tommarsh.eventsearch.core.data

sealed class FetchState<T> {
    data class Loading<T>(val initialLoad: Boolean) : FetchState<T>()
    data class Success<T>(val data: T) : FetchState<T>()
    data class Failure<T>(val throwable: Throwable) : FetchState<T>()
}