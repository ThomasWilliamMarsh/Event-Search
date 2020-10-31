package info.tommarsh.eventsearch.ui.search.navigation

import androidx.compose.runtime.ambientOf
import androidx.navigation.NavController
import info.tommarsh.eventsearch.ui.search.SearchFragmentDirections

val Navigator = ambientOf<SearchNavigator>{ error("Navigator not provided!") }

class SearchNavigator(private val controller: NavController) {

    fun navigateToCategory(name: String, id: String) {
        controller.navigate(SearchFragmentDirections.actionSearchFragmentToCategoryFragment(id, name))
    }

    fun navigateToEvent(id: String) {
        controller.navigate(SearchFragmentDirections.actionSearchFragmentToEventFragment(id))
    }
}