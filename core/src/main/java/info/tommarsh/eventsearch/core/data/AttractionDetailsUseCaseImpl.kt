package info.tommarsh.eventsearch.core.data

import info.tommarsh.eventsearch.core.data.attractions.AttractionsRepository
import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionDetailsModel
import info.tommarsh.eventsearch.core.data.events.EventRepository
import javax.inject.Inject

class AttractionDetailsUseCaseImpl
@Inject internal constructor(
    private val eventRepository: EventRepository,
    private val attractionsRepository: AttractionsRepository
) : AttractionDetailsUseCase {

    override suspend fun get(id: String): AttractionDetailsModel {
        val attraction = attractionsRepository.getAttraction(id)
        val events = eventRepository.getEventsForAttraction(id)

        return AttractionDetailsModel(
            attraction = attraction,
            events = events
        )
    }
}