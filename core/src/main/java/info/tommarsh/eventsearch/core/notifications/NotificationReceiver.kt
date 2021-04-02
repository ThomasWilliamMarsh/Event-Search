package info.tommarsh.eventsearch.core.notifications

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService<NotificationManager>() ?: return
        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION) ?: return
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)

        notificationManager.notify(notificationId, notification)
    }

    companion object {
        internal const val NOTIFICATION_ID = "notification_id"
        internal const val NOTIFICATION = "notification"
    }
}