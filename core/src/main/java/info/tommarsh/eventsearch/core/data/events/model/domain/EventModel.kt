package info.tommarsh.eventsearch.core.data.events.model.domain

data class EventModel(
    val id: String,
    val name: String,
    val venue: String,
    val dates: DatesModel,
    val promoter: PromoterModel?,
    val presales: List<PresalesModel>?,
    val publicSales: PublicModel?,
    val imageUrl: String
)

data class DatesModel(
    val start: StartModel
)

data class StartModel(
    val dateTime: String?,
    val dateTBD: Boolean,
    val dateTBA: Boolean,
)

data class PromoterModel(
    val id: String,
    val name: String
)

data class PresalesModel(
    val startDateTime: String?,
    val endDateTime: String?
)

data class PublicModel(
    val startTBA: Boolean,
    val startTBD: Boolean,
    val startDateTime: String?,
    val endDateTime: String?
)