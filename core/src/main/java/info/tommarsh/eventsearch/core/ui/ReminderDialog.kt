package info.tommarsh.eventsearch.core.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import info.tommarsh.eventsearch.core.R
import info.tommarsh.eventsearch.core.notifications.NotificationFactory
import info.tommarsh.eventsearch.core.notifications.NotificationScheduler
import info.tommarsh.eventsearch.core.notifications.NotificationType
import javax.inject.Inject

@ActivityScoped
class ReminderDialog @Inject internal constructor(
    @ActivityContext private val context: Context,
    private val notificationScheduler: NotificationScheduler,
    private val notificationFactory: NotificationFactory
) {

    suspend fun show(id: String, eventName: String, url: String) {

        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(context.getString(R.string.reminder_dialog_title))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        val notification = notificationFactory.create(
            NotificationType.EventReminder(
                eventName = eventName,
                imageUrl = url
            )
        )
        picker.addOnPositiveButtonClickListener { time -> notificationScheduler.schedule(
                time = time,
                id = id.hashCode(),
                notification = notification
            )
        }

        picker.show((context as AppCompatActivity).supportFragmentManager, "date")
    }
}
