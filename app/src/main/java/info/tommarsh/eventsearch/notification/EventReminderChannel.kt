package info.tommarsh.eventsearch.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import info.tommarsh.eventsearch.core.R
import info.tommarsh.eventsearch.core.notifications.NotificationChannelFactory
import javax.inject.Inject

internal class EventReminderChannel @Inject constructor() : NotificationChannelFactory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun create(context: Context): NotificationChannel {
        val name = context.getString(R.string.event_reminder_channel_name)
        val description = context.getString(R.string.event_reminder_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        return NotificationChannel(CHANNEL_ID, name, importance).apply {
            setDescription(description)
        }
    }

    companion object {
        const val CHANNEL_ID = "event_reminders"
    }
}