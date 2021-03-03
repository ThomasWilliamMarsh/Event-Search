package info.tommarsh.eventsearch.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
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
        setDecorFitsSystemWindows(window, false)
        setContent {
            MainComposable()
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
                    attractionsFlow = viewModel.attractionsFlow(currentQuery),
                    categoriesFlow = viewModel.categoriesFlow,
                    likedItemsFlow = viewModel.likedAttractionsFlow,
                    navigateToEvent = { id -> controller.navigate("Event/$id") },
                    navigateToCategory = { id, name -> controller.navigate("Category/$id/$name") },
                    deleteLikedAttraction = viewModel::deleteLikedAttraction,
                    setCurrentQuery = setCurrentQuery
                )
            }
            composable("Event/{id}") { backStackEntry ->
                val viewModel: AttractionDetailViewModel by viewModels()
                val eventId = backStackEntry.stringArg("id")

                AttractionDetailScreen(
                    fetchFlow = viewModel.attractionFlow(eventId),
                    likedFlow = viewModel.likedFlow(eventId),
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
                    attractionsFlow = viewModel.attractionsFlow(categoryId),
                    navigateToEvent = { id -> controller.navigate("Event/$id") }
                )
            }
        }
    }
}