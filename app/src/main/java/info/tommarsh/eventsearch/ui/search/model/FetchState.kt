package info.tommarsh.eventsearch.ui.search.model

sealed class FetchState<T> {
    data class Loading<T>(val initialLoad: Boolean) : FetchState<T>()
    data class Success<T>(val items: List<T>) : FetchState<T>()
    data class Failure<T>(val throwable: Throwable) : FetchState<T>()
}