package info.tommarsh.eventsearch.core.data.likes.local

import androidx.room.*
import info.tommarsh.eventsearch.core.data.likes.model.data.LikedAttraction
import kotlinx.coroutines.flow.Flow

@Dao
interface LikedAttractionsDao {

    @Query("SELECT * FROM LikedAttraction")
    fun getLikedAttractions() : Flow<List<LikedAttraction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLikedAttraction(attraction: LikedAttraction)

    @Delete
    suspend fun removeLikedAttraction(attraction: LikedAttraction)
}