package info.tommarsh.eventsearch

import androidx.compose.runtime.ambientOf
import info.tommarsh.eventsearch.navigation.NavigationViewModel

val Navigator = ambientOf<NavigationViewModel> { error("No Navigator Found!") }