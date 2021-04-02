package info.tommarsh.eventsearch.ui.common

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import info.tommarsh.eventsearch.core.R
import info.tommarsh.eventsearch.core.notifications.NotificationScheduler
import info.tommarsh.eventsearch.notification.EventReminderNotification
import javax.inject.Inject

@ActivityScoped
class ReminderDialog @Inject internal constructor(
    @ActivityContext private val context: Context,
    private val notificationScheduler: NotificationScheduler,
    private val notificationFactory: EventReminderNotification,

    ) {

    suspend fun show(id: String, eventName: String, url: String) {

        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(context.getString(R.string.reminder_dialog_title))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        val notification = notificationFactory.create(id = id, name = eventName, image = url)
        picker.addOnPositiveButtonClickListener { time ->
            notificationScheduler.schedule(
                time = time,
                id = id.hashCode(),
                notification = notification
            )
            Toast.makeText(context, "Reminder set!", Toast.LENGTH_SHORT).show()
        }

        picker.show((context as AppCompatActivity).supportFragmentManager, "date")
    }
}
