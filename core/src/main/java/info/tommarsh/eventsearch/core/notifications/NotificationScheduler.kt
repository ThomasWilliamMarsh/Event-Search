package info.tommarsh.eventsearch.core.notifications

import android.app.AlarmManager
import android.app.AlarmManager.RTC
import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService
import androidx.core.os.bundleOf
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun schedule(time: Long, id: Int, notification: Notification) {
        val alarmManager = context.getSystemService<AlarmManager>()
        alarmManager?.setExact(RTC, time, buildNotificationIntent(id, notification))
    }

    private fun buildNotificationIntent(id: Int, notification: Notification): PendingIntent {
        val extras = bundleOf(
            NotificationReceiver.NOTIFICATION_ID to id,
            NotificationReceiver.NOTIFICATION to notification
        )
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtras(extras)

        return PendingIntent.getBroadcast(context, 0, intent, FLAG_UPDATE_CURRENT)
    }
}