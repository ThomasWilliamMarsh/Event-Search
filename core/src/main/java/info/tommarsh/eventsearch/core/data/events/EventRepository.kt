package info.tommarsh.eventsearch.core.data.events

import info.tommarsh.eventsearch.domain.EventModel

interface EventRepository {

    suspend fun getEventsForAttraction(id: String): List<EventModel>
}