package info.tommarsh.eventsearch.core.data.attractions.model.data

data class AttractionsResponse(
    val _embedded: EmbeddedResponse?,
    val page: PageResponse
)

data class PageResponse(
    val size: Int,
    val totalPages: Int,
    val totalElements: Int,
    val number: Int
)

data class EmbeddedResponse(
    val attractions: List<AttractionResponse>
)

data class AttractionResponse(
    val name: String,
    val type: String,
    val id: String,
    val test: Boolean,
    val url: String?,
    val locale: String,
    val description: String?,
    val additionalInfo: String?,
    val images: List<ImageResponse>,
    val classifications: List<ClassificationResponse>,
    val upcomingEvents: UpcomingEventsResponse,
    val _links: LinksResponse
)

data class ClassificationResponse(
    val primary: Boolean,
    val genre: GenreResponse?
)

data class GenreResponse(
    val id: String,
    val name: String
)

data class ImageResponse(
    val ratio: String,
    val url: String,
    val width: Int,
    val height: Int,
    val fallback: Boolean
)

data class UpcomingEventsResponse(
    val _total: Int,
    val ticketmaster: Int?
)

data class LinksResponse(
    val self: SelfResponse
)

data class SelfResponse(
    val href: String
)
