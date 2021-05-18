package info.tommarsh.eventsearch.attraction.ui.model

internal data class AttractionDetailViewModel(
    val id: String,
    val name: String,
    val genre: String,
    val description: String?,
    val numberOfEvents: Int,
    val detailImage: String?,
    val events: List<EventViewModel>,
    val relatedAttractions: List<RelatedAttractionViewModel>
)