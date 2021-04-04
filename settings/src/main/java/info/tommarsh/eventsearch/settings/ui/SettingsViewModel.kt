package info.tommarsh.eventsearch.settings.ui

import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.tommarsh.eventsearch.core.data.preferences.PreferencesRepository
import info.tommarsh.eventsearch.settings.ui.model.SettingsScreenAction
import info.tommarsh.eventsearch.settings.ui.model.SettingsScreenAction.NightModeChanged
import info.tommarsh.eventsearch.settings.ui.model.SettingsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel
@Inject constructor(private val repository: PreferencesRepository) :
    ViewModel() {

    private val _screenState = MutableStateFlow(SettingsScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getDarkModePreference().collectLatest { darkMode ->
                setDefaultNightMode(darkMode)
                _screenState.value = screenState.value.copy(darkMode = darkMode)
            }
        }
    }

    fun postAction(action: SettingsScreenAction) {
        when (action) {
            is NightModeChanged -> setDarkMode(action.nightMode)
            else -> throw NotImplementedError("Action not handled!: $action")
        }
    }

    private fun setDarkMode(preference: Int) = viewModelScope.launch {
        repository.setDarkModePreference(preference)
    }
}