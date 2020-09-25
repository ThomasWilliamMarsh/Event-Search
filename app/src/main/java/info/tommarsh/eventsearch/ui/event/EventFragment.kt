package info.tommarsh.eventsearch.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import info.tommarsh.eventsearch.EventSearchApp
import info.tommarsh.eventsearch.ui.event.screen.EventScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class EventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = EventFragmentArgs.fromBundle(requireArguments())
        return ComposeView(context = requireContext()).apply {
            setContent {
                EventSearchApp {
                    EventScreen(name = args.eventName, id = args.eventId)
                }
            }
        }
    }
}