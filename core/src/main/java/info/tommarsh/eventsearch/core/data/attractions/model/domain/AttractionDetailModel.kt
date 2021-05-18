package info.tommarsh.eventsearch.core.data.attractions.model.domain

import info.tommarsh.eventsearch.core.data.events.model.domain.EventModel

data class AttractionDetailModel(
    val attraction: AttractionModel,
    val events: List<EventModel>,
    val relatedAttractions: List<AttractionModel>
)