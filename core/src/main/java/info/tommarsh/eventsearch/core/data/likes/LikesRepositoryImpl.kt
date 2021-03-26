package info.tommarsh.eventsearch.core.data.likes

import info.tommarsh.eventsearch.core.data.likes.local.LikedAttractionsDao
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.core.data.likes.model.toDaoModel
import info.tommarsh.eventsearch.core.data.likes.model.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class LikesRepositoryImpl
@Inject constructor(private val likedAttractionsDao: LikedAttractionsDao) : LikesRepository {

    override fun getLikedAttractions(): Flow<List<LikedAttractionModel>> {
        return likedAttractionsDao.getLikedAttractions()
            .map { liked ->
                liked.map { dbModel -> dbModel.toDomainModel() }
            }
    }

    override fun getAttractionLiked(id: String): Flow<Boolean> {
        return likedAttractionsDao.getLikedAttractions()
            .map { attractions -> attractions.find { liked -> liked.id == id } != null }
    }

    override suspend fun addLikedAttraction(attraction: LikedAttractionModel) {
        likedAttractionsDao.addLikedAttraction(attraction.toDaoModel())
    }

    override suspend fun removeLikedAttraction(attraction: LikedAttractionModel) {
        likedAttractionsDao.removeLikedAttraction(attraction.toDaoModel())
    }
}