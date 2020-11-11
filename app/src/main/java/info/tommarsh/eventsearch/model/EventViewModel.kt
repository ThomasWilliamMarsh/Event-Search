package info.tommarsh.eventsearch.model

import info.tommarsh.eventsearch.domain.EventModel
import org.joda.time.DateTime

internal data class EventViewModel(
    val month: String,
    val day: String,
    val dowAndTime: String,
    val venue: String
)

internal fun EventModel.toViewModel(): EventViewModel {
    val dateTime = DateTime(initialStartDateTime)
    return EventViewModel(
        month = dateTime.getMonthString(),
        day = dateTime.getDayString(),
        dowAndTime = dateTime.getDowTimeString(),
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
    return "${dayOfWeek().asShortText} - ${localTime.hourOfDay}:${localTime.minuteOfHour}"
}