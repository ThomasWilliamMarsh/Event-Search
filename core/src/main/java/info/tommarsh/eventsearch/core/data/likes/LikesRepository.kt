package info.tommarsh.eventsearch.core.data.likes

import info.tommarsh.eventsearch.domain.LikedAttractionModel
import kotlinx.coroutines.flow.Flow

interface LikesRepository {

    fun getLikedAttractions() : Flow<List<LikedAttractionModel>>

    suspend fun addLikedAttraction(attraction: LikedAttractionModel)

    suspend fun removeLikedAttraction(attraction: LikedAttractionModel)
}