package info.tommarsh.eventsearch.core.data.likes.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LikedAttraction")
data class LikedAttraction(@PrimaryKey val id: String,
                           val name: String,
                           val imageUrl: String)