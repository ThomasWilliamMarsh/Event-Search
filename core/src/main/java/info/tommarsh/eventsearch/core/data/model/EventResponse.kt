package info.tommarsh.eventsearch.core.data.model

data class EventResponse(
    val _embedded: Embedded,
    val page: Page
)

data class Embedded(
    val events: List<Event>
)

data class Page(
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
    val number: Int
)

data class Event(
    val name: String,
    val id: String,
    val info: String?,
    val url: String,
    val images: List<Images>,
    val sales: Sales,
    val dates: Dates,
    val _embedded: EmbeddedEvent
)

data class EmbeddedEvent(
    val venues: List<Venue>
)

data class Venue(val name: String)

data class Images(
    val ratio: String,
    val url: String,
    val width: Int,
    val height: Int,
    val fallback: Boolean
)

data class Sales(
    val public: Public?,
    val presales: List<Presales>?
)

data class Dates(
    val initialStartDate: InitialStartDate?,
    val timezone: String?,
    val spanMultipleDays: Boolean
)

data class Public(
    val startDateTime: String?,
    val startTBD: Boolean,
    val startTBA: Boolean,
    val endDateTime: String?
)

data class Presales(
    val startDateTime: String?,
    val endDateTime: String?,
    val name: String
)

data class InitialStartDate(
    val localDate: String,
    val localTime: String,
    val dateTime: String
)

