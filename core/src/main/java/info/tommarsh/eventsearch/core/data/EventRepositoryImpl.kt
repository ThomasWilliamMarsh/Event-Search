package info.tommarsh.eventsearch.core.data

import info.tommarsh.eventsearch.core.data.model.toDomainModel
import info.tommarsh.eventsearch.core.data.remote.EventsAPI
import info.tommarsh.eventsearch.domain.EventModel
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(private val api: EventsAPI) : EventRepository {

    override suspend fun getEvents(): List<EventModel> {
        return api.getEvents().toDomainModel()
    }
}