package info.tommarsh.eventsearch.core.notifications

import android.app.Notification
import android.content.Context
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import dagger.hilt.android.qualifiers.ApplicationContext
import info.tommarsh.eventsearch.core.R
import info.tommarsh.eventsearch.core.notifications.channel.EventReminderChannel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NotificationFactory
@Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageLoader: ImageLoader
) {

    private val imageHeight = 300
    private val imageWidth = 534

    suspend fun create(type: NotificationType): Notification {
        return when (type) {
            is NotificationType.EventReminder -> buildEventReminder(type.eventName, type.imageUrl)
        }
    }

    private suspend fun buildEventReminder(name: String, image: String): Notification {
        return NotificationCompat.Builder(context, EventReminderChannel.CHANNEL_ID)
            .setContentTitle(context.getString(R.string.event_reminder_notification_title))
            .setContentText(name)
            .setSmallIcon(R.drawable.ic_baseline_event_24)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(createBitmap(image))
            )
            .build()
    }

    private suspend fun createBitmap(url: String): Bitmap? {
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()

        return when (val result = imageLoader.execute(request)) {
            is ErrorResult -> null
            is SuccessResult -> result.drawable.toBitmap(imageWidth, imageHeight)
        }
    }
}

sealed class NotificationType {
    data class EventReminder(val eventName: String, val imageUrl: String) : NotificationType()
}