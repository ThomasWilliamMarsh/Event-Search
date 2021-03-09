package info.tommarsh.eventsearch

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import dagger.hilt.android.HiltAndroidApp
import info.tommarsh.eventsearch.core.data.preferences.PreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class EventSearchApp : Application() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun onCreate() {
        super.onCreate()
        setStoredNightMode()
    }

    private fun setStoredNightMode() = runBlocking {
        val mode = preferencesRepository.getDarkModePreference().first()
        setDefaultNightMode(mode)
    }
}