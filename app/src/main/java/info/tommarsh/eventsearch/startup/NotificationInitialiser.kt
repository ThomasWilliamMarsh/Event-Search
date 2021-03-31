package info.tommarsh.eventsearch.startup

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService
import androidx.startup.Initializer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import info.tommarsh.eventsearch.core.notifications.channel.NotificationChannelFactory

class NotificationInitialiser : Initializer<Unit> {
    override fun create(context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService<NotificationManager>()
            val channelsProvider = EntryPointAccessors.fromApplication(
                context,
                NotificationInitialiserDependencies::class.java
            )
            channelsProvider.getChannels().forEach { factory ->
                val channel = factory.create(context)
                notificationManager?.createNotificationChannel(channel)
            }
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface NotificationInitialiserDependencies {
    fun getChannels(): Set<NotificationChannelFactory>
}