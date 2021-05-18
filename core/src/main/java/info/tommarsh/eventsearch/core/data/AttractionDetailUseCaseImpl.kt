package info.tommarsh.eventsearch.core.data

import info.tommarsh.eventsearch.core.data.attractions.AttractionsRepository
import info.tommarsh.eventsearch.core.data.attractions.model.domain.AttractionDetailModel
import info.tommarsh.eventsearch.core.data.events.EventRepository
import javax.inject.Inject

class AttractionDetailUseCaseImpl
@Inject internal constructor(
    private val eventRepository: EventRepository,
    private val attractionsRepository: AttractionsRepository
) : AttractionDetailUseCase {

    override suspend fun get(id: String): AttractionDetailModel {
        val attraction = attractionsRepository.getAttraction(id)
        val events = eventRepository.getEventsForAttraction(id)
        val relatedAttractions = attraction.genreId?.let { genreId ->
            attractionsRepository.getAttractionsForGenre(genreId)
        } ?: emptyList()

        return AttractionDetailModel(attraction, events, relatedAttractions)
    }
}