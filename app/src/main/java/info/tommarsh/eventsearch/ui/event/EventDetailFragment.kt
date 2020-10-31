package info.tommarsh.eventsearch.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.ui.event.screen.EventDetailScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class EventDetailFragment : Fragment() {

    private val viewModel by viewModels<EventDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = EventDetailFragmentArgs.fromBundle(requireArguments())

        viewModel.getEventDetails(args.eventId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(context = requireContext()).apply {
            setContent {
                EventSearchApp {
                    EventDetailScreen(viewModel)
                }
            }
        }
    }
}