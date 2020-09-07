package info.tommarsh.eventsearch.core.data.remote

import info.tommarsh.eventsearch.core.data.model.EventResponse
import retrofit2.http.GET

interface EventsAPI
{
    @GET("discovery/v2/events.json")
    suspend fun getEvents() : EventResponse
}