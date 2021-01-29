package info.tommarsh.eventsearch.core.data.events

import info.tommarsh.eventsearch.core.data.events.model.toDomainModel
import info.tommarsh.eventsearch.core.data.events.remote.EventsAPI
import info.tommarsh.eventsearch.core.data.events.model.domain.EventModel
import javax.inject.Inject

internal class EventRepositoryImpl @Inject constructor(private val api: EventsAPI) : EventRepository {

    override suspend fun getEventsForAttraction(id: String): List<EventModel> {
        return api.getEvents(id).toDomainModel()
    }
}