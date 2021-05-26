package info.tommarsh.eventsearch.attraction.ui.model

import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionDetailModel
import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionModel
import info.tommarsh.eventsearch.core.data.events.model.domain.EventModel
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import org.joda.time.DateTime

internal fun AttractionDetailViewModel.toLikedAttraction(): LikedAttractionModel {
    return LikedAttractionModel(
        id = id,
        name = name,
        imageUrl = detailImage.orEmpty()
    )
}

internal fun AttractionDetailModel.toViewModel(): AttractionDetailViewModel {
    return AttractionDetailViewModel(
        id = attraction.id,
        name = attraction.name,
        description = attraction.description,
        genre = attraction.genreName.orEmpty(),
        numberOfEvents = attraction.numberOfEvents,
        detailImage = attraction.searchImage,
        events = events.map { event -> event.toViewModel() },
        relatedAttractions = relatedAttractions.map { attraction -> attraction.toViewModel() }
    )
}

internal fun AttractionModel.toViewModel(): RelatedAttractionViewModel {
    return RelatedAttractionViewModel(
        id = id,
        imageUrl = detailImage
    )
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