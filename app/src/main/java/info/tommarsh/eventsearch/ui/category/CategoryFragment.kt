package info.tommarsh.eventsearch.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.ui.category.screen.CategoryScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = CategoryFragmentArgs.fromBundle(requireArguments())
        return ComposeView(context = requireContext()).apply {
            setContent {
                EventSearchApp {
                    CategoryScreen(name = args.categoryName, id = args.categoryId)
                }
            }
        }
    }
}