package info.tommarsh.eventsearch.core.data.events.model.data

internal data class EventResponse(
    val _embedded: Embedded,
    val page: Page
)

internal data class Embedded(
    val events: List<Event>
)

internal data class Page(
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
    val number: Int
)

internal data class Event(
    val name: String,
    val id: String,
    val info: String?,
    val url: String,
    val promoter: Promoter?,
    val images: List<Images>,
    val sales: Sales,
    val dates: Dates,
    val _embedded: EmbeddedEvent
)

internal data class EmbeddedEvent(
    val venues: List<Venue>
)

internal data class Venue(val name: String)

internal data class Images(
    val ratio: String,
    val url: String,
    val width: Int,
    val height: Int,
    val fallback: Boolean
)

internal data class Sales(
    val public: Public?,
    val presales: List<Presales>?
)

internal data class Dates(
    val start: Start
)

internal data class Start(
    val dateTime: String?,
    val dateTBD: Boolean,
    val dateTBA: Boolean,
)

internal data class Public(
    val startDateTime: String?,
    val startTBD: Boolean,
    val startTBA: Boolean,
    val endDateTime: String?
)

internal data class Presales(
    val startDateTime: String?,
    val endDateTime: String?,
    val name: String
)

internal data class Promoter(
    val id: String,
    val name: String
)

