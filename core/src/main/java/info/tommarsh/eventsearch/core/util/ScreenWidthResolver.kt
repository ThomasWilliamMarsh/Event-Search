package info.tommarsh.eventsearch.core.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ScreenWidthResolver
@Inject constructor(@ApplicationContext private val context: Context) {
    fun get() = context.resources.displayMetrics.widthPixels
}