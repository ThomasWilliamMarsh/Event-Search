package info.tommarsh.eventsearch.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import info.tommarsh.eventsearch.stringArg
import info.tommarsh.eventsearch.ui.attractions.AttractionDetailScreen
import info.tommarsh.eventsearch.ui.attractions.AttractionDetailViewModel
import info.tommarsh.eventsearch.ui.category.CategoryScreen
import info.tommarsh.eventsearch.ui.category.CategoryViewModel
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
            composable("Search") {
                val viewModel: SearchViewModel by viewModels()
                val (currentQuery, setCurrentQuery) = remember { mutableStateOf("") }

                SearchScreen(
                    attractionsFlow = viewModel.getAttractions(currentQuery),
                    categoriesFlow = viewModel.categoriesState,
                    likedItemsFlow = viewModel.likedAttractions,
                    navigateToEvent = { id -> controller.navigate("Event/$id") },
                    navigateToCategory = { id, name -> controller.navigate("Category/$id/$name") },
                    setCurrentQuery = setCurrentQuery
                )
            }
            composable("Event/{id}") { backStackEntry ->
                val viewModel: AttractionDetailViewModel by viewModels()
                val eventId = backStackEntry.stringArg("id")

                AttractionDetailScreen(
                    fetchFlow = viewModel.fetchStateFlowFor(eventId),
                    likedFlow = viewModel.likedStateFlowFor(eventId),
                    onLiked = viewModel::addLikedAttraction,
                    onUnliked = viewModel::removeLikedAttraction
                )
            }
            composable("Category/{id}/{name}") { backStackEntry ->
                val viewModel: CategoryViewModel by viewModels()
                val categoryId = backStackEntry.stringArg("id")
                val categoryName = backStackEntry.stringArg("name")

                CategoryScreen(
                    categoryName = categoryName,
                    attractionsFlow = viewModel.getAttractions(categoryId),
                    navigateToEvent = { id -> controller.navigate("Event/$id") }
                )
            }
        }
    }
}