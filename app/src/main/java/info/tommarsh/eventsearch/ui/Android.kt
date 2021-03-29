package info.tommarsh.eventsearch.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import info.tommarsh.eventsearch.core.data.likes.model.domain.LikedAttractionModel
import info.tommarsh.eventsearch.model.AttractionViewModel
import javax.inject.Inject

@ActivityScoped
internal class ReminderDialog @Inject constructor(@ActivityContext private val activity: Context) {

    fun show(model: LikedAttractionModel) {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Set your reminder")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        picker.show((activity as AppCompatActivity).supportFragmentManager, "date")
    }
}