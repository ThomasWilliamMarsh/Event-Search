package info.tommarsh.eventsearch.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import info.tommarsh.eventsearch.ui.attractions.AttractionDetailScreen
import info.tommarsh.eventsearch.ui.category.CategoryScreen
import info.tommarsh.eventsearch.ui.search.SearchScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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
            startDestination = "Search"
        ) {
            composable("Search") { backStackEntry ->
                SearchScreen(
                    backStackEntry = backStackEntry,
                    controller = controller
                )
            }
            composable("Event/{id}") { backStackEntry ->
                AttractionDetailScreen(
                    backStackEntry = backStackEntry
                )
            }
            composable("Category/{id}/{name}") { backStackEntry ->
                CategoryScreen(
                    backStackEntry = backStackEntry,
                    controller = controller
                )
            }
        }
    }
}