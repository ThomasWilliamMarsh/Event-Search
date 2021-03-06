package info.tommarsh.eventsearch.core.data.attractions.remote

import info.tommarsh.eventsearch.core.data.attractions.model.data.AttractionResponse
import info.tommarsh.eventsearch.core.data.attractions.model.data.AttractionsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AttractionsAPI {
    @GET("discovery/v2/attractions.json")
    suspend fun getAttractions(): AttractionsResponse

    @GET("discovery/v2/attractions.json")
    suspend fun getAttractionsForKeyword(
        @Query("keyword") query: String,
        @Query("page") page: Int
    ): AttractionsResponse

    @GET("discovery/v2/attractions.json")
    suspend fun getAttractionsForCategory(
        @Query("classificationName") query: String,
        @Query("page") page: Int
    ): AttractionsResponse

    @GET("discovery/v2/attractions.json")
    suspend fun getAttractionsForGenre(
        @Query("genreId") query: String,
        @Query("page") page: Int
    ): AttractionsResponse

    @GET("discovery/v2/attractions/{attraction}.json")
    suspend fun attractionDetails(@Path("attraction") event: String): AttractionResponse
}