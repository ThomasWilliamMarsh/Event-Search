package info.tommarsh.eventsearch.core.data.likes

import info.tommarsh.eventsearch.core.data.likes.local.LikedAttractionsDao
import info.tommarsh.eventsearch.core.data.likes.model.LikedAttraction
import info.tommarsh.eventsearch.core.data.likes.model.toDomainModel
import info.tommarsh.eventsearch.domain.LikedAttractionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class LikesRepositoryImpl
@Inject constructor(private val likedAttractionsDao: LikedAttractionsDao) : LikesRepository {

    override fun getLikedAttractions(): Flow<List<LikedAttractionModel>> {
        return likedAttractionsDao.getLikedAttractions()
            .map { liked -> liked.map { dbModel -> dbModel.toDomainModel() } }
    }

    override suspend fun addLikedAttraction(id: String) {
        likedAttractionsDao.addLikedAttraction(LikedAttraction(id))
    }

    override suspend fun removeLikedAttraction(id: String) {
        likedAttractionsDao.removeLikedAttraction(LikedAttraction(id))
    }
}