package info.tommarsh.eventsearch

import androidx.navigation.NavBackStackEntry
import info.tommarsh.eventsearch.model.FetchState

//region NavBackStackEntry
fun NavBackStackEntry.stringArg(id: String, default: String = ""): String {
    return arguments?.getString(id, default) ?: default
}
//EndRegion

suspend inline fun <reified T> fetch(crossinline block: suspend () -> T): FetchState<T> {
    return try {
        FetchState.Success(block())
    } catch (throwable: Throwable) {
        FetchState.Failure(throwable)
    }
}