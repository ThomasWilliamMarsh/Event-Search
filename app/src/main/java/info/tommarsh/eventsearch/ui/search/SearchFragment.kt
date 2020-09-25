package info.tommarsh.eventsearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.compose.runtime.Providers
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.ui.search.navigation.Navigator
import info.tommarsh.eventsearch.ui.search.navigation.SearchNavigator
import info.tommarsh.eventsearch.ui.search.screen.SearchScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel by viewModels<SearchViewModel>()

    private val navigator by lazy { SearchNavigator(findNavController()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(context = requireContext()).apply {
            setContent {
                Providers(Navigator provides navigator) {
                    EventSearchApp {
                        SearchScreen(searchViewModel = viewModel)
                    }
                }
            }
        }
    }
}