package info.tommarsh.eventsearch.core.data.attractions.model.domain

data class AttractionModel(
    val name: String,
    val id: String,
    val type: String,
    val url: String,
    val additionalInfo: String?,
    val description: String?,
    val searchImage: String,
    val detailImage: String,
    val locale: String,
    val genreName: String?,
    val genreId: String?,
    val numberOfEvents: Int
)