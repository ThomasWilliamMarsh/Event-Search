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
import info.tommarsh.eventsearch.attraction.ui.AttractionDetailScreen
import info.tommarsh.eventsearch.category.ui.CategoryScreen
import info.tommarsh.eventsearch.core.navigation.Screen
import info.tommarsh.eventsearch.settings.ui.SettingsScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
internal class MainActivity : AppCompatActivity() {

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
                SearchScreen(controller = controller)
            }
            composable(
                route = Screen.Attraction.route,
                deepLinks = listOf(navDeepLink {
                    uriPattern = Screen.Attraction.deeplink
                })
            ) {
                AttractionDetailScreen(
                    controller = controller
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