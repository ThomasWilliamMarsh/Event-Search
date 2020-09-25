package info.tommarsh.eventsearch.core.data.events

import info.tommarsh.eventsearch.core.data.events.model.toDomainModel
import info.tommarsh.eventsearch.core.data.events.remote.EventsAPI
import info.tommarsh.eventsearch.domain.EventModel
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(private val api: EventsAPI) : EventRepository {

    override suspend fun getEvents(): List<EventModel> {
        return api.getEvents().toDomainModel()
    }

    override suspend fun searchForEvents(query: String): List<EventModel> {
        return api.searchForEvents(query).toDomainModel()
    }

    override suspend fun eventsFromCategory(category: String): List<EventModel> {
        return api.eventsFromCategory(category).toDomainModel()
    }
}