package info.tommarsh.eventsearch.core.data.events.model

import info.tommarsh.eventsearch.domain.PresalesModel
import info.tommarsh.eventsearch.domain.PublicModel

private const val IMAGE_ASPECT_RATIO = "16_9"

internal fun EventResponse.toDomainModel(): List<info.tommarsh.eventsearch.domain.EventModel> {
    val events = _embedded.events

    return events.map { event ->
        info.tommarsh.eventsearch.domain.EventModel(
            id = event.id,
            name = event.name,
            venue = event._embedded.venues.firstOrNull()?.name.orEmpty(),
            initialStartDateTime = event.dates.initialStartDate?.dateTime,
            presales = event.sales.presales.toDomainModel(),
            publicSales = event.sales.public.toDomainModel(),
            imageUrl = event.images.getUrl(),
        )
    }
}

private fun List<Images>.getUrl(): String {
    return first { image -> image.ratio == IMAGE_ASPECT_RATIO && image.width >= 1020 }.url
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

