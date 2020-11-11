package info.tommarsh.eventsearch.domain

data class AttractionDetailsModel(
    val attraction: AttractionModel,
    val events: List<EventModel>
)