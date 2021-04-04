package info.tommarsh.eventsearch.attraction.ui.model

import info.tommarsh.eventsearch.core.data.events.model.domain.EventModel
import org.joda.time.DateTime

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

internal fun EventModel.toViewModel(): EventViewModel {
    val start = dates.start
    val date = when {
        start.dateTBA -> EventDateViewModel.TBA
        start.dateTBD -> EventDateViewModel.TBC
        else -> {
            val dateTime = DateTime(start.dateTime)
            EventDateViewModel.Date(
                month = dateTime.getMonthString(),
                day = dateTime.getDayString(),
                dowAndTime = dateTime.getDowTimeString()
            )
        }
    }

    return EventViewModel(
        date = date,
        venue = venue
    )
}

private fun DateTime.getMonthString(): String {
    return monthOfYear().asShortText
}

private fun DateTime.getDayString(): String {
    return dayOfMonth().asShortText
}

private fun DateTime.getDowTimeString(): String {
    val localTime = toLocalTime()
    return "${dayOfWeek().asShortText} - " +
            localTime.hourOfDay.toString().padStart(2, '0') + ":" +
            localTime.minuteOfHour.toString().padStart(2, '0')
}