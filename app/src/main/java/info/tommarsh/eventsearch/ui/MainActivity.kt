package info.tommarsh.eventsearch.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.eventsearch.navigation.Screen
import info.tommarsh.eventsearch.ui.attractions.AttractionDetailScreen
import info.tommarsh.eventsearch.ui.category.CategoryScreen
import info.tommarsh.eventsearch.ui.common.ReminderDialog
import info.tommarsh.eventsearch.ui.search.SearchScreen
import info.tommarsh.eventsearch.ui.settings.SettingsScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
internal class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var reminderDialog: ReminderDialog

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
            startDestination = Screen.Search.route
        ) {
            composable(Screen.Search.route) {
                SearchScreen(controller = controller, reminderDialog = reminderDialog)
            }
            composable(
                route = Screen.Attraction.route,
                deepLinks = listOf(navDeepLink {
                    uriPattern = Screen.Attraction.deeplink
                })
            ) { backStackEntry ->
                AttractionDetailScreen(
                    backStackEntry = backStackEntry
                )
            }
            composable(Screen.Category.route) { backStackEntry ->
                CategoryScreen(
                    backStackEntry = backStackEntry,
                    controller = controller
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    controller = controller,
                )
            }
        }
    }
}