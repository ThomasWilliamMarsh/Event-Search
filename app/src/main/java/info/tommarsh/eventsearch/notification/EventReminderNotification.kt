package info.tommarsh.eventsearch.notification


import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import dagger.hilt.android.qualifiers.ApplicationContext
import info.tommarsh.eventsearch.core.R
import info.tommarsh.eventsearch.ui.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class EventReminderNotification
@Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageLoader: ImageLoader
) {

    suspend fun create(id: String, name: String, image: String): Notification {
        return NotificationCompat.Builder(context, EventReminderChannel.CHANNEL_ID)
            .setContentTitle(context.getString(R.string.event_reminder_notification_title))
            .setContentText(name)
            .setSmallIcon(R.drawable.ic_baseline_event_24)
            .setContentIntent(createDeepLink(id))
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(createBitmap(image))
            )
            .build()
    }

    private fun createDeepLink(id: String): PendingIntent {
        val intent = Intent(
            ACTION_VIEW,
            "app://eventsearch.app/attraction/$id".toUri(),
            context, MainActivity::class.java
        )
        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)!!
        }
    }

    private suspend fun createBitmap(url: String): Bitmap? {
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()

        return when (val result = imageLoader.execute(request)) {
            is ErrorResult -> null
            is SuccessResult -> result.drawable.toBitmap(IMAGE_WIDTH, IMAGE_HEIGHT)
        }
    }

    companion object {
        private const val IMAGE_HEIGHT = 300
        private const val IMAGE_WIDTH = 534
    }
}

