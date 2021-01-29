package info.tommarsh.eventsearch.core.data.attractions.model.domain

import info.tommarsh.eventsearch.core.data.events.model.domain.EventModel

data class AttractionDetailsModel(
    val attraction: AttractionModel,
    val events: List<EventModel>
)