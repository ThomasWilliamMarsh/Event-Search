package info.tommarsh.eventsearch.core.data.preferences

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "settings")

internal class DataStorePreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesRepository {

    override fun getDarkModePreference(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[DARK_MODE_KEY] ?: MODE_NIGHT_FOLLOW_SYSTEM
        }
    }

    override suspend fun setDarkModePreference(preference: Int) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = preference
        }
    }

    companion object {
        private val DARK_MODE_KEY = intPreferencesKey("dark_mode")
    }
}
