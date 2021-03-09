package info.tommarsh.eventsearch.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.eventsearch.core.data.preferences.PreferencesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(private val repository: PreferencesRepository) :
    ViewModel() {

    val darkMode = repository.darkModePreference()

    fun setDarkMode(preference: Int) = viewModelScope.launch {
        repository.setDarkModePreference(preference)
    }
}