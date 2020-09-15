package info.tommarsh.eventsearch.core.data.events.remote

import info.tommarsh.eventsearch.core.data.events.model.EventResponse
import retrofit2.http.GET

interface EventsAPI
{
    @GET("discovery/v2/events.json")
    suspend fun getEvents() : EventResponse
}