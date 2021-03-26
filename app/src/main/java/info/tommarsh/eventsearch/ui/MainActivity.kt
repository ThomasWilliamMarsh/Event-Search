package info.tommarsh.eventsearch.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.eventsearch.core.data.preferences.PreferencesRepository
import info.tommarsh.eventsearch.navigation.Arguments
import info.tommarsh.eventsearch.navigation.Destinations
import info.tommarsh.eventsearch.ui.attractions.AttractionDetailScreen
import info.tommarsh.eventsearch.ui.category.CategoryScreen
import info.tommarsh.eventsearch.ui.search.SearchScreen
import info.tommarsh.eventsearch.ui.settings.SettingsScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                MainComposable()
            }
        }
    }

    @Composable
    fun MainComposable() {
        val controller = rememberNavController()
        NavHost(
            navController = controller,
            startDestination = Destinations.SEARCH
        ) {
            composable(Destinations.SEARCH) { _ ->
                SearchScreen(controller = controller)
            }
            composable("${Destinations.EVENT}/{${Arguments.ID}}") { backStackEntry ->
                AttractionDetailScreen(
                    backStackEntry = backStackEntry
                )
            }
            composable("${Destinations.CATEGORY}/{${Arguments.ID}}/{${Arguments.NAME}}") { backStackEntry ->
                CategoryScreen(
                    backStackEntry = backStackEntry,
                    controller = controller
                )
            }
            composable(Destinations.SETTINGS) { _ ->
                SettingsScreen(
                    controller = controller,
                )
            }
        }
    }
}