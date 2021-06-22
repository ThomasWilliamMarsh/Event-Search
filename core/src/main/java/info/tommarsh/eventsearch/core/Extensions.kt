package info.tommarsh.eventsearch.core

import androidx.navigation.NavBackStackEntry
import info.tommarsh.eventsearch.core.data.FetchState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//region NavBackStackEntry
fun NavBackStackEntry.stringArg(id: String, default: String = ""): String {
    return arguments?.getString(id, default) ?: default
}
//EndRegion

suspend inline fun <reified T> fetch(crossinline block: suspend () -> T): Flow<FetchState<T>> =
    flow {
        emit(FetchState.Loading(true))
        try {
            emit(FetchState.Success(block()))
        } catch (throwable: Throwable) {
            emit(FetchState.Failure<T>(throwable))
        }
    }