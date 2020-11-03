package info.tommarsh.eventsearch.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.stringArg
import info.tommarsh.eventsearch.theme.EventSearchAppTheme
import info.tommarsh.eventsearch.ui.category.screen.CategoryScreen
import info.tommarsh.eventsearch.ui.event.EventDetailScreen
import info.tommarsh.eventsearch.ui.event.EventDetailViewModel
import info.tommarsh.eventsearch.ui.search.SearchScreen
import info.tommarsh.eventsearch.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainComposable()
        }
    }

    @Composable
    fun MainComposable() = EventSearchApp {
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
                val viewModel: EventDetailViewModel by viewModels()
                viewModel.getEventDetails(backStackEntry.stringArg("id"))
                EventDetailScreen(
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