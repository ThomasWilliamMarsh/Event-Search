package info.tommarsh.eventsearch.domain

data class AttractionModel(
    val name: String,
    val id: String,
    val type: String,
    val url: String,
    val searchImage: String,
    val detailImage: String,
    val locale: String,
    val genre: String?,
    val numberOfEvents: Int
)