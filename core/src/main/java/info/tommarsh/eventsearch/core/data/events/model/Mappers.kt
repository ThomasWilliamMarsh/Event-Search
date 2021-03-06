package info.tommarsh.eventsearch.core.data.events.model

import info.tommarsh.eventsearch.core.data.events.model.data.*
import info.tommarsh.eventsearch.core.data.events.model.domain.*

private const val IMAGE_ASPECT_RATIO = "16_9"
private const val EVENT_VENUE_UNKNOWN = "Unknown"

internal fun EventResponse.toDomainModel(): List<EventModel> {
    val events = _embedded.events
    return events.map { event -> event.toDomainModel() }
}

internal fun Event.toDomainModel(): EventModel {
    return EventModel(
        id = id,
        name = name,
        promoter = promoter?.toDomainModel(),
        venue = _embedded.venues.firstOrNull()?.name ?: EVENT_VENUE_UNKNOWN,
        dates = dates.toDomainModel(),
        presales = sales.presales.toDomainModel(),
        publicSales = sales.public.toDomainModel(),
        imageUrl = images.getUrl()
    )
}

private fun List<Images>.getUrl(): String {
    return first { image -> image.ratio == IMAGE_ASPECT_RATIO && image.width >= 1020 }.url
}

private fun Dates.toDomainModel(): DatesModel {
    return DatesModel(start = start.toDomainModel())
}

private fun Start.toDomainModel(): StartModel {
    return StartModel(
        dateTime = dateTime,
        dateTBD = dateTBD,
        dateTBA = dateTBA
    )
}

private fun Promoter.toDomainModel(): PromoterModel {
    return PromoterModel(
        id = id,
        name = name
    )
}

private fun List<Presales>?.toDomainModel(): List<PresalesModel>? {
    return this?.map { domain ->
        PresalesModel(domain.startDateTime, domain.endDateTime)
    }
}

private fun Public?.toDomainModel(): PublicModel? {
    return this?.let {
        PublicModel(
            startTBA = startTBA,
            startTBD = startTBD,
            startDateTime = startDateTime,
            endDateTime = endDateTime
        )
    }
}

