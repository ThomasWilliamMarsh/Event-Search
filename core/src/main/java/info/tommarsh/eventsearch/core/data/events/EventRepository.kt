package info.tommarsh.eventsearch.core.data.events

import info.tommarsh.eventsearch.domain.EventModel

interface EventRepository {

    suspend fun getEvents(): List<EventModel>

    suspend fun searchForEvents(query: String) : List<EventModel>

    suspend fun eventsFromCategory(category: String) : List<EventModel>

    suspend fun eventDetails(event: String) : EventModel
}