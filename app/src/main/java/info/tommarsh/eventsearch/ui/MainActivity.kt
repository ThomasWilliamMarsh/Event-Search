package info.tommarsh.eventsearch.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.eventsearch.ProvidesStatusBarHeight
import info.tommarsh.eventsearch.stringArg
import info.tommarsh.eventsearch.ui.attractions.AttractionDetailScreen
import info.tommarsh.eventsearch.ui.attractions.AttractionDetailViewModel
import info.tommarsh.eventsearch.ui.category.screen.CategoryScreen
import info.tommarsh.eventsearch.ui.search.SearchScreen
import info.tommarsh.eventsearch.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvidesStatusBarHeight {
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
            composable("Search") {
                val viewModel: SearchViewModel by viewModels()
                SearchScreen(
                    viewModel = viewModel,
                    navigateToEvent = { id -> controller.navigate("Event/$id") },
                    navigateToCategory = { id, name -> controller.navigate("Category/$id/$name") }
                )
            }
            composable("Event/{id}") { backStackEntry ->
                val viewModel: AttractionDetailViewModel by viewModels()
                viewModel.getEventDetails(backStackEntry.stringArg("id"))
                AttractionDetailScreen(
                    viewModel = viewModel
                )
            }
            composable("Category/{name}/{id}") { backStackEntry ->
                CategoryScreen(
                    name = backStackEntry.stringArg("name"),
                    id = backStackEntry.stringArg("id")
                )
            }
        }
    }
}