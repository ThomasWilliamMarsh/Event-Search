package info.tommarsh.eventsearch.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import info.tommarsh.eventsearch.core.notifications.NotificationChannelFactory
import info.tommarsh.eventsearch.notification.EventReminderChannel

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NotificationModule {

    @Binds
    @IntoSet
    abstract fun bindEventReminderChannel(eventReminderChannel: EventReminderChannel): NotificationChannelFactory
}