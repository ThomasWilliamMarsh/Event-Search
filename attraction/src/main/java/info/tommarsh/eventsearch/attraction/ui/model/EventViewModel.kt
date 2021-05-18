package info.tommarsh.eventsearch.attraction.ui.model

internal data class EventViewModel(
    val date: EventDateViewModel,
    val venue: String
)

internal sealed class EventDateViewModel {
    data class Date(val month: String, val day: String, val dowAndTime: String) :
        EventDateViewModel()

    object TBA : EventDateViewModel()
    object TBC : EventDateViewModel()
}