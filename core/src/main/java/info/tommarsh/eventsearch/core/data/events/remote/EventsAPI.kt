package info.tommarsh.eventsearch.core.data.events.remote

import info.tommarsh.eventsearch.core.data.events.model.EventResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsAPI {
    @GET("discovery/v2/events.json")
    suspend fun getEvents(): EventResponse

    @GET("discovery/v2/events.json")
    suspend fun searchForEvents(@Query("keyword") query: String): EventResponse
}