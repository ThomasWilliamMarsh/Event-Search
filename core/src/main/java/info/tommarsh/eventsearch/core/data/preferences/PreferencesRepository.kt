package info.tommarsh.eventsearch.core.data.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun darkModePreference() : Flow<Int>

    suspend fun setDarkModePreference(preference: Int)
}