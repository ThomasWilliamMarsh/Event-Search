package info.tommarsh.eventsearch.core.data.events

import info.tommarsh.eventsearch.domain.EventModel

interface EventRepository {

    suspend fun getEvents(): List<EventModel>
}