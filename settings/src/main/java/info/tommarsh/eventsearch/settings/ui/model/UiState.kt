package info.tommarsh.eventsearch.settings.ui.model

import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

internal data class SettingsScreenState(
    val darkMode: Int = MODE_NIGHT_FOLLOW_SYSTEM
)

internal sealed class SettingsScreenAction {
    data class NightModeChanged(val nightMode: Int) : SettingsScreenAction()
    object NavigateBack : SettingsScreenAction()
}