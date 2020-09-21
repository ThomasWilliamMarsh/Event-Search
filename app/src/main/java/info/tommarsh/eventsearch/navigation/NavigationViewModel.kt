package info.tommarsh.eventsearch.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


enum class Screen {
    SEARCH,
    EVENT
}

class NavigationViewModel : ViewModel() {

    private val _currentScreen = MutableLiveData<Screen>()
    val currentScreen: LiveData<Screen> = _currentScreen

    fun navigate(screen: Screen) {
        _currentScreen.value = screen
    }
}