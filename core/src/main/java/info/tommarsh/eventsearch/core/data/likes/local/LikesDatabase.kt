package info.tommarsh.eventsearch.core.data.likes.local

import androidx.room.Database
import androidx.room.RoomDatabase
import info.tommarsh.eventsearch.core.data.likes.model.LikedAttraction

@Database(entities = [LikedAttraction::class], version = 1)
internal abstract class LikesDatabase : RoomDatabase() {
    abstract fun likedAttractionsDao() : LikedAttractionsDao
}