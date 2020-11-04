package info.tommarsh.eventsearch.domain

data class EventModel(
    val id: String,
    val name: String,
    val venue: String,
    val initialStartDateTime: String?,
    val promoter: PromoterModel,
    val presales: List<PresalesModel>?,
    val publicSales: PublicModel?,
    val imageUrl: String
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