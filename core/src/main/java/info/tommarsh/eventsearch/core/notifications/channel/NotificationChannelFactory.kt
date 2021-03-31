package info.tommarsh.eventsearch.core.notifications.channel

import android.app.NotificationChannel
import android.content.Context

interface NotificationChannelFactory {

    fun create(context: Context): NotificationChannel
}